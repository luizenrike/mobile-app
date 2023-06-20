package com.loja.swiftshop.model

import android.app.Activity
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.loja.swiftshop.adapter.AdapterPedido
import com.loja.swiftshop.adapter.AdapterProduto
import org.w3c.dom.Text
import java.util.UUID

class DB {

    fun salvarDadosUsuario(nome: String){

        val usuarioId = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val usuarios = hashMapOf(
            "nome" to nome
        )

        val documentReference:DocumentReference = db
            .collection("Usuarios")
            .document(usuarioId)

        documentReference.set(usuarios).addOnSuccessListener{
            Log.d("DB", "Sucesso ao salvar os dados")

        }.addOnFailureListener{ error ->
            Log.d("DB_ERROR", "Erro ao salvar os dados! ${error.printStackTrace()}")
        }

    }

    fun recuperarDadosUsuarioPerfil(context : Activity, nomeUsuario : TextView, emailUsuario : TextView, fotoPerfil: ImageView){
        val usuarioId = FirebaseAuth.getInstance().currentUser!!.uid
        val email = FirebaseAuth.getInstance().currentUser!!.email
        val db = FirebaseFirestore.getInstance()

        val documentReference : DocumentReference = db.collection("Usuarios").document(usuarioId)
        documentReference.addSnapshotListener { documento, error ->
            if(documento != null){
                nomeUsuario.text = documento.getString("nome")
                emailUsuario.text = email
                Glide.with(context).load(documento.getString("foto")).into(fotoPerfil)
            }
        }

    }

    fun obterListaDeProdutos(lista_produtos: MutableList<Produto>, adapter_produto:AdapterProduto){
        val db = FirebaseFirestore.getInstance()

        db.collection("Produtos").get()
            .addOnCompleteListener{tarefa ->
                if(tarefa.isSuccessful){
                    for(documento in tarefa.result!!){
                        val produtos = documento.toObject(Produto::class.java)
                        lista_produtos.add(produtos)
                        adapter_produto.notifyDataSetChanged()
                    }
                }
            }

    }

    fun obterListaDeProdutosFiltrada(filtro : String, lista_produtos: MutableList<Produto>, adapter_produto:AdapterProduto){
        val db = FirebaseFirestore.getInstance()

        db.collection("Produtos").get()
            .addOnCompleteListener{tarefa ->
                if(tarefa.isSuccessful){
                    for(documento in tarefa.result!!){
                        val produtos = documento.toObject(Produto::class.java)
                        if(produtos.categoria == filtro) {
                            lista_produtos.add(produtos)
                            adapter_produto.notifyDataSetChanged()
                        }
                    }
                }
            }

    }

    fun obterListaDeProdutosSearchView(filtro : String, lista_produtos: MutableList<Produto>, adapter_produto:AdapterProduto){
        val db = FirebaseFirestore.getInstance()
        db.collection("Produtos").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val filteredList = ArrayList<Produto>()

                    for (documento in task.result!!) {
                        val produto = documento.toObject(Produto::class.java)
                        if (produto.nome?.lowercase()?.contains(filtro.lowercase()) == true) {
                            filteredList.add(produto)
                        }
                    }

                    lista_produtos.clear()
                    lista_produtos.addAll(filteredList)
                    adapter_produto.notifyDataSetChanged()
                }
            }
//        db.collection("Produtos").get()
//            .addOnCompleteListener{tarefa ->
//                if(tarefa.isSuccessful){
//                    for(documento in tarefa.result!!){
//                        val produtos = documento.toObject(Produto::class.java)
//                        if(produtos.nome?.lowercase()?.contains(filtro.lowercase()) == true) {
//                            lista_produtos.add(produtos)
//                            adapter_produto.notifyDataSetChanged()
//                        }
//                    }
//                }
//            }

    }

    fun salvarDadosPedidoUsuario(
        endereco : String,
        celular : String,
        produto : String,
        preco : String,
        tamanho : String,
        status_pagamento : String,
        status_entrega : String,
    ){
        var db = FirebaseFirestore.getInstance()
        var usuarioID = FirebaseAuth.getInstance().currentUser!!.uid
        var pedidoID = UUID.randomUUID().toString()


        val pedidos = hashMapOf(
            "endereco" to endereco,
            "celular" to celular,
            "produto" to produto,
            "preco" to preco,
            "tamanho" to tamanho,
            "status_pagamento" to status_pagamento,
            "status_entrega" to status_entrega
        )

        val documentReference = db.collection("Usuario_Pedidos").document(usuarioID)
            .collection("Pedidos").document(pedidoID)
        documentReference.set(pedidos).addOnSuccessListener {
            Log.d("db_pedidos", "Sucesso ao salvar os pedidos")
        }


    }

    fun obterListaDePedidos(lista_pedidos : MutableList<Pedido>, adapter_pedidos : AdapterPedido){
        var db = FirebaseFirestore.getInstance()
        var usuarioID = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("Usuario_Pedidos").document(usuarioID).collection("Pedidos")
            .get().addOnCompleteListener{tarefa ->
                if(tarefa.isSuccessful){
                    for(documento in tarefa.result!!){
                        val pedidos = documento.toObject(Pedido::class.java)
                        lista_pedidos.add(pedidos)
                        adapter_pedidos.notifyDataSetChanged()
                    }
                }
            }
    }
}