package com.example.taskvmg4.ui.repository

import com.example.taskvmg4.ui.service.RetrofitClient

object ServiceLocator {

    private val taskApi =
        RetrofitClient.instance

    val taskRepository =
        TaskRepository(taskApi)
}