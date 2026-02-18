package com.example.cineapp

import android.app.Application
import com.example.cineapp.common.utils.AndroidNetworkChecker
import com.example.cineapp.common.utils.NetworkChecker
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CineAppApplication: Application()