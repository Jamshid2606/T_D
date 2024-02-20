package com.talabalardaftari.tdcleanhilt.presentation.pagination

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.talabalardaftari.tdcleanhilt.BuildConfig
import com.talabalardaftari.tdcleanhilt.data.main.model.response.GetNoteBooksResponseItem
import com.talabalardaftari.tdcleanhilt.databinding.ItemAddNotebookBinding
import com.talabalardaftari.tdcleanhilt.databinding.ItemNotebookBinding

class UserPaging3Adapter : PagingDataAdapter<GetNoteBooksResponseItem, UserPaging3Adapter.DataVh>(MyDiffUtil()){

    class MyDiffUtil: DiffUtil.ItemCallback<GetNoteBooksResponseItem>(){
        override fun areItemsTheSame(oldItem: GetNoteBooksResponseItem, newItem: GetNoteBooksResponseItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: GetNoteBooksResponseItem, newItem: GetNoteBooksResponseItem): Boolean = oldItem == newItem
    }

    inner class DataVh(val binding: ItemNotebookBinding): RecyclerView.ViewHolder(binding.root){
        fun onBind(data: GetNoteBooksResponseItem){
            binding.apply {
                val uri = BuildConfig.BASE_URL + "/attachment/download?id="+data.attachmentId
                binding.iconNote.setImageURI(uri.toUri())
                binding.nameNote.text = data.name
            }
        }
    }

    override fun onBindViewHolder(holder: DataVh, position: Int) {
        val item = getItem(position)
        if (item!=null){
            holder.onBind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataVh = DataVh(
        ItemNotebookBinding.inflate(LayoutInflater.from(parent.context),parent,false))
}