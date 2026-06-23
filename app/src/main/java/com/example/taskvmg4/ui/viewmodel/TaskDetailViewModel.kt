package com.example.taskvmg4.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskvmg4.ui.repository.TaskRepository
import com.example.taskvmg4.ui.model.Task
import com.example.taskvmg4.ui.service.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val repository: TaskRepository
) : ViewModel()
{
    private val _state =
        MutableStateFlow<TaskDetailState>(TaskDetailState.Loading)

    val state = _state.asStateFlow()

    var id by mutableStateOf("")
        private set
    var title by mutableStateOf("")
        private set
    var completed by mutableStateOf(false)
        private set
    var description by mutableStateOf("")
        private set

    fun onIdChange(value: String) {
        id = value
    }
    fun onTitleChange(value: String) {
        title = value
    }
    fun onCompletedChange(value: Boolean) {
        completed = value
    }
    fun onDescriptionChange(value: String) {
        description = value
    }

    fun clearForm() {
        id = ""
        title = ""
        completed = false
        description = ""
    }
    fun loadForm(task:Task){
        id = task.id
        title = task.title
        completed = task.completed
        description = task.description
    }

    fun findById(id: String) {
        if (id == "0") {
            clearForm()
            return
        }
        _state.value = TaskDetailState.Loading
        viewModelScope.launch {
            when (val result = repository.findById(id))
            {
                is ApiResult.Success-> {
                    _state.value = TaskDetailState.Success(result.data)
                }
                is ApiResult.Error-> {
                    _state.value = TaskDetailState.Error(result.message)
                }
            }
        }
    }

    fun save() {
        val task = Task(
            id = id,
            title = title,
            completed = completed,
            description = description
        )
        _state.value = TaskDetailState.Loading
        viewModelScope.launch {
            when (val result = repository.save(task)) {
                is ApiResult.Success -> {
                    _state.value = TaskDetailState.Success(result.data)
                }
                is ApiResult.Error -> {
                    _state.value = TaskDetailState.Error(result.message)
                }
            }
        }
    }
}