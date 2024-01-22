package com.example.taskmanagermvvm

class TaskRepository {
    private val tasks = mutableListOf<Task>()

    fun getAllTasks(): List<Task> {
        return tasks
    }

    fun addTask(task: Task) {
        tasks.add(task)
    }

    fun deleteTask(taskId: Int) {
        tasks.removeAll { it.id == taskId}
    }


}