package ru.m11_timer_data_storage.repository

import android.content.Context


private const val PREFERENCE_NAME = "m11_timer_data_storage"
private const val PREFERENCE_LOCAL_KEY = "localVal"


class Repository(context: Context) {
    private var localVal: String? = null
    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun saveText(text: String) {
        localVal = text
        preference.edit().putString(PREFERENCE_LOCAL_KEY, text).apply()
    }

    fun clearText() {
        localVal = null
        preference.edit().clear().apply()
    }

    fun getText(): String {
        return when {
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            getDataFromSharedPreference() != null -> getDataFromSharedPreference()!!
            else -> ""
        }
    }

    private fun getDataFromSharedPreference() = preference.getString(PREFERENCE_LOCAL_KEY, null)

    private fun getDataFromLocalVariable() = localVal
}