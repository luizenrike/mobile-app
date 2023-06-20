package com.loja.swiftshop.activities.DetalhesProduto

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.loja.swiftshop.R
import com.loja.swiftshop.activities.Pagamento.Pagamento
import com.loja.swiftshop.databinding.ActivityDetalhesProdutoBinding

class DetalhesProduto : AppCompatActivity() {

    lateinit var binding : ActivityDetalhesProdutoBinding
    var tamanho_produto = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesProdutoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        //recuperando dados de outra tela que foram enviados
        val foto = intent.extras?.getString("foto")
        val nome = intent.extras?.getString("nome")
        val preco = intent.extras?.getString("preco")
        val categoria = intent.extras?.getString("categoria")

        Glide.with(this).load(foto).into(binding.dtFotoProduto)
        binding.dtNomeProduto.text = nome
        binding.dtPrecoProduto.text = "R$ ${preco}"
        if(categoria == "camisas" || categoria == "bermudas"){
            binding.tamanho1.text = "PP"
            binding.tamanho2.text = "P"
            binding.tamanho3.text = "M"
            binding.tamanho4.text = "G"
            binding.tamanho5.text = "GG"
        }else if(categoria == "acessorios"){
            binding.dtTituloTamanho.text = "Categoria de tamanho único, escolha 'U'"
            binding.tamanho1.text = ""
            binding.tamanho1.setBackgroundResource(R.color.transparent)
            binding.tamanho2.text = ""
            binding.tamanho2.setBackgroundResource(R.color.transparent)
            binding.tamanho3.text = "U"
            binding.tamanho4.text = ""
            binding.tamanho4.setBackgroundResource(R.color.transparent)
            binding.tamanho5.text = ""
            binding.tamanho5.setBackgroundResource(R.color.transparent)
        }

        binding.buttonFinalizarPedido.setOnClickListener{
            val tamanho_1 = binding.tamanho1
            val tamanho_2 = binding.tamanho2
            val tamanho_3 = binding.tamanho3
            val tamanho_4 = binding.tamanho4
            val tamanho_5 = binding.tamanho5

            when(true){
                tamanho_1.isChecked -> tamanho_produto = tamanho_1.text.toString()
                tamanho_2.isChecked -> tamanho_produto = tamanho_2.text.toString()
                tamanho_3.isChecked -> tamanho_produto = tamanho_3.text.toString()
                tamanho_4.isChecked -> tamanho_produto = tamanho_4.text.toString()
                tamanho_5.isChecked -> tamanho_produto = tamanho_5.text.toString()
                else -> {}
            }
            if(!tamanho_1.isChecked && !tamanho_2.isChecked && !tamanho_3.isChecked &&
                !tamanho_4.isChecked && !tamanho_5.isChecked){
                val snackbar = Snackbar.make(it, "Escolha um tamanho", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }else{
                val intent = Intent(this, Pagamento::class.java)
                intent.putExtra("tamanho_produto", tamanho_produto)
                intent.putExtra("nome", nome)
                intent.putExtra("preco", preco)
                startActivity(intent)
            }
        }

    }
}