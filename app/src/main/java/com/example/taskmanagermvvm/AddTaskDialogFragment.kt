package com.example.taskmanagermvvm

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AddTaskDialogFragment : DialogFragment() {

    // Interface to communicate with the hosting activity
    interface TaskDialogListener {
        // Callback method invoked when a task is added
        fun onTaskAdded(title: String)
    }

    // Reference to the listener
    private var listener: TaskDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create an AlertDialog
        val builder = AlertDialog.Builder(requireActivity())

        // Get the layout inflater
        val inflater = requireActivity().layoutInflater
        // Inflate the layout for the dialog
        val view = inflater.inflate(R.layout.dialog_add_task, null)

        // Find the EditText for the task title in the inflated layout
        val taskTitleEditText: EditText = view.findViewById(R.id.taskTitleEditText)

        // Set up the AlertDialog with the inflated view
        builder.setView(view)
            .setTitle("Add Task")
            .setPositiveButton("Add") { _, _ ->
                // Positive button click listener - invoke onTaskAdded with the entered title
                val title = taskTitleEditText.text.toString()
                listener?.onTaskAdded(title)
            }
            .setNegativeButton("Cancel", null)

        // Create and return the AlertDialog
        return builder.create()
    }

    // Method to set the TaskDialogListener
    fun setTaskDialogListener(listener: TaskDialogListener) {
        this.listener = listener
    }

    // Called when the fragment is attached to an activity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Ensure the hosting activity implements the TaskDialogListener interface
        if (context is TaskDialogListener) {
            listener = context
        } else {
            // Throw an exception if the hosting activity doesn't implement the interface
            throw ClassCastException("$context must implement TaskDialogListener")
        }
    }

    // Called when the fragment is detached from an activity
    override fun onDetach() {
        super.onDetach()
        // Clear the listener reference to avoid potential memory leaks
        listener = null
    }
}

