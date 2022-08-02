package com.bcd.gifsearch.ui.gifs

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bcd.gifsearch.R
import com.bcd.gifsearch.databinding.FragmentGifDetailsBinding
import com.bcd.gifsearch.utilities.DateUtils.formatDate
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GIFDetailsFragment : Fragment(R.layout.fragment_gif_details) {

    private val args by navArgs<GIFDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGifDetailsBinding.bind(view)

        binding.apply {

            val gif = args.gif

            Glide.with(this@GIFDetailsFragment)
                .load(gif.mediaFormats["gif"]?.url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false

                        imageView.isVisible = true

                        nameTextView.isVisible = true
                        createdTextView.isVisible = true
                        tagsTextView.isVisible = true
                        shareButton.isVisible = true
                        return false
                    }
                })
                .into(imageView)

            nameTextView.text = "Description: ${gif.description}"
            createdTextView.text = "Created: ${formatDate(gif.created)}"
            tagsTextView.text = "Tags: ${gif.tags.joinToString(", ")}"

            shareButton.setOnClickListener {

                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_STREAM, Uri.parse(gif.mediaFormats["gif"]?.url))
                    type = "image/gif"
                }

                startActivity(Intent.createChooser(shareIntent, null))
            }
        }
    }
}