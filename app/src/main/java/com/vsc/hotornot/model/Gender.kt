package com.vsc.hotornot.model

import com.vsc.hotornot.R
import kotlinx.serialization.Serializable

@Serializable
enum class Gender(val genderText: Int, val genderIcon: Int) {
    MAN(R.string.man, R.drawable.ic_man),
    WOMAN(R.string.woman, R.drawable.ic_woman),
    OTHER(R.string.other, R.drawable.ic_login_person),
}