package com.vsc.hotornot.repository

import android.content.Context
import android.content.res.Resources
import com.vsc.hotornot.R
import com.vsc.hotornot.UserSharedPreferences
import com.vsc.hotornot.model.Friend
import com.vsc.hotornot.model.Gender
import java.util.Collections.shuffle

class FriendRepository(context: Context?, val resources: Resources) {

    private val userSharedPreferences = UserSharedPreferences.getInstance(context)
    var listOfSavedFriends = userSharedPreferences.getFriends()

    private fun createdFriends(): List<Friend> {
        return listOf(
            Friend(
                resources.getString(R.string.third_person_name),
                resources.getString(R.string.third_person_last_name),
                resources.getString(R.string.third_person_email),
                Gender.MAN,
                resources.getString(R.string.third_person_interests),
                resources.getString(R.string.third_person_rating),
                createRandomFriendCharacteristics(),
                R.drawable.third_person_img
            ), Friend(
                resources.getString(R.string.second_person_name),
                resources.getString(R.string.second_person_last_name),
                resources.getString(R.string.second_person_email),
                Gender.MAN,
                resources.getString(R.string.second_person_interests),
                resources.getString(R.string.second_person_rating),
                createRandomFriendCharacteristics(),
                R.drawable.second_person_img
            ), Friend(
                resources.getString(R.string.first_person_name),
                resources.getString(R.string.first_person_last_name),
                resources.getString(R.string.first_person_email),
                Gender.MAN,
                resources.getString(R.string.first_person_interests),
                resources.getString(R.string.first_person_rating),
                createRandomFriendCharacteristics(),
                R.drawable.first_person_img
            )
        )
    }

    private fun createRandomFriendCharacteristics(): List<String> {
        val friendCharacteristics = resources.getStringArray(R.array.characteristics).toList()
        shuffle(friendCharacteristics)
        val characteristicsCount =
            (resources.getInteger(R.integer.one)..friendCharacteristics.size).random()
        val listOfCharacteristics = mutableListOf<String>()
        for (i in resources.getInteger(R.integer.zero)..characteristicsCount) {
            listOfCharacteristics.add(friendCharacteristics[i])
        }
        return listOfCharacteristics
    }

    private fun saveFriends() = userSharedPreferences.setListOfFriends(createdFriends())

    fun checkIfFriendsSaved() {
        when (listOfSavedFriends) {
            null -> saveFriends()
        }
    }

    companion object {

        private var instance: FriendRepository? = null

        fun getInstance(context: Context?, resources: Resources): FriendRepository {
            if (instance == null) {
                instance = FriendRepository(context, resources)
            }
            return instance as FriendRepository
        }
    }
}