package com.vsc.hotornot

import android.content.Context
import android.content.SharedPreferences
import android.widget.RadioButton
import com.google.android.material.textfield.TextInputEditText

class UserSharedPreferences(private val context: Context?) {

    private val userSharedPreferencesKey = "userSharedPreferences"

    fun saveUserData(firstNameEditText: TextInputEditText, lastNameEditText: TextInputEditText, gender: String?, interest: String) {
        val userSharedPreferences = context?.getSharedPreferences(
            userSharedPreferencesKey,
            Context.MODE_PRIVATE
        )
        val editor = userSharedPreferences?.edit()
        editor?.apply {
            putString("first_name", firstNameEditText.toString())
            putString("last_name", lastNameEditText.toString())
            putString("gender", gender)
            putString("interest", interest)
        }?.apply()
    }

    fun getUserFirstName(): String? {
        val userSharedPreferences = context?.getSharedPreferences(
            userSharedPreferencesKey,
            Context.MODE_PRIVATE
        )
        return userSharedPreferences?.getString("first_name", null)
    }

    fun deleteUser() {
        val userSharedPreferences = context?.getSharedPreferences(
            userSharedPreferencesKey,
            Context.MODE_PRIVATE
        )
        val editor = userSharedPreferences?.edit()
        editor?.apply() {
            remove("first_name")
            remove("last_name")
            remove("gender")
            remove("interest")
        }?.apply()
    }
}