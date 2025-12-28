package com.hostel.Issues.models.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val roomNumber: String,
    val phoneNumber: String,
    val role: String = "student"
)
