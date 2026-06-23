package com.example.taskvmg4.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
object TaskList
@Serializable
data class TaskDetail(
    val taskId : String
)