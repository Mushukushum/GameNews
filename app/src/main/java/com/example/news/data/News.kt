package com.example.news.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class News(
    @SerializedName("title")
    var title: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("img")
    var img: String? = null,
    @SerializedName(value = "click_url", alternate = ["url"])
    var click_url: String,
    @SerializedName("time")
    var time: String,
    @SerializedName("top")
    var top: String
):Parcelable