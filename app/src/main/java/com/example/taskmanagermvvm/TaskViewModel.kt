package com.example.taskmanagermvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    // LiveData to hold the list of tasks
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    // Initialize the ViewModel
    init {
        // Initialize tasks using a coroutine to ensure it's done asynchronously
        CoroutineScope(Dispatchers.IO).launch {
            // Fetch tasks from the repository and update the LiveData
            _tasks.postValue(repository.getAllTasks())
        }
    }

    // Add a new task to the list
    fun addTask(task: Task) {
        // Use a coroutine to perform the operation asynchronously
        CoroutineScope(Dispatchers.IO).launch {
            // Add the task to the repository
            repository.addTask(task)
            // Fetch the updated list of tasks from the repository and update the LiveData
            _tasks.postValue(repository.getAllTasks())
        }
    }

    // Delete a task with the specified ID
    fun deleteTask(taskId: Int) {
        // Use a coroutine to perform the operation asynchronously
        CoroutineScope(Dispatchers.IO).launch {
            // Delete the task from the repository
            repository.deleteTask(taskId)
            // Fetch the updated list of tasks from the repository and update the LiveData
            _tasks.postValue(repository.getAllTasks())
        }
    }
}

