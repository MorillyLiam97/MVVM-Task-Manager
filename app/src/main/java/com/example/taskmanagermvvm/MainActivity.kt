package com.example.taskmanagermvvm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), AddTaskDialogFragment.TaskDialogListener {

    // Declare variables
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var adapter: TaskAdapter

    private var selectedTask: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView and its adapter
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = TaskAdapter(emptyList(),
            onItemClick = { clickedTask -> handleTaskSelection(clickedTask) },
            onDeleteClick = { selectedTask?.id?.let { taskId -> taskViewModel.deleteTask(taskId) } }
        )

        // Set up RecyclerView with LinearLayoutManager
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Create TaskRepository and TaskViewModel instances
        val repository = TaskRepository()
        taskViewModel = ViewModelProvider(this, TaskViewModelFactory(repository)).get(TaskViewModel::class.java)

        // Observe the LiveData in the ViewModel and update the adapter when the data changes
        taskViewModel.tasks.observe(this, Observer { tasks ->
            adapter.updateTasks(tasks)
        })

        // Set up UI buttons
        val addTaskButton: Button = findViewById(R.id.addTaskButton)
        val deleteTaskButton: Button = findViewById(R.id.deleteTaskButton)

        // Handle addTaskButton click by showing the AddTaskDialogFragment
        addTaskButton.setOnClickListener {
            showAddTaskDialog()
        }

        // Handle deleteTaskButton click by deleting the selected task (if any)
        deleteTaskButton.setOnClickListener {
            selectedTask?.let { task ->
                Log.d("MainActivity", "Deleting task with ID: ${task.id}")
                taskViewModel.deleteTask(task.id)
                selectedTask = null
            }

        }
    }

    // Handle the selection of a task in the RecyclerView
    private fun handleTaskSelection(clickedTask: Task) {
        selectedTask = clickedTask
        Toast.makeText(this, "Task selected: ${clickedTask.title}", Toast.LENGTH_SHORT).show()
    }

    // Show the AddTaskDialogFragment
    private fun showAddTaskDialog() {
        val dialogFragment = AddTaskDialogFragment()
        dialogFragment.setTaskDialogListener(this)
        dialogFragment.show(supportFragmentManager, "AddTaskDialogFragment")
    }

    // Callback from AddTaskDialogFragment when a task is added
    override fun onTaskAdded(title: String) {
        // Create a new task with a unique ID and add it to the ViewModel
        val newTask = Task(
            id = adapter.itemCount + 1,
            title = title,
            description = "Description for $title",
            isCompleted = false
        )
        taskViewModel.addTask(newTask)
    }
}



