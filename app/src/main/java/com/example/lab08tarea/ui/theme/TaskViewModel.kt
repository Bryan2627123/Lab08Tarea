package com.example.lab08tarea.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab08tarea.Task
import com.example.lab08tarea.TaskDao
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val dao: TaskDao) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        viewModelScope.launch {
            _tasks.value = dao.getAllTasks()
        }
    }

    fun addTask(title: String, description: String, target: String) {
        val newTask = Task(title = title, description = description, target = target)
        viewModelScope.launch {
            dao.insertTask(newTask)
            _tasks.value = dao.getAllTasks()
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            dao.deleteAllTasks()
            _tasks.value = emptyList()
        }
    }
}

val db = FirebaseFirestore.getInstance()

fun saveTaskToCloud(task: Task) {
    val taskData = hashMapOf(
        "title" to task.title,
        "description" to task.description,
        "isCompleted" to task.isCompleted
    )
    db.collection("tasks").add(taskData)
}

fun getTasksFromCloud() {
    db.collection("tasks").addSnapshotListener { snapshots, e ->
        if (e != null) {
            Log.w("FirebaseError", "Listen failed.", e)
            return@addSnapshotListener
        }

        for (doc in snapshots!!) {
            val task = doc.toObject(Task::class.java)
            // Aquí puedes actualizar la lista de tareas en el ViewModel
        }
    }
}

