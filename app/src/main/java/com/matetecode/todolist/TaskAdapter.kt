package com.matetecode.todolist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val onDeleteClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val currentTasks = mutableListOf<Task>()

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTextView: TextView = itemView.findViewById(R.id.taskTextView)
        val deleteTaskButton: Button = itemView.findViewById(R.id.deleteTaskButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = currentTasks[position]
        holder.taskTextView.text = task.description
        Log.d("TaskAdapter", "Binding task at position $position: ${task.description}")

        holder.deleteTaskButton.setOnClickListener {
            onDeleteClick(task)
        }
    }

    override fun getItemCount(): Int {
        return currentTasks.size
    }

    fun updateTasks(newTasksList: List<Task>) {
        currentTasks.clear()
        currentTasks.addAll(newTasksList)
        notifyDataSetChanged()
        Log.d("TaskAdapter", "updateTasks called. New list size: ${currentTasks.size}")
    }

    fun removeTask(task: Task) {
        val position = currentTasks.indexOf(task)
        if (position != -1) {
            currentTasks.removeAt(position)
            notifyItemRemoved(position)
            Log.d("TaskAdapter", "Task removed at position $position. New list size: ${currentTasks.size}")
        }
    }
}