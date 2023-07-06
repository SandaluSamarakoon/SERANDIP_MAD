package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.TeaModel

class TeaAdapter (private val teaList: ArrayList<TeaModel>): RecyclerView.Adapter<TeaAdapter.ViewHolder>() {


    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.tea_list_item,parent,false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: TeaAdapter.ViewHolder, position: Int) {


        val currentTea = teaList[position]
        holder.tvTeaName.text = currentTea.teaBrand
    }

    override fun getItemCount(): Int {
        return teaList.size
    }

    class ViewHolder(itemView: View,clickListener:onItemClickListener): RecyclerView.ViewHolder(itemView) {

        val tvTeaName : TextView = itemView.findViewById(R.id.tvTeaName)


        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }

    }
}