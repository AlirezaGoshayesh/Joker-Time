package com.test.jokertime.data.model


import com.google.gson.annotations.SerializedName

data class JokeModel(
    @SerializedName("category")
    val category: String,
    @SerializedName("joke")
    val joke: String,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("flags")
    val flags: Flags,
    @SerializedName("id")
    val id: Int,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("safe")
    val safe: Boolean,
    @SerializedName("type")
    val type: String
)