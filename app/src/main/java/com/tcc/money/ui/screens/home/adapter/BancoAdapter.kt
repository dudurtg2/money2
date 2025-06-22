package com.tcc.money.ui.screens.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tcc.money.R
import com.tcc.money.data.models.Banco
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BancoAdapter(private val bancos: List<Banco>) :
    RecyclerView.Adapter<BancoAdapter.BancoViewHolder>() {

    inner class BancoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomeBanco: TextView = view.findViewById(R.id.txtNomeBanco)
        val valorBanco: TextView = view.findViewById(R.id.txtValorBanco)
        val imgBanco: ImageView = view.findViewById(R.id.imgBanco)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BancoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_banco, parent, false)
        return BancoViewHolder(view)
    }

    override fun onBindViewHolder(holder: BancoViewHolder, position: Int) {
        val banco = bancos[position]
        holder.nomeBanco.text = banco.nome
        holder.valorBanco.text = "R$%.2f".format(banco.valor)
        holder.imgBanco.setImageResource(banco.iconeResId)
    }

    override fun getItemCount(): Int = bancos.size
}
