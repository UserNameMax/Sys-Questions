package ru.mishenko.maksin.sysquestions.ui.questions.viewModel

import ru.mishenko.maksin.sysquestions.useCase.QuestionUseCase

class AllQuestionViewModel(questionUseCase: QuestionUseCase) : QuestionViewModel() {
    init {
        initState(questionUseCase.getAllEvents())
    }
}