package com.talabalardaftari.tdcleanhilt.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talabalardaftari.tdcleanhilt.databinding.ItemAddNotebookBinding

class AddNoteBookAdapter(private val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<AddNoteBookAdapter.MyViewHolder>() {
    var list = ArrayList<Int>()

    fun setData(list: ArrayList<Int>){
        this.list.clear()
        list.let { this.list.addAll(it) }
        notifyDataSetChanged()
    }

    inner class MyViewHolder(val binding:ItemAddNotebookBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data:Int){
            binding.iconAdd.setImageResource(data)
            binding.root.setOnClickListener {
                onItemClick(layoutPosition)
            }
        }
    }
    override fun getItemCount(): Int =list.size
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.bind(list[position])
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =  MyViewHolder(
        ItemAddNotebookBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )
}