package com.hostel.Issues.models.response

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val roomNumber: String,
    val role: String,
    // optional fields returned by some API responses
    val block: String? = null,
    val messType: String? = null
)
