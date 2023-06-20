package com.loja.swiftshop.activities.TelaPrincipal

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SearchEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.loja.swiftshop.R
import com.loja.swiftshop.activities.FormLogin.FormLogin
import com.loja.swiftshop.activities.Pedidos.Pedidos
import com.loja.swiftshop.activities.dialog.DialogPerfilUsuario
import com.loja.swiftshop.adapter.AdapterProduto
import com.loja.swiftshop.databinding.ActivityTelaPrincipalBinding
import com.loja.swiftshop.model.DB
import com.loja.swiftshop.model.Produto

class TelaPrincipal : AppCompatActivity() {
    var clicked = false
    lateinit var binding : ActivityTelaPrincipalBinding
    lateinit var adapterProdutos:AdapterProduto
    var lista_produtos: MutableList<Produto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recycler_produtos = binding.recyclerProdutos
        recycler_produtos.layoutManager = GridLayoutManager(this, 2)
        recycler_produtos.setHasFixedSize(true)
        adapterProdutos = AdapterProduto(this, lista_produtos)
        recycler_produtos.adapter = adapterProdutos
        val db = DB()
        db.obterListaDeProdutos(lista_produtos, adapterProdutos)

        binding.buttomTodos.setOnClickListener{
            clicked = true
            if(clicked){
                binding.buttomTodos.setBackgroundResource(R.drawable.button_menu_enable)
                binding.buttomTodos.setTextColor(Color.WHITE)
                binding.buttomAcessorios.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomAcessorios.setTextColor(Color.DKGRAY)
                binding.buttomCalcados.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomCalcados.setTextColor(Color.DKGRAY)
                binding.buttomCamisas.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomCamisas.setTextColor(Color.DKGRAY)
                binding.buttomBermudas.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomBermudas.setTextColor(Color.DKGRAY)
                lista_produtos.clear()
                recycler_produtos.clearOnScrollListeners()
                db.obterListaDeProdutos(lista_produtos, adapterProdutos)
                binding.txtListTitulo.text = "Todos"
            }
        }

        binding.buttomAcessorios.setOnClickListener{
            clicked = true
            if(clicked){
                binding.buttomAcessorios.setBackgroundResource(R.drawable.button_menu_enable)
                binding.buttomAcessorios.setTextColor(Color.WHITE)
                binding.buttomTodos.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomTodos.setTextColor(Color.DKGRAY)
                binding.buttomCalcados.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomCalcados.setTextColor(Color.DKGRAY)
                binding.buttomCamisas.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomCamisas.setTextColor(Color.DKGRAY)
                binding.buttomBermudas.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomBermudas.setTextColor(Color.DKGRAY)
                lista_produtos.clear()
                recycler_produtos.clearOnScrollListeners()
                db.obterListaDeProdutosFiltrada("acessorios", lista_produtos, adapterProdutos)
                binding.txtListTitulo.text = "Acessórios"
            }
        }

        binding.buttomCalcados.setOnClickListener{
            clicked = true
            if(clicked){
                binding.buttomCalcados.setBackgroundResource(R.drawable.button_menu_enable)
                binding.buttomCalcados.setTextColor(Color.WHITE)
                binding.buttomTodos.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomTodos.setTextColor(Color.DKGRAY)
                binding.buttomAcessorios.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomAcessorios.setTextColor(Color.DKGRAY)
                binding.buttomCamisas.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomCamisas.setTextColor(Color.DKGRAY)
                binding.buttomBermudas.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomBermudas.setTextColor(Color.DKGRAY)
                lista_produtos.clear()
                recycler_produtos.clearOnScrollListeners()
                db.obterListaDeProdutosFiltrada("calcados", lista_produtos, adapterProdutos)
                binding.txtListTitulo.text = "Calçados"
            }
        }

        binding.buttomCamisas.setOnClickListener{
            clicked = true
            if(clicked){
                binding.buttomCamisas.setBackgroundResource(R.drawable.button_menu_enable)
                binding.buttomCamisas.setTextColor(Color.WHITE)
                binding.buttomTodos.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomTodos.setTextColor(Color.DKGRAY)
                binding.buttomAcessorios.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomAcessorios.setTextColor(Color.DKGRAY)
                binding.buttomCalcados.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomCalcados.setTextColor(Color.DKGRAY)
                binding.buttomBermudas.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomBermudas.setTextColor(Color.DKGRAY)
                lista_produtos.clear()
                recycler_produtos.clearOnScrollListeners()
                db.obterListaDeProdutosFiltrada("camisas", lista_produtos, adapterProdutos)
                binding.txtListTitulo.text = "Camisas"
            }
        }

        binding.buttomBermudas.setOnClickListener{
            clicked = true
            if(clicked){
                binding.buttomBermudas.setBackgroundResource(R.drawable.button_menu_enable)
                binding.buttomBermudas.setTextColor(Color.WHITE)
                binding.buttomTodos.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomTodos.setTextColor(Color.DKGRAY)
                binding.buttomAcessorios.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomAcessorios.setTextColor(Color.DKGRAY)
                binding.buttomCalcados.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomCalcados.setTextColor(Color.DKGRAY)
                binding.buttomCamisas.setBackgroundResource(R.drawable.button_menu_disable)
                binding.buttomCamisas.setTextColor(Color.DKGRAY)
                lista_produtos.clear()
                recycler_produtos.clearOnScrollListeners()
                db.obterListaDeProdutosFiltrada("bermudas", lista_produtos, adapterProdutos)
                binding.txtListTitulo.text = "Bermudas"
            }
        }

       binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
           override fun onQueryTextSubmit(query : String?): Boolean {

               return true;


           }

           override fun onQueryTextChange(query : String): Boolean {
               lista_produtos.clear()
               adapterProdutos.lista_produtos.clear()
               recycler_produtos.clearOnScrollListeners()
               db.obterListaDeProdutosSearchView(query, lista_produtos, adapterProdutos)
               adapterProdutos.notifyDataSetChanged()

               return true
           }

       })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.perfil -> iniciarDialogPerfilUsuario()
            R.id.pedidos -> iniciarTelaDePedidos()
            R.id.deslogar -> deslogarUsuario()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun iniciarDialogPerfilUsuario(){
        val dialogPerfil = DialogPerfilUsuario(this)
        dialogPerfil.iniciarPerfilUsuario()
        dialogPerfil.recuperarDadosUsuarioBanco()
    }

    private fun iniciarTelaDePedidos(){
        val intent = Intent(this, Pedidos::class.java)
        startActivity(intent)

    }

    private fun deslogarUsuario(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}

