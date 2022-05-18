package com.vsc.hotornot

import android.content.Context
import android.provider.ContactsContract
import androidx.fragment.app.FragmentActivity
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val USER_SHARED_PREFERENCES_KEY = "userSharedPreferences"

class UserSharedPreferences(activity: FragmentActivity?) {

    private val userSharedPreferences = activity?.getSharedPreferences(
        USER_SHARED_PREFERENCES_KEY,
        Context.MODE_PRIVATE
    )

    fun saveUserData(user: User) {
        val userData = Json.encodeToString(user)
        userSharedPreferences?.edit()?.putString(USER_SHARED_PREFERENCES_KEY, userData)?.apply()
    }

    fun getUserData(): User? {
        val userData =
            userSharedPreferences?.getString(USER_SHARED_PREFERENCES_KEY, null) ?: return null
        return Json.decodeFromString(userData)
    }

    fun deleteUser() {
        userSharedPreferences?.edit()?.remove(USER_SHARED_PREFERENCES_KEY)?.apply()
    }
}