package com.example.cineapp.common.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AndroidNetworkChecker @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkChecker {

    override fun hasInternet(): Boolean {
        return NetworkUtils.isInternetAvaible(context)
    }
}