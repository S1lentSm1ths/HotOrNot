package com.vsc.hotornot.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var firstName: String,
    val lastName: String,
    val email: String,
    val gender: Gender,
    val interests: String
)