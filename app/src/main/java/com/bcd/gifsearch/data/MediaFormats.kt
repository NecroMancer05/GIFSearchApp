package com.bcd.gifsearch.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaFormats(val map: HashMap<String, MediaFormat> = hashMapOf()) : Parcelable,
    MutableMap<String, MediaFormat> by map
