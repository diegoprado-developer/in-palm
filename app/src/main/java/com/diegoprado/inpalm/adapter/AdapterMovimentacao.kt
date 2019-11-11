package com.diegoprado.inpalm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diegoprado.inpalm.R
import com.diegoprado.inpalm.model.Movimentacao
import kotlinx.android.synthetic.main.activity_movimentacao.view.*

class AdapterMovimentacao(val movimentacoes: List<Movimentacao>,
                          val context: Context): RecyclerView.Adapter<AdapterMovimentacao.MyViewHolder>()
{

    val itemCount: Int?
        get() = movimentacoes!!.size

    override fun getItemCount(): Int {
        return movimentacoes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLista = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_movimentacao, parent, false)

        return MyViewHolder(itemLista)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movimentacao = movimentacoes!![position]

        holder.titulo!!.setText(movimentacao.descricao)
        holder.valor!!.setText(movimentacao.valor.toString())
        holder.categoria!!.setText(movimentacao.categoria)
        holder.valor!!.setTextColor(context!!.resources.getColor(R.color.colorAccentReceita))

        if(movimentacao.tipo === "d" || movimentacao.tipo.equals("d")){
            holder.valor!!.setTextColor(context!!.resources.getColor(R.color.colorAccentDespesa))
            holder.valor!!.text = "-" + movimentacao.valor
        }
    }


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        internal var titulo: TextView? = null
        internal var valor: TextView? = null
        internal var categoria: TextView? = null

        init {
            titulo = itemView.findViewById(R.id.textAdapterTitulo)
            valor = itemView.findViewById(R.id.textAdapterValor)
            categoria = itemView.findViewById(R.id.textAdapterCategoria)
        }
    }

}