package ru.user_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val jsonCat = """{
//            "id":12,
//            "url":"FromJson"
//        }""".trimIndent()
//
//
//        val jsonAdapter = Gson().getAdapter(CatImageModel::class.java)
//        val catImageModel = jsonAdapter.fromJson(jsonCat)
//
//        val test = 1
    }
}

