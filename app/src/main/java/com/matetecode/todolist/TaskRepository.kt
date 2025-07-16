package com.matetecode.todolist

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskRepository(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("task_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Constante para la clave donde se guardará la lista de tareas
    private val TASKS_KEY = "tasks_list"


    // Carga la lista de tareas desde SharedPreferences.
    fun loadTasks(): MutableList<Task> {
        val json = sharedPreferences.getString(TASKS_KEY, null)
        return if (json != null) {
            // Define el tipo de dato List<Task> para que Gson sepa cómo deserializar
            val type = object : TypeToken<MutableList<Task>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf() // Retorna una lista vacía si no hay nada guardado
        }
    }

    // Guarda la lista actual de tareas en SharedPreferences.
    fun saveTasks(tasks: List<Task>) {
        val json = gson.toJson(tasks)
        sharedPreferences.edit().putString(TASKS_KEY, json).apply()
    }
}