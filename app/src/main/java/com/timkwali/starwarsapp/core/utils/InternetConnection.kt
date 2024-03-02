package com.timkwali.starwarsapp.core.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun isInternetAvailable(connectivityManager: ConnectivityManager): Boolean {
    val network = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(network)
    return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}



