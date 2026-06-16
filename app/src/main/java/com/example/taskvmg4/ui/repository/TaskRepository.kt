package com.example.taskvmg4.ui.repository


import com.example.taskvmg4.ui.model.Task
import com.example.taskvmg4.ui.service.ApiResult
import com.example.taskvmg4.ui.service.TaskService

class TaskRepository(private val api : TaskService) {

    suspend fun findAll(): ApiResult<List<Task>> {
        return try {
            val response = api.getTasks()
            if (response.isSuccessful) {
                ApiResult.Success(response.body() ?: emptyList())
            }
            else {
                ApiResult.Error("Error HTTP ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun findById(id: Int) : ApiResult<Task>
    {
        return try {
            val response = api.getTaskById(id)
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            }
            else {
                ApiResult.Error("Error HTTP ${response.code()}")
            }
        } catch (e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }

    suspend fun save(task: Task) : ApiResult<Task>
    {
        return try {
            val response = api.saveTask(task)
            if (response.isSuccessful && response.body() != null) {
                ApiResult.Success(response.body()!!)
            } else {
                ApiResult.Error("Error HTTP ${response.code()}")
            }
        }
        catch(e: Exception) {
            ApiResult.Error(e.message ?: "Unknown error")
        }
    }
}