package com.kuantum.artbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.kuantum.artbook.databinding.ImageRowBinding
import javax.inject.Inject

class ImageApiAdapter @Inject constructor(
    private val glide: RequestManager,
) : RecyclerView.Adapter<ImageApiAdapter.ImageApiHolder>() {

    class ImageApiHolder(val binding: ImageRowBinding) : RecyclerView.ViewHolder(binding.root)

    var onItemClick: ((String) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var imageList: List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageApiHolder {
        val binding = ImageRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageApiHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageApiHolder, position: Int) {
        glide.load(imageList[position]).into(holder.binding.imageview)

        val url = imageList[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(url)
        }
    }
}