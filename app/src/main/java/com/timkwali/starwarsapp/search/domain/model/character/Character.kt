package com.timkwali.starwarsapp.search.domain.model.character

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Character(
    val url: String,
    val name: String,
    val birthYear: String,

): Parcelable {
    val id: String get() =
        url.removeSuffix("/")
            .split("/")
            .last()
}
