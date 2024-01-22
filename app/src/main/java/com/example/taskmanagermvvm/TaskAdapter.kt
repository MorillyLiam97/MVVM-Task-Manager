package com.example.taskmanagermvvm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private var tasks: List<Task>,
    private val onItemClick: (Task) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // Selected task variable to track the currently selected task
    private var selectedTask: Task? = null

    // Create view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        // Inflate the layout for an individual task item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    // Bind data to the view holder
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // Get the task at the given position
        val task = tasks[position]
        // Bind the task data to the view holder
        holder.bind(task)

        // Set a click listener for the entire item view
        holder.itemView.setOnClickListener {
            // Update the selectedTask and invoke onItemClick callback
            selectedTask = task
            onItemClick(task)
        }

        // Retrieve the context and delete button from the hosting activity
        val context = holder.itemView.context as? MainActivity
        val deleteButton = context?.findViewById<Button>(R.id.deleteTaskButton)

        // Set a click listener for the delete button
        deleteButton?.setOnClickListener {
            // Invoke onDeleteClick callback with the ID of the task to delete
            onDeleteClick(task.id)
        }
    }

    // Return the total number of tasks
    override fun getItemCount(): Int {
        return tasks.size
    }

    // Update the list of tasks and notify the adapter
    fun updateTasks(updatedTasks: List<Task>) {
        tasks = updatedTasks
        notifyDataSetChanged()
    }

    // Get the list of tasks
    fun getTasks(): List<Task> {
        return tasks
    }

    // View holder class to hold references to views within each task item
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Reference to the title TextView
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

        // Bind data to the views within the view holder
        fun bind(task: Task) {
            titleTextView.text = task.title
        }
    }
}

