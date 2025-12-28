package com.hostel.Issues.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import android.app.Application
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.runtime.mutableStateListOf
import com.hostel.Issues.data.model.Issue
import com.hostel.Issues.data.model.Student
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class HostelIssuesViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("hostel_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    var loggedInStudent: Student? = null
    var isWardenLoggedIn: Boolean = false
    var loginErrorMessage: String = ""
    var registerErrorMessage: String = ""
    var registerSuccess: Boolean = false

    // use a Snapshot state list so Compose recomposes when issues change
    private val _issues = mutableStateListOf<Issue>()
    val issues: List<Issue> get() = _issues

    init {
        loadIssues()
    }

    private fun loadIssues() {
        val json = prefs.getString("issues", null)
        if (json != null) {
            val type = object : TypeToken<List<Issue>>() {}.type
            val loaded: List<Issue> = gson.fromJson(json, type)
            _issues.addAll(loaded)
        }
    }

    private fun saveIssues() {
        val json = gson.toJson(issues)
        prefs.edit().putString("issues", json).apply()
    }

    fun deleteIssue(issueId: String) {
        _issues.removeAll { it.id == issueId }
        saveIssues()
    }

    // Dummy student login for local/demo flows. Accepts any non-empty email/password
    suspend fun studentLogin(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val e = email.trim()
                if (e.isEmpty() || password.isBlank()) {
                    loginErrorMessage = "Please enter email and password"
                    return@withContext false
                }
                // Create a simple in-memory student using the email as identifier
                val namePart = e.substringBefore('@').replace('.', ' ').replace('_', ' ')
                val displayName = namePart.split(' ').joinToString(" ") { it.replaceFirstChar { ch -> ch.titlecaseChar() } }
                loggedInStudent = Student(
                    id = e.hashCode().toString(),
                    name = if (displayName.isBlank()) "Student" else displayName,
                    email = e,
                    password = "HIDDEN",
                    roomNumber = "101",
                    block = "A",
                    messType = "Veg"
                )
                loginErrorMessage = ""
                true
            } catch (e: Exception) {
                loginErrorMessage = "Error: ${e.message ?: ""}"
                false
            }
        }
    }

    // Dummy register: accept any non-empty fields and mark registerSuccess true
    suspend fun studentRegister(
        name: String,
        email: String,
        password: String,
        roomNumber: String,
        phoneNumber: String,
        role: String = "student"
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                if (name.isBlank() || email.isBlank() || password.isBlank()) {
                    registerErrorMessage = "Please fill required fields"
                    registerSuccess = false
                    return@withContext false
                }
                // For demo, simply mark success. Optionally create a logged in student.
                registerErrorMessage = ""
                registerSuccess = true
                true
            } catch (e: Exception) {
                registerErrorMessage = "Error: ${e.message ?: ""}"
                registerSuccess = false
                false
            }
        }
    }

    fun logout() {
        loggedInStudent = null
    }

    // Simple warden auth for local UI flows. In a real app this would call an API.
    fun wardenLogin(username: String, password: String): Boolean {
        // Credentials are hardcoded in the dashboard UI for demo
        val valid = username == "aman58@gmail.com" && password == "aman58"
        isWardenLoggedIn = valid
        return valid
    }

    // Lookup student from in-memory state. Currently we only track the logged in student.
    fun getStudentById(studentId: String): Student? {
        if (loggedInStudent?.id == studentId) return loggedInStudent
        // no persistent storage implemented yet
        return null
    }

    fun raiseIssue(title: String, description: String, type: String) {
        val issue = Issue(
            id = "I${System.currentTimeMillis()}",
            studentId = loggedInStudent?.id ?: "",
            title = title,
            description = description,
            type = type,
            status = "Pending",
            createdAt = getCurrentDate()
        )
        _issues.add(issue)
        saveIssues()
    }

    fun updateIssueStatus(issueId: String, newStatus: String, remarks: String? = null) {
        val index = _issues.indexOfFirst { it.id == issueId }
        if (index != -1) {
            val curr = _issues[index]
            _issues[index] = curr.copy(
                status = newStatus,
                wardenRemarks = remarks,
                resolvedAt = if (newStatus == "Resolved") getCurrentDate() else null
            )
            saveIssues()
        }
    }

    fun getIssueById(issueId: String): Issue? {
        return _issues.find { it.id == issueId }
    }

    fun getAllStudentIssues(): List<Issue> {
        val studentId = loggedInStudent?.id ?: ""
        return _issues.filter { it.studentId == studentId }
    }

    private fun getCurrentDate(): String {
        return java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a").format(java.util.Date())
    }
}
