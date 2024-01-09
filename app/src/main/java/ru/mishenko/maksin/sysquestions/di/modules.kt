package ru.mishenko.maksin.sysquestions.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.mishenko.maksin.sysquestions.repository.QuestionRepository
import ru.mishenko.maksin.sysquestions.repository.ResourceQuestionRepository
import ru.mishenko.maksin.sysquestions.ui.questions.viewModel.AllQuestionViewModel
import ru.mishenko.maksin.sysquestions.ui.questions.viewModel.QuestionViewModel
import ru.mishenko.maksin.sysquestions.ui.questions.viewModel.RandomQuestionViewModel
import ru.mishenko.maksin.sysquestions.useCase.QuestionUseCase

val module = module {
    single<QuestionRepository> { ResourceQuestionRepository(context = androidContext()) }
    single<QuestionUseCase> { QuestionUseCase(questionRepository = get()) }
    viewModel<AllQuestionViewModel> { AllQuestionViewModel(questionUseCase = get<QuestionUseCase>()) }
    viewModel<RandomQuestionViewModel> { RandomQuestionViewModel(questionUseCase = get<QuestionUseCase>()) }
}