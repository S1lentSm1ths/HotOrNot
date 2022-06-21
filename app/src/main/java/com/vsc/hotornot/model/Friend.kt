package com.vsc.hotornot.model

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    var firstName: String,
    val lastName: String,
    val email: String,
    val gender: Gender,
    val interests: String,
    var rating: String?,
    val characteristics: List<String>,
    val image: Int,
    val uniqueId: String
)
