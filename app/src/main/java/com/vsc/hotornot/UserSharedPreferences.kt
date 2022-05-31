package com.vsc.hotornot

import android.content.Context
import android.content.SharedPreferences
import com.vsc.hotornot.model.Friend

import com.vsc.hotornot.model.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserSharedPreferences(context: Context?) {

    init {
        userSharedPreferences = context?.getSharedPreferences(
            USER_SHARED_PREFERENCES_KEY,
            Context.MODE_PRIVATE
        )
    }

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

    fun setListOfFriends(friends: List<Friend>) {
        val friendsData = Json.encodeToString(friends)
        userSharedPreferences?.edit()?.putString(FRIENDS_SHARED_PREFERENCES_KEY, friendsData)?.apply()
    }

    fun getFriends(): List<Friend>? {
        val friendsData = userSharedPreferences?.getString(FRIENDS_SHARED_PREFERENCES_KEY, null) ?: return null
        return Json.decodeFromString(friendsData)
    }

    companion object {

        private var userSharedPreferences: SharedPreferences? = null
        private const val USER_SHARED_PREFERENCES_KEY = "userSharedPreferences"
        private const val FRIENDS_SHARED_PREFERENCES_KEY = "friendSharedPreferences"
        private var instance: UserSharedPreferences? = null

        fun getInstance(context: Context?): UserSharedPreferences {
            if (instance == null) {
                instance = UserSharedPreferences(context)
            }
            return instance as UserSharedPreferences
        }
    }
}