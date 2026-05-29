package com.taska.taska

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taska.taska.ui.theme.TaskaTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.UUID
import kotlin.uuid.Uuid
import androidx.core.content.edit

@Serializable
data class Task(
  val title: String,
  val status: String = "todo",
  val uuid: String = UUID.randomUUID().toString()
)

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      TaskaTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Column(
            modifier = Modifier.padding(innerPadding)
          ) {
            val navController = rememberNavController()
            val ctx = LocalContext.current
            val sp = ctx.getSharedPreferences("prefs", MODE_PRIVATE)

            fun getTasks(): List<Task> {
              val string = sp.getString("tasks", "") ?: ""
              val list = try {
                Json.decodeFromString<List<Task>>(string)
              } catch (e: Exception) {
                emptyList()
              }
              return list
            }

            fun newTask(item: Task) {
              val list = getTasks().toMutableList()
              list.add(item)
              sp.edit { putString("tasks", Json.encodeToString(list)) }
            }


            NavHost(navController, "cadastrar") {
              composable("home") {
                Home(
                  cadastrar = { navController.navigate("cadastrar") },
                  verKanban = { navController.navigate("kanban") },
                  gerarRelatorio = {}
                )
              }

              composable("cadastrar") {
                Cadastrar(
                  onBackToHome = { navController.navigate("home") },
                  onSaveTask = {
                    newTask(
                      Task(
                        title = it
                      )
                    )
                    Toast.makeText(
                      ctx, "Tarefa criada e adicionada com sucesso a coluna TO DO",
                      Toast.LENGTH_LONG
                    ).show()
                    navController.navigate("kanban")
                  }
                )
              }

            }
          }
        }
      }
    }
  }
}