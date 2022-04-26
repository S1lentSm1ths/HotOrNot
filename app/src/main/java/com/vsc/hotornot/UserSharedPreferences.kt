//package com.vsc.hotornot
//
//import android.app.Application
//import android.content.Context
//import android.content.SharedPreferences
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.textfield.TextInputEditText
//import com.vsc.hotornot.Constants.Companion.userSharedPreferencesKey
//
//class UserSharedPreferences : Application() {
//
//    fun saveUserData(firstNameEditText: TextInputEditText, lastNameEditText: TextInputEditText) {
//        val userSharedPreferences: SharedPreferences = getSharedPreferences(
//            userSharedPreferencesKey,
//            Context.MODE_PRIVATE
//        )
//        val editor = userSharedPreferences.edit()
//        editor.apply {
//            putString("first_name", firstNameEditText.toString())
//            putString("last_name", lastNameEditText.toString())
//        }.apply()
//    }
//
//    fun getUserFirstName(): String? {
//        val userSharedPreferences: SharedPreferences = getSharedPreferences(
//            userSharedPreferencesKey,
//            Context.MODE_PRIVATE
//        )
//        return userSharedPreferences.getString("first_name", null)
//    }
//
//    fun deleteUser() {
//        val userSharedPreferences = getSharedPreferences(
//            userSharedPreferencesKey,
//            Context.MODE_PRIVATE
//        )
//        val editor = userSharedPreferences.edit()
//        editor.apply() {
//            remove("first_name")
//            remove("last_name")
//        }.apply()
//    }
//}