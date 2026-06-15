package com.example.taskvmg4.ui.repository


import com.example.taskvmg4.ui.model.Task
import com.example.taskvmg4.ui.service.ApiResult
import com.example.taskvmg4.ui.service.TaskService

class TaskRepository(private val api : TaskService) {

    private val tasks = mutableListOf<Task>(
        Task(1, "Task 1", false,""),
        Task(2, "Task 2", true,""),
        Task(3, "Task 3", false,""),
        Task(4, "Task 4", true,""),
        Task(5, "Task 5", false,"")
    )

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

    fun getTasks(): List<Task>  = tasks

    fun addTask(task: Task) = tasks.add(task)

    fun getTaskId(id: Int): Task? = tasks.find { it.id == id }

    fun removeTask(task: Task) = tasks.remove(task)

    fun toggleTask(task: Task) {
        val index = tasks.indexOf(task)
        if (index != -1) {
            tasks[index] = task.copy(completed = !task.completed)
        }
    }

}