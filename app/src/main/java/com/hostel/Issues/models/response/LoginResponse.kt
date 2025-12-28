package com.hostel.Issues.models.response

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: User?
)
