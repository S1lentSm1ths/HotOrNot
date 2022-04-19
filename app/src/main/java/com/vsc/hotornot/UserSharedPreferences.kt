package com.vsc.hotornot

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.vsc.hotornot.Constants.Companion.userSharedPreferencesKey

class UserSharedPreferences : AppCompatActivity() {
    fun getUserData(): String? {
        val userSharedPreferences = getSharedPreferences(
            userSharedPreferencesKey,
            Context.MODE_PRIVATE
        )
        return userSharedPreferences?.getString("first_name", null)
    }

    fun saveUserData(firstNameEditText: TextInputEditText, lastNameEditText: TextInputEditText) {
        val userSharedPreferences = getSharedPreferences(
            userSharedPreferencesKey,
            Context.MODE_PRIVATE
        )
        val editor = userSharedPreferences?.edit()
        editor?.apply {
            putString("first_name", firstNameEditText.toString())
            putString("last_name", lastNameEditText.toString())
        }?.apply()
    }
}