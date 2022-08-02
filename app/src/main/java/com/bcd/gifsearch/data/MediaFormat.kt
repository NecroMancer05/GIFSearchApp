package com.bcd.gifsearch.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MediaFormat(
    val url: String,
    val size: Number,
    @SerializedName("dims") val dimensions: Array<Number>
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MediaFormat

        if (url != other.url) return false
        if (size != other.size) return false
        if (!dimensions.contentEquals(other.dimensions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = url.hashCode()

        result = 31 * result + size.hashCode()
        result = 31 * result + dimensions.contentHashCode()

        return result
    }
}
