package ru.mishenko.maksin.sysquestions.ui.questions.model

import ru.mishenko.maksin.sysquestions.model.Question

sealed class QuestionUi {
    abstract val question: Question
    abstract val answerType: AnswerType

    data class QuestionWithOneAnswer(
        override val question: Question,
        override val answerType: AnswerType,
        val answer: Int?
    ) : QuestionUi()

    data class QuestionWithMoreAnswer(
        override val question: Question,
        override val answerType: AnswerType,
        val selectedAnswers: List<Int>,
        val answers: List<Int>?
    ) : QuestionUi()
}
