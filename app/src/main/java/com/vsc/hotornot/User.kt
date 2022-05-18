package com.vsc.hotornot

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var firstName: String,
    val lastName: String,
    val email: String,
    val gender: String,
    val interests: String
)