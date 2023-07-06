package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.HotModel

class HotAdapter (private val hotList: ArrayList<HotModel>) : RecyclerView.Adapter<HotAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.hot_list_item,parent,false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {

        val currentHot = hotList[position]
        holder.tvHotName.text = currentHot.hotName

    }

    override fun getItemCount(): Int {
        return hotList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener): RecyclerView.ViewHolder(itemView) {

        val tvHotName : TextView = itemView.findViewById(R.id.tvHotName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}