package com.dev.foodappchallengebinar

import android.app.Application
import com.dev.foodappchallengebinar.data.source.local.database.AppDatabase

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}