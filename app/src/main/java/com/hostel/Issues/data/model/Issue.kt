package com.hostel.Issues.data.model

data class Issue(
    val id: String,
    val studentId: String,
    val title: String,
    val description: String,
    val type: String, // "Electricity", "Water", "Cleanliness", "Internet", "Other"
    val status: String, // "Pending", "In Progress", "Resolved"
    val createdAt: String,
    val resolvedAt: String? = null,
    val wardenRemarks: String? = null
)
