package com.timkwali.starwarsapp.core.data.api.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<Result?>?
)