package com.attractions

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp


private const val API_KEY = "1ab58c34-c4c7-4fc9-81a4-cc68b9159109"

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey(API_KEY)
    }
}