package com.example.lab08tarea

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import com.example.lab08tarea.ui.theme.Lab08TareaTheme
import com.example.lab08tarea.ui.theme.TaskViewModel
import com.example.lab08tarea.ui.theme.TaskViewModelFactory

class MainActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(TaskDatabase.getDatabase(this).taskDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Solicitar permiso para notificaciones en Android 13 o superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
        }

        setContent {
            Lab08TareaTheme {
                TaskForm()
            }
        }
    }
}


@Composable
fun TaskForm() {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Asegurar que se aplique el fondo aquí
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barra superior de título
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFF283593)), // Color azul
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Add New Does",
                fontSize = 24.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de título
        BasicTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFFE0E0E0)), // Color gris claro
            textStyle = TextStyle(fontSize = 18.sp),
            decorationBox = { innerTextField ->
                if (title.isEmpty()) {
                    Text("Add Title", color = Color.Gray, fontSize = 18.sp)
                }
                innerTextField()
            }
        )

        // Campo de descripción
        BasicTextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFFE0E0E0)), // Color gris claro
            textStyle = TextStyle(fontSize = 18.sp),
            decorationBox = { innerTextField ->
                if (description.isEmpty()) {
                    Text("Description", color = Color.Gray, fontSize = 18.sp)
                }
                innerTextField()
            }
        )

        // Campo de objetivo
        BasicTextField(
            value = target,
            onValueChange = { target = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color(0xFFE0E0E0)), // Color gris claro
            textStyle = TextStyle(fontSize = 18.sp),
            decorationBox = { innerTextField ->
                if (target.isEmpty()) {
                    Text("Target", color = Color.Gray, fontSize = 18.sp)
                }
                innerTextField()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Crear Tarea
        Button(
            onClick = { /* Agregar lógica */ },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF06292) // Color rosa brillante
            ),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Create Now", fontSize = 18.sp, color = Color.White)
        }

        // Botón Cancelar
        Button(
            onClick = { /* Lógica de cancelar */ },
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD3D3D3) // Color gris para cancelar
            ),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Cancel", fontSize = 18.sp, color = Color.Black)
        }
    }
}

@Composable
fun TaskList(taskViewModel: TaskViewModel) {
    val tasks by taskViewModel.tasks.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        tasks.forEach { task ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.title,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = if (task.isCompleted) "Completado" else "Pendiente",
                    color = if (task.isCompleted) Color.Green else Color.Red,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }
    }
}




