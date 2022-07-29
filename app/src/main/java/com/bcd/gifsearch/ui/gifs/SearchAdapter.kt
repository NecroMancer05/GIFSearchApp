package com.bcd.gifsearch.ui.gifs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bcd.gifsearch.data.Result
import com.bcd.gifsearch.databinding.GifRowItemBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class SearchAdapter : ListAdapter<Result, SearchAdapter.SearchViewHolder>(SearchComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {

        val binding = GifRowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

        val currentItem = getItem(position)

        if (currentItem != null)
            holder.bind(currentItem)
    }

    class SearchViewHolder(private val binding: GifRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Result) {

            val drawable = CircularProgressDrawable(itemView.context).apply {
                centerRadius = 64f
                strokeWidth = 8f
                start()
            }

            binding.apply {
                Glide.with(itemView)
                    .load(data.mediaFormats["gif"]?.url)
                    .placeholder(drawable)
                    .into(imageViewGif)

                textViewDescription.text = data.description
                textViewCreated.text = formatDate(data.created)
                textViewTags.text = data.tags.joinToString(", ")
            }
        }

        private fun formatDate(timeStamp: Float): String {

            val sdf = SimpleDateFormat("dd/MM/YYY HH:mm:ss")

            return sdf.format(Date((timeStamp * 1000).toLong()))
        }
    }

    class SearchComparator : DiffUtil.ItemCallback<Result>() {

        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
            oldItem == newItem
    }
}