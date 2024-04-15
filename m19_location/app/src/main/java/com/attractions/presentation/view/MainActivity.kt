package com.attractions.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.attractions.R
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MapKitFactory.setApiKey("1ab58c34-c4c7-4fc9-81a4-cc68b9159109")
    }
}