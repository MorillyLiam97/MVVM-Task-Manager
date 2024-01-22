package com.example.taskmanagermvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {

    // Factory method called by ViewModelProvider to create an instance of ViewModel
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the requested ViewModel class is TaskViewModel
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            // Create and return a new instance of TaskViewModel with the provided repository
            return TaskViewModel(repository) as T
        }

        // Throw an exception if an unknown ViewModel class is requested
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
