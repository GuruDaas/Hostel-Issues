package com.hostel.Issues.data.model

data class Student(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val roomNumber: String = "",
    val block: String = "",       // Add this line
    val messType: String = ""     // Add this line
)
