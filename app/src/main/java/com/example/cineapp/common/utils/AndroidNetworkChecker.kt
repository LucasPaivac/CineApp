package com.example.cineapp.common.utils

import android.content.Context

class AndroidNetworkChecker(
    private val context: Context
) : NetworkChecker {

    override fun hasInternet(): Boolean {
        return NetworkUtils.isInternetAvaible(context)
    }
}