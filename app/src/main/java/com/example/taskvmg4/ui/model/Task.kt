package com.example.taskvmg4.ui.model

data class Task(
    val id : String,
    val title : String,
    val completed : Boolean = false,
    val description: String
)
