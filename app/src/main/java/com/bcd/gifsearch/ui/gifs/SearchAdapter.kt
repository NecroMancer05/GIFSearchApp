package com.bcd.gifsearch.ui.gifs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bcd.gifsearch.data.GIF
import com.bcd.gifsearch.databinding.GifRowItemBinding
import com.bcd.gifsearch.utilities.DateUtils.formatDate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class SearchAdapter(private val clickListener: OnItemClickListener) :
    PagingDataAdapter<GIF, SearchAdapter.SearchViewHolder>(SearchComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = GifRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null)
            holder.bind(currentItem)
    }

    inner class SearchViewHolder(private val binding: GifRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)

                    if (item != null)
                        clickListener.onItemClick(item)
                }
            }
        }

        fun bind(data: GIF) {

            val drawable = CircularProgressDrawable(itemView.context).apply {
                centerRadius = 64f
                strokeWidth = 8f
                start()
            }

            binding.apply {
                Glide.with(itemView)
                    .load(data.mediaFormats["gif"]?.url)
                    .placeholder(drawable)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imageViewGif)

                textViewDescription.text = data.description
                textViewCreated.text = formatDate(data.created)
                textViewTags.text = data.tags.joinToString(", ")
            }
        }
    }

    class SearchComparator : DiffUtil.ItemCallback<GIF>() {

        override fun areItemsTheSame(oldItem: GIF, newItem: GIF): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GIF, newItem: GIF): Boolean =
            oldItem == newItem
    }

    interface OnItemClickListener {
        fun onItemClick(gif: GIF)
    }
}