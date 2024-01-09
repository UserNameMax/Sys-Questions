package ru.mishenko.maksin.sysquestions.ui.questions.model

data class State(
    val questions: List<QuestionUi>,
    val select: Int,
) {
    companion object {
        val default = State(listOf(), 0)
    }
}
