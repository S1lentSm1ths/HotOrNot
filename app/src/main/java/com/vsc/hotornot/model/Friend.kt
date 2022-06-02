package com.vsc.hotornot.model

import android.graphics.drawable.Drawable
import android.media.Image
import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    var firstName: String,
    val lastName: String,
    val email: String,
    val gender: Gender,
    val interests: String,
    val rating: String,
    val characteristics: List<String>,
    val image: Int
)
