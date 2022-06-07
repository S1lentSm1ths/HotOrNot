package com.vsc.hotornot.repository

import android.content.Context
import com.vsc.hotornot.UserSharedPreferences
import com.vsc.hotornot.model.User

class UserRepository(context: Context?) {

    private val userSharedPreferences = UserSharedPreferences.getInstance(context)

    fun saveUserData(user: User) = userSharedPreferences.saveUserData(user)

     fun getUser(): User? {
        return userSharedPreferences.getUserData()
    }

    fun checkIfUserExist(navigateToMainScreen: Unit, navigateToRegistrationScreen: Unit) =
        if (getUser() != null) {
            navigateToMainScreen
        } else {
            navigateToRegistrationScreen
        }

    companion object {

        private var instance: UserRepository? = null

        fun getInstance(context: Context?): UserRepository {
            if (instance == null) {
                instance = UserRepository(context)
            }
            return instance as UserRepository
        }
    }
}