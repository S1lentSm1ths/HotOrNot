package com.vsc.hotornot.model

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    var firstName: String,
    val lastName: String,
    val email: String,
    val gender: String,
    val interests: String
)
