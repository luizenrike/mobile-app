package com.loja.swiftshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loja.swiftshop.databinding.PedidoItemBinding
import com.loja.swiftshop.model.Pedido

class AdapterPedido(val context: Context, val lista_pedidos : MutableList<Pedido>) :
    RecyclerView.Adapter<AdapterPedido.PedidoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val item_lista = PedidoItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return PedidoViewHolder(item_lista)
    }

    override fun getItemCount() = lista_pedidos.size

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        holder.txtEndereco.text = lista_pedidos.get(position).endereco
        holder.txtCelular.text = lista_pedidos.get(position).celular
        holder.txtNome.text = lista_pedidos.get(position).produto
        holder.txtPreco.text = lista_pedidos.get(position).preco
        holder.txtTamanhoProduto.text = lista_pedidos.get(position).tamanho
        holder.txtStatusPagamento.text = lista_pedidos.get(position).status_pagamento
        holder.txtStatusEntrega.text = lista_pedidos.get(position).status_entrega
    }
    inner class PedidoViewHolder(binding : PedidoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val txtEndereco = binding.txtEndereco
        val txtCelular = binding.txtCelular
        val txtNome = binding.txtNomeProduto
        val txtPreco = binding.txtPrecoProduto
        val txtTamanhoProduto = binding.txtTamanhoProduto
        val txtStatusPagamento = binding.txtStatusPagamento
        val txtStatusEntrega = binding.txtStatusEntrega


    }
}