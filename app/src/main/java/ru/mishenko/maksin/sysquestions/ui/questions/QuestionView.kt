package ru.mishenko.maksin.sysquestions.ui.questions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.mishenko.maksin.sysquestions.ui.questions.model.AnswerType
import ru.mishenko.maksin.sysquestions.ui.questions.model.QuestionUi
import ru.mishenko.maksin.sysquestions.ui.questions.viewModel.AllQuestionViewModel
import ru.mishenko.maksin.sysquestions.ui.questions.viewModel.QuestionViewModel
import ru.mishenko.maksin.sysquestions.ui.questions.viewModel.RandomQuestionViewModel
import kotlin.math.absoluteValue

@Composable
fun QuestionView(viewModel: QuestionViewModel) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = viewModel.title(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        QuestSelect(
            quests = state.questions,
            selected = state.select,
            onClick = { viewModel.selectQuestion(it) })
        Spacer(modifier = Modifier.height(10.dp))
        state.questions[state.select].View(
            modifier = Modifier
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        viewModel.drag(delta)
                    },
                    onDragStopped = {
                        viewModel.finishDrag()
                    }
                ),
            onAnswer = { questionUi, answers ->
                viewModel.answer(questionUi, answers)
            },
            onSelect = { questionUi, i ->
                viewModel.select(questionUi, i)
            }
        )
    }
}

private fun QuestionViewModel.title(): String = when (this) {
    is AllQuestionViewModel -> "All questions"
    is RandomQuestionViewModel -> "Random questions"
    else -> "Questions"
}

@Composable
fun QuestionUi.View(
    modifier: Modifier = Modifier,
    onAnswer: (QuestionUi, List<Int>) -> Unit,
    onSelect: (QuestionUi, Int) -> Unit
) {
    Column(modifier = modifier) {
        Text(question.text)
        AnswersView(onAnswer, onSelect)
    }
}

@Composable
fun QuestionUi.AnswersView(
    onAnswer: (QuestionUi, List<Int>) -> Unit,
    onSelect: (QuestionUi, Int) -> Unit
) {
    when (this) {
        is QuestionUi.QuestionWithMoreAnswer -> AnswersView(
            onAnswer = onAnswer,
            onSelect = onSelect
        )

        is QuestionUi.QuestionWithOneAnswer -> AnswersView(onAnswer = onAnswer)
    }
}

@Composable
fun QuestionUi.QuestionWithOneAnswer.AnswersView(onAnswer: (QuestionUi, List<Int>) -> Unit) {
    val quest = this
    LazyColumn(contentPadding = PaddingValues(10.dp)) {
        items(question.answers) {
            val index = question.answers.indexOf(it)
            val color =
                when {
                    answerType != AnswerType.Nothing && question.trueAnswersIndex.contains(index) -> Color.Green.copy(
                        alpha = 0.5f
                    )

                    answerType == AnswerType.Fail && answer == index -> Color.Red.copy(alpha = 0.5f)
                    else -> Color.Gray
                }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color)
                    .clickable { onAnswer(quest, listOfNotNull(index)) }
            ) {
                Text(text = it)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun QuestionUi.QuestionWithMoreAnswer.AnswersView(
    onAnswer: (QuestionUi, List<Int>) -> Unit,
    onSelect: (QuestionUi, Int) -> Unit
) {
    val quest = this
    LazyColumn(contentPadding = PaddingValues(10.dp)) {
        items(question.answers) {
            val index = question.answers.indexOf(it)
            val color =
                when {
                    answerType == AnswerType.Nothing && selectedAnswers.contains(index) -> Color.Blue
                    answerType != AnswerType.Nothing && question.trueAnswersIndex.contains(index) -> Color.Green.copy(
                        alpha = 0.5f
                    )

                    answerType == AnswerType.Fail && answers?.contains(index) ?: false -> Color.Red.copy(
                        alpha = 0.5f
                    )

                    else -> Color.Gray
                }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color)
                    .clickable { onSelect(quest, index) }
            ) {
                Text(text = it)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Gray)
                    .clickable { onAnswer(quest, quest.selectedAnswers) }
            ) {
                Text(text = "Answer")
            }
        }
    }
}

@Composable
fun QuestSelect(quests: List<QuestionUi>, selected: Int, onClick: (Int) -> Unit) {
    val rowState = rememberLazyListState()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        contentPadding = PaddingValues(horizontal = 5.dp),
        state = rowState
    ) {
        items(quests.size) {
            val color = when (quests[it].answerType) {
                AnswerType.Correct -> Color.Green
                AnswerType.Fail -> Color.Red
                AnswerType.Nothing -> Color.Gray
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .background(color.copy(alpha = if (selected == it) 0.5f else 1f))
                    .clickable { onClick(it) },
                contentAlignment = Alignment.Center
            ) {
                Text(text = "$it")
            }
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
    LaunchedEffect(key1 = selected) {
        when {
            selected == (quests.size - 1) ||
                    selected < rowState.firstVisibleItemIndex -> rowState.scrollToItem(selected)

            (rowState.firstVisibleItemIndex - selected).absoluteValue > 5 ->
                rowState.scrollToItem(rowState.firstVisibleItemIndex + 1)
        }
    }
}