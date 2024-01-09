package ru.mishenko.maksin.sysquestions.ui.questions.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.mishenko.maksin.sysquestions.model.Question
import ru.mishenko.maksin.sysquestions.ui.questions.model.AnswerType
import ru.mishenko.maksin.sysquestions.ui.questions.model.QuestionUi
import ru.mishenko.maksin.sysquestions.ui.questions.model.State

abstract class QuestionViewModel : ViewModel() {
    private var mutableState = MutableStateFlow(State.default)
    val state = mutableState.asStateFlow()

    protected fun initState(questions: List<Question>) {
        mutableState.update {
            it.copy(questions = questions.map { it.toUi() })
        }
    }

    fun selectQuestion(n: Int) {
        if (n in 0..<state.value.questions.size) {
            mutableState.update { it.copy(select = n) }
        }
    }

    fun answer(questionUi: QuestionUi, answers: List<Int>) {
        if (answers.isEmpty()) return
        if (questionUi.answerType != AnswerType.Nothing) return
        val type =
            if (questionUi.question.trueAnswersIndex.toSet() == answers.toSet()) AnswerType.Correct else AnswerType.Fail
        val newQuestionUi = when (questionUi) {
            is QuestionUi.QuestionWithMoreAnswer -> {
                questionUi.copy(answerType = type, answers = answers, selectedAnswers = listOf())
            }

            is QuestionUi.QuestionWithOneAnswer -> {
                questionUi.copy(answerType = type, answer = answers.first())
            }
        }
        val list = state.value.questions.toMutableList()
        val index = list.indexOf(questionUi)
        list[index] = newQuestionUi
        mutableState.update { it.copy(questions = list) }
    }

    fun select(questionUi: QuestionUi, selectedIndex: Int) {
        if (questionUi is QuestionUi.QuestionWithMoreAnswer) {
            val list = state.value.questions.toMutableList()
            val index = list.indexOf(questionUi)
            val selectAnswers = if (questionUi.selectedAnswers.contains(selectedIndex)) {
                questionUi.selectedAnswers - selectedIndex
            } else {
                questionUi.selectedAnswers + selectedIndex
            }
            list[index] = questionUi.copy(selectedAnswers = selectAnswers)
            mutableState.update { it.copy(questions = list) }
        }
    }

    private var dragable = true
    fun drag(delta: Float) {
        if (!dragable) return
        val select = state.value.select
        val size = state.value.questions.size
        val newSelect = if (delta < 0) (select + 1) % size else (select + size - 1) % size
        mutableState.update { it.copy(select = newSelect) }
        dragable = false
    }

    fun finishDrag() {
        dragable = true
    }

    private fun Question.toUi(): QuestionUi {
        return if (trueAnswersIndex.size == 1) {
            QuestionUi.QuestionWithOneAnswer(
                question = this,
                answerType = AnswerType.Nothing,
                answer = null
            )
        } else {
            QuestionUi.QuestionWithMoreAnswer(
                question = this,
                answerType = AnswerType.Nothing,
                selectedAnswers = listOf(),
                answers = null
            )
        }
    }
}