package com.matetecode.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskRepository: TaskRepository
    private val taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa el repositorio
        taskRepository = TaskRepository(applicationContext)

        val recyclerViewTasks: RecyclerView = findViewById(R.id.recyclerViewTasks)
        val newTaskEditText: EditText = findViewById(R.id.newTaskEditText)
        val addTaskButton: Button = findViewById(R.id.addTaskButton)

        // Inicializa el adaptador y le pasa la lógica de eliminación
        taskAdapter = TaskAdapter { taskToDelete ->
            taskList.remove(taskToDelete)
            taskRepository.saveTasks(taskList)
            taskAdapter.updateTasks(taskList)
            Toast.makeText(this, "Tarea '${taskToDelete.description}' eliminada", Toast.LENGTH_SHORT).show()
        }

        // Configura el RecyclerView con un LayoutManager y el adaptador
        recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        recyclerViewTasks.adapter = taskAdapter

        // Añade tareas de muestra y actualiza el adaptador
        //addSampleTasks()

        // Cargamos las tareas guardadas al inicio
        loadTasks()


        // Configura el listener del botón Añadir
        addTaskButton.setOnClickListener {
            val taskDescription = newTaskEditText.text.toString().trim()
            if (taskDescription.isNotEmpty()) {
                val newTask = Task(id = UUID.randomUUID().toString(), description = taskDescription)
                taskList.add(0, newTask)
                taskRepository.saveTasks(taskList)
                taskAdapter.updateTasks(taskList) // Actualiza el adaptador
                newTaskEditText.text.clear()
                Toast.makeText(this, "Tarea añadida: '$taskDescription'", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "La descripción de la tarea no puede estar vacía", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*
    private fun addSampleTasks() {
        taskList.add(Task(id = UUID.randomUUID().toString(), description = "Comprar víveres", isCompleted = false))
        taskList.add(Task(id = UUID.randomUUID().toString(), description = "Pasear al perro", isCompleted = true))
        taskList.add(Task(id = UUID.randomUUID().toString(), description = "Estudiar Kotlin", isCompleted = false))
        taskAdapter.updateTasks(taskList)
    }
     */

    private fun loadTasks() {
        // Carga las tareas desde el repositorio
        val loadedTasks = taskRepository.loadTasks()
        taskList.clear()
        taskList.addAll(loadedTasks)
        taskAdapter.updateTasks(taskList)
    }
}