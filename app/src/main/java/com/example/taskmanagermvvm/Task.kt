package com.example.taskmanagermvvm

import java.io.Serializable

data class Task(
    val id: Int,
    var title: String,
    var description: String,
    var isCompleted: Boolean
) : Serializable