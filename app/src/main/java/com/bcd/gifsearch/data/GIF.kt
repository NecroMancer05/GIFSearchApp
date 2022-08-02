package com.bcd.gifsearch.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GIF(
    val id: String,
    val created: Float,
    @SerializedName("content_description") val description: String,
    @SerializedName("itemurl") val itemUrl: String,
    val url: String,
    val tags: Array<String>,
    @SerializedName("hasaudio") val hasAudio: Boolean,
    @SerializedName("media_formats") val mediaFormats: MediaFormats
) : Parcelable
