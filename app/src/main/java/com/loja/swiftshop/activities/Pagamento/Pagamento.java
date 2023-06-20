package com.loja.swiftshop.activities.Pagamento;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.loja.swiftshop.R;
import com.loja.swiftshop.activities.TelaPrincipal.TelaPrincipal;
import com.loja.swiftshop.databinding.ActivityPagamentoBinding;
import com.loja.swiftshop.model.DB;

public class Pagamento extends AppCompatActivity {
    ActivityPagamentoBinding binding;
    DB db = new DB();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagamentoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        String nome = getIntent().getExtras().getString("nome");
        String preco = getIntent().getExtras().getString("preco");
        String tamanho_produto = getIntent().getExtras().getString("tamanho_produto");

        //Log.d("t", tamanho_produto);

        binding.btFazerPagamento.setOnClickListener(view -> {
            String bairro = binding.editBairro.getText().toString();
            String rua_numero = binding.editRuaENumero.getText().toString();
            String cidade_estado = binding.editCidadeEstado.getText().toString();
            String celular = binding.editCelular.getText().toString();

            if(bairro.isEmpty() || rua_numero.isEmpty() || cidade_estado.isEmpty() || celular.isEmpty()){
                Snackbar snackbar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(Color.RED);
                snackbar.setTextColor(Color.WHITE);
                snackbar.show();
            }else{
                Snackbar snackbar = Snackbar.make(view, "Fazendo pagamento...", Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.GREEN);
                snackbar.setTextColor(Color.WHITE);
                String endereco = "Bairro: " + bairro + " || " + " Rua e Número: " + rua_numero + " || "
                        + "Cidade e Estado: " + cidade_estado;
                String status_pagamento = "Status de Pagamento: " + " " + "Pagamento Aprovado";
                String status_entrega = "Status de Entrega: " + " " + "Em processamento";
                String nomeProduto = "Nome: " + " " + nome;
                String precoProduto = "Preço: " + preco;
                String tamanho = "Tamanho: " + " " + tamanho_produto;
                String celularUser = "Celular: " + " " + celular;
                db.salvarDadosPedidoUsuario(endereco, celularUser, nomeProduto, precoProduto, tamanho, status_pagamento, status_entrega);
                snackbar.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Pagamento.this, TelaPrincipal.class);
                        startActivity(intent);
                    }
                }, 2000);


            }


        });
    }
}