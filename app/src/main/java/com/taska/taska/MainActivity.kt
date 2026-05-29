package com.taska.taska

import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taska.taska.ui.theme.TaskaTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.UUID
import androidx.core.content.edit
import java.io.File

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

            fun editTask(newTask: Task) {
              val list = getTasks().toMutableList()
              list.removeIf { it.uuid == newTask.uuid }
              list.add(newTask)
              sp.edit { putString("tasks", Json.encodeToString(list)) }
            }


            NavHost(navController, "home") {
              composable("home") {
                Home(
                  cadastrar = { navController.navigate("cadastrar") },
                  verKanban = { navController.navigate("kanban") },
                  gerarRelatorio = {

                    val retorno = gerarRelatorio(getTasks());
                    Toast.makeText(ctx, "Relatório gerado salvo em Downloads/$retorno", Toast.LENGTH_SHORT ).show()}
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
              composable("kanban") {
                Kanban(
                  onBackToHome = { navController.navigate("home") },
                  onPrevious = { task ->
                    when (task.status) {
                      "todo" -> Toast.makeText(
                        ctx, "Não é possível mover uma atividade para antes de TODO",
                        Toast.LENGTH_SHORT
                      ).show()

                      "doing" -> editTask(task.copy(status = "todo"))
                      "done" -> editTask(task.copy(status = "doing"))

                    }


                  },
                  onNext = { task ->
                    when (task.status) {
                      "todo" -> editTask(task.copy(status = "doing"))
                      "doing" -> editTask(task.copy(status = "done"))
                      "done" -> Toast.makeText(
                        ctx, "Não é possível mover uma atividade para depois de DONE",
                        Toast.LENGTH_SHORT
                      ).show()


                    }


                  },
                  getTasks = {
                    getTasks()

                  },

                  cadastrarAtividade = { navController.navigate("cadastrar") }
                )
              }

            }
          }
        }
      }
    }
  }
}

fun gerarRelatorio(task: List<Task>): String {
  val todo = task.filter { it.status == "todo" }
  val doing = task.filter { it.status == "doing" }
  val done = task.filter { it.status == "done" }

  val string = """
To do 
${todo.joinToString("\n") { "  -${it.title}" }}
   
Doing 
${doing.joinToString("\n") { "  -${it.title}" }}
  
Done 
${done.joinToString("\n") { "  -${it.title}" }}
   
  """.trimIndent()

  val fileName = "relatorio_tasks_${System.currentTimeMillis()}.txt"
  val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName)

  file.writeText(string)
  return fileName
}