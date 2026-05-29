package com.taska.taska

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Cadastrar(onBackToHome: () -> Unit, onSaveTask: (String) -> Unit) {
  var title by remember { mutableStateOf("") }
  var isError by remember { mutableStateOf(false) }
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
            .alpha(0.6f),
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
            IconButton({ onBackToHome() }) {
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
                "CADASTRAR ATIVIDADE",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 4.dp)
              )
            }
          }
          Image(painterResource(R.drawable.logo_crop), null, modifier = Modifier.size(48.dp))
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Image(
            painter = painterResource(R.drawable.logo),
            null,
            modifier = Modifier.fillMaxWidth(0.8f)

          )
        }
        Column(
          modifier = Modifier.fillMaxWidth(0.8f),
          horizontalAlignment = Alignment.End,
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

          OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Título da tarefa") },
            trailingIcon = { Icon(Icons.Default.Task, null) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
          )

          Button(
            onClick = {
              if (title.isEmpty()) {
                isError = true
              } else {
                isError = false
                onSaveTask(title)
              }
            },
            modifier = Modifier.fillMaxWidth(0.5f),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(),
            colors = ButtonDefaults.buttonColors(Color.Transparent)
          ) {
            Row(
              Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                  brush = Brush.horizontalGradient(
                    colors = listOf(
                      MaterialTheme.colorScheme.primary,
                      MaterialTheme.colorScheme.secondary
                    )
                  )
                ),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Text("Cadastrar", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
            }
          }

          AnimatedVisibility(isError) {

            Column(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.error)
                .padding(8.dp)
            ) {
              Text(
                "Mensagem de erro: ",
                color = MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 16.sp
              )
              Text(
                "Esse campo é obrigatório",
                color = MaterialTheme.colorScheme.background,
                fontWeight = FontWeight.SemiBold,

                lineHeight = 16.sp
              )
            }
          }
        }
      }
    }
  }
}
