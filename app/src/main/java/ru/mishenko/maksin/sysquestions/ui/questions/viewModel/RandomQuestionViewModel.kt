package ru.mishenko.maksin.sysquestions.ui.questions.viewModel

import ru.mishenko.maksin.sysquestions.useCase.QuestionUseCase

class RandomQuestionViewModel(questionUseCase: QuestionUseCase) : QuestionViewModel() {
    init {
        initState(questionUseCase.getRandomEvents(30))
    }
}