package com.taska.taska

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Kanban(
  onBackToHome: () -> Unit,
  getTasks: () -> List<Task>,
  onNext: (Task) -> Unit,
  onPrevious: (Task) -> Unit,
  cadastrarAtividade: () -> Unit
) {

  var todo = remember { mutableStateListOf<Task>() }
  var doing = remember { mutableStateListOf<Task>() }
  var done = remember { mutableStateListOf<Task>() }
  fun updateTasks() {
    todo.clear()
    doing.clear()
    done.clear()

    todo.addAll(getTasks().filter { it.status == "todo" })
    doing.addAll(getTasks().filter { it.status == "doing" })
    done.addAll(getTasks().filter { it.status == "done" })
  }
  LaunchedEffect(Unit) {
    updateTasks()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    Box(
      modifier = Modifier.fillMaxSize()
    ) {
      Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,

        verticalArrangement = Arrangement.Bottom
      ) {
        Image(
          painter = painterResource(R.drawable.logo_crop),
          null,
          modifier = Modifier
            .size(400.dp)
            .offset(120.dp, 54.dp)
            .alpha(0f),
          contentScale = ContentScale.Crop
        )
      }
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Row(
          Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            IconButton(onClick = { onBackToHome() }) {
              Icon(Icons.Default.ArrowBack, null)
            }
            Column() {
              Text(
                "Taska",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 28.sp
                )
              )
              Text(
                "KANBAN",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp)
              )
            }
          }
          Image(painterResource(R.drawable.logo_crop), null, modifier = Modifier.size(48.dp))
        }

        Row(
          modifier = Modifier
            .fillMaxHeight(0.8f)
            .fillMaxWidth()
            .padding(vertical = 24.dp, horizontal = 12.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(
            modifier = Modifier
              .fillMaxHeight()

              .weight(1f)

              .clip(RoundedCornerShape(8.dp))

              .background(MaterialTheme.colorScheme.surface)

          ) {
            Column(
              Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                "TO DO",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp)
              )

              Text("A fazer", fontSize = 13.sp)
            }
            LazyColumn(
              modifier = Modifier.padding(12.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              items(todo) {
                Card(
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier.fillMaxWidth(),
                  colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                  ),
                  elevation = CardDefaults.cardElevation(4.dp),

                  ) {

                  Row(
                    Modifier
                      .fillMaxWidth()
                      .testTag("todo_column_${it.title}")
                      .padding(4.dp),
                    horizontalArrangement = Arrangement.Center
                  ) {
                    Text(it.title, textAlign = TextAlign.Center)
                  }
                  Row(
                    Modifier
                      .fillMaxWidth()
                      .background(MaterialTheme.colorScheme.onSurface)
                      .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {

                    Icon(
                      Icons.Default.KeyboardArrowLeft,
                      "${it.title}-TODO-PREVIOUS",
                      tint = MaterialTheme.colorScheme.error,
                      modifier = Modifier.clickable {
                        onPrevious(it)
                        updateTasks()
                      })
                    Icon(
                      Icons.Default.KeyboardArrowRight,
                      "${it.title}-TODO-NEXT",
                      tint = MaterialTheme.colorScheme.secondary,
                      modifier = Modifier.clickable {
                        onNext(it)
                        updateTasks()
                      })
                  }
                }


              }



              item {
                Spacer(Modifier.height(24.dp))
              }
            }
          }
          Column(
            modifier = Modifier
              .fillMaxHeight()

              .testTag("doing_column")
              .weight(1f)

              .clip(RoundedCornerShape(8.dp))

              .background(MaterialTheme.colorScheme.surface)

          ) {
            Column(
              Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
                .padding(4.dp),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                "DOING",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp)
              )
              Text("Em andamento", fontSize = 13.sp)
            }
            LazyColumn(
              modifier = Modifier.padding(12.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              items(doing) {
                Card(
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier.fillMaxWidth(),
                  colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                  ),
                  elevation = CardDefaults.cardElevation(4.dp),

                  ) {

                  Row(
                    Modifier
                      .fillMaxWidth()

                      .testTag("doing_column_${it.title}")
                      .padding(4.dp),
                    horizontalArrangement = Arrangement.Center
                  ) {
                    Text(it.title, textAlign = TextAlign.Center)
                  }
                  Row(
                    Modifier
                      .fillMaxWidth()
                      .background(MaterialTheme.colorScheme.onSurface)
                      .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {

                    Icon(
                      Icons.Default.KeyboardArrowLeft,
                      "${it.title}-DOING-PREVIOUS",
                      tint = MaterialTheme.colorScheme.error,
                      modifier = Modifier.clickable {
                        onPrevious(it)
                        updateTasks()
                      })
                    Icon(
                      Icons.Default.KeyboardArrowRight,
                      "${it.title}-DOING-NEXT",
                      tint = MaterialTheme.colorScheme.secondary,
                      modifier = Modifier.clickable {
                        onNext(it)
                        updateTasks()
                      })
                  }
                }


              }



              item {
                Spacer(Modifier.height(24.dp))
              }
            }
          }



          Column(
            modifier = Modifier
              .fillMaxHeight()

              .testTag("done_column")
              .weight(1f)

              .clip(RoundedCornerShape(8.dp))

              .background(MaterialTheme.colorScheme.surface)

          ) {
            Column(
              Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onSurface)
                .padding(4.dp),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(
                "DONE",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp),
                color = MaterialTheme.colorScheme.background
              )

              Text("Concluído", fontSize = 13.sp,
                color = MaterialTheme.colorScheme.background
                )
            }
            LazyColumn(
              modifier = Modifier.padding(12.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
              items(done) {
                Card(
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier.fillMaxWidth(),
                  colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                  ),
                  elevation = CardDefaults.cardElevation(4.dp),

                  ) {

                  Row(
                    Modifier
                      .fillMaxWidth()
                      .testTag("done_column_${it.title}")
                      .padding(4.dp),
                    horizontalArrangement = Arrangement.Center
                  ) {
                    Text(it.title, textAlign = TextAlign.Center)
                  }
                  Row(
                    Modifier
                      .fillMaxWidth()
                      .background(MaterialTheme.colorScheme.onSurface)
                      .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {

                    Icon(
                      Icons.Default.KeyboardArrowLeft,
                      "${it.title}-DONE-PREVIOUS",
                      tint = MaterialTheme.colorScheme.error,
                      modifier = Modifier.clickable {
                        onPrevious(it)
                        updateTasks()
                      })
                    Icon(
                      Icons.Default.KeyboardArrowRight,
                      "${it.title}-DONE-NEXT",
                      tint = MaterialTheme.colorScheme.secondary,
                      modifier = Modifier.clickable {
                        onNext(it)
                        updateTasks()
                      })
                  }
                }


              }



              item {
                Spacer(Modifier.height(24.dp))
              }
            }
          }
        }



        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

          Button(
            onClick = {
              cadastrarAtividade()
            },
            modifier = Modifier.fillMaxWidth(0.5f),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(Color.Transparent)
          ) {
            Row(
              Modifier
                .fillMaxWidth()
                .background(
                  brush = Brush.horizontalGradient(
                    colors = listOf(
                      MaterialTheme.colorScheme.primary,
                      MaterialTheme.colorScheme.secondary
                    )
                  )
                )
                .padding(vertical = 16.dp, horizontal = 12.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                "Cadastrar \natividade",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
              )
              Icon(Icons.Default.ArrowForward, null)
            }
          }
        }
      }
    }
  }
}