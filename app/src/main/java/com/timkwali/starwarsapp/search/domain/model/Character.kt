package com.timkwali.starwarsapp.search.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Character(
    val url: String,
    val name: String,
    val birthYear: String,
    val id: String = url.substring(url.length - 2, url.length - 1)
): Parcelable
