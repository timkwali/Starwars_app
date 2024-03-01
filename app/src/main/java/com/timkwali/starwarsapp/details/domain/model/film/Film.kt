package com.timkwali.starwarsapp.details.domain.model.film

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
    val title: String,
    val openingCrawl: String
): Parcelable
