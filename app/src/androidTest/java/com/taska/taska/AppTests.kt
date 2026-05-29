package com.taska.taska

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAncestors
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import kotlin.jvm.Throws

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppTests {
  @get:Rule
  val composeTestRule = createAndroidComposeRule<MainActivity>()


  fun d() {
    Thread.sleep(2000)
  }

  //Acessar app
  @Test
  fun `1`() {
    d()
    composeTestRule.onNodeWithText("HOME").assertIsDisplayed()
    d()
    composeTestRule.onNodeWithText("Cadastrar \natividade").assertIsDisplayed()
    d()
    composeTestRule.onNodeWithText("Ver \nKanban").assertIsDisplayed()
    d()
    composeTestRule.onNodeWithText("Gerar \nrelatório").assertIsDisplayed()
    d()
  }


  //Navegar para cadastro
  @Test
  fun `2`() {
    d()
    composeTestRule.onNodeWithText("Cadastrar \natividade").performClick()
    d()
    composeTestRule.onNodeWithText("CADASTRAR ATIVIDADE").assertIsDisplayed()
    d()
  }

  //Validar campo vazio
  @Test
  fun `3`() {
    d()
    composeTestRule.onNodeWithText("Cadastrar \natividade").performClick()
    d()

    composeTestRule.onNodeWithText("Mensagem de erro:").assertIsNotDisplayed()
    d()

//    composeTestRule.onNodeWithText("Título da tarefa").performTextInput()
    composeTestRule.onNodeWithText("Cadastrar").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("Mensagem de erro:").assertIsDisplayed()
    d()
  }


  //Cadastrar atividade
  @Test
  fun `4`() {
    d()
    composeTestRule.onNodeWithText("Cadastrar \natividade").performClick()
    d()
    composeTestRule.onNodeWithText("Título da tarefa").performTextInput("Atividade 01")
    composeTestRule.onNodeWithText("Cadastrar").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KANBAN").assertIsDisplayed()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNode(hasTestTag("todo_column_Atividade 01") and hasAnyChild(hasText("Atividade 01")))
      .assertIsDisplayed()

  }


  //Ver kanban
  @Test
  fun `5`() {
    d()
    composeTestRule.onNodeWithText("Ver \nKanban").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KANBAN").assertIsDisplayed()
    d()
    composeTestRule.onNodeWithText("TO DO").assertIsDisplayed()
    d()
    composeTestRule.onNodeWithText("DOING").assertIsDisplayed()
    d()
    composeTestRule.onNodeWithText("DONE").assertIsDisplayed()
    d()
  }


  //Validar inserção
  @Test
  fun `6`() {
    d()
    composeTestRule.onNodeWithText("Ver \nKanban").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KANBAN").assertIsDisplayed()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNode(hasTestTag("todo_column_Atividade 01") and hasAnyChild(hasText("Atividade 01")))
      .assertIsDisplayed()
  }


  //Avançar atividade (1)
  @Test
  fun `7`() {
    d()

    composeTestRule.onNodeWithText("Ver \nKanban").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KANBAN").assertIsDisplayed()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithContentDescription("Atividade 01-TODO-NEXT").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNode(
      hasTestTag("doing_column_Atividade 01") and hasAnyChild(hasText("Atividade 01"))
    ).assertIsDisplayed()
  }


  //Avançar de novo
  @Test
  fun `8`() {
    d()

    composeTestRule.onNodeWithText("Ver \nKanban").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KANBAN").assertIsDisplayed()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithContentDescription("Atividade 01-DOING-NEXT").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNode(
      hasTestTag("done_column_Atividade 01") and hasAnyChild(hasText("Atividade 01"))
    ).assertIsDisplayed()
  }

  //Avançar inválido
  @Test
  fun `9`() {
    d()
    composeTestRule.onNodeWithText("Ver \nKanban").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KANBAN").assertIsDisplayed()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithContentDescription("Atividade 01-DONE-NEXT").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNode(
      hasTestTag("done_column_Atividade 01") and hasAnyChild(hasText("Atividade 01"))
    ).assertIsDisplayed()
  }


  //voltar atividade
  @Test
  fun `10`() {
    d()
    composeTestRule.onNodeWithText("Ver \nKanban").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KANBAN").assertIsDisplayed()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithContentDescription("Atividade 01-DONE-PREVIOUS").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNode(
      hasTestTag("doing_column_Atividade 01") and hasAnyChild(hasText("Atividade 01"))
    ).assertIsDisplayed()
  }


  //voltar de novo
  @Test
  fun `11`() {
    d()
    composeTestRule.onNodeWithText("Ver \nKanban").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KANBAN").assertIsDisplayed()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithContentDescription("Atividade 01-DOING-PREVIOUS").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNode(
      hasTestTag("todo_column_Atividade 01") and hasAnyChild(hasText("Atividade 01"))
    ).assertIsDisplayed()
  }


  //voltar inválido
  @Test
  fun `12`() {
    d()
    composeTestRule.onNodeWithText("Ver \nKanban").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KANBAN").assertIsDisplayed()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithContentDescription("Atividade 01-TODO-PREVIOUS").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNode(
      hasTestTag("todo_column_Atividade 01") and hasAnyChild(hasText("Atividade 01"))
    ).assertIsDisplayed()
  }


  //Cadastrar atividade
  @Test
  fun `13`() {
    d()
    composeTestRule.onNodeWithText("Cadastrar \natividade").performClick()
    d()
    composeTestRule.onNodeWithText("Título da tarefa").performTextInput("Atividade 02")
    composeTestRule.onNodeWithText("Cadastrar").performClick()
    d()

    composeTestRule.onNodeWithText("Cadastrar \natividade").performClick()
    d()
    composeTestRule.onNodeWithText("Título da tarefa").performTextInput("Atividade 03")
    d()
    composeTestRule.onNodeWithText("Cadastrar").performClick()
    d()
    composeTestRule.waitForIdle()
    composeTestRule.onNodeWithText("KANBAN").assertIsDisplayed()
    composeTestRule.onNodeWithText("Atividade 02").assertIsDisplayed()
    d()
    composeTestRule.onNodeWithText("Atividade 03").assertIsDisplayed()

    composeTestRule.onNode(hasTestTag("todo_column_Atividade 02") and hasAnyChild(hasText("Atividade 02")))
      .assertIsDisplayed()
    d()

    composeTestRule.onNode(hasTestTag("todo_column_Atividade 03") and hasAnyChild(hasText("Atividade 03")))
      .assertIsDisplayed()
    d()

    d()
  }


  //Cadastrar atividade
  @Test
  fun `14`() {
    d()
    composeTestRule.onNodeWithText("Ver \nKanban").performClick()
    d()
    composeTestRule.onNodeWithContentDescription("Atividade 02-TODO-NEXT").performClick()
    d()
    composeTestRule.onNodeWithContentDescription("Atividade 03-TODO-NEXT").performClick()
    d()
    composeTestRule.onNodeWithContentDescription("Atividade 03-DOING-NEXT").performClick()
    d()

    composeTestRule.onNode(hasTestTag("todo_column_Atividade 01") and hasAnyChild(hasText("Atividade 01")))
      .assertIsDisplayed()
    d()
    composeTestRule.onNode(hasTestTag("doing_column_Atividade 02") and hasAnyChild(hasText("Atividade 02")))
      .assertIsDisplayed()
    d()
    composeTestRule.onNode(hasTestTag("done_column_Atividade 03") and hasAnyChild(hasText("Atividade 03")))
      .assertIsDisplayed()
    d()
  }


  //GErar relatório
  @Test
  fun `15`() {
    d()
    composeTestRule.onNodeWithText("Gerar \nrelatório").performClick()
    d()
  }

  @Test
  fun `16`() {
    composeTestRule.onNodeWithText("DSALDJSALKD").assertIsDisplayed()
  }


  @Test
  fun `17`() {
    composeTestRule.onNodeWithText("DSALDJSALKD").assertIsDisplayed()
  }
}