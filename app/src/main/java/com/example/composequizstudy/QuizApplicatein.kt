package com.example.composequizstudy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class QuizApplicatein: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}