package com.matetecode.todolist

data class Task(
    val id: String,
    val description: String,
    var isCompleted: Boolean = false
)