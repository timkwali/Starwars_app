package com.timkwali.starwarsapp.search.domain.model

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

class AssetParamType : NavType<Character>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Character? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Character {
        return Gson().fromJson(value, Character::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Character) {
        bundle.putParcelable(key, value)
    }
}