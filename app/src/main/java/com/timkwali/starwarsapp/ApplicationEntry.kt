package com.timkwali.starwarsapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationEntry: Application()