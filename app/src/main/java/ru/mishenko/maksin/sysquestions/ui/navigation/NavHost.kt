package ru.mishenko.maksin.sysquestions.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.mishenko.maksin.sysquestions.ui.MainMenuView
import ru.mishenko.maksin.sysquestions.ui.questions.viewModel.AllQuestionViewModel
import ru.mishenko.maksin.sysquestions.ui.questions.QuestionView
import ru.mishenko.maksin.sysquestions.ui.questions.viewModel.RandomQuestionViewModel

@Composable
fun NavigationHost(navController: NavHostController = rememberNavController()) {
    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = NavDestination.MainMenu.name
    ) {
        composable(NavDestination.MainMenu.name) {
            MainMenuView {
                navController.navigate(it.name)
            }
        }
        composable(NavDestination.AllQuestion.name) {
            QuestionView(viewModel = koinViewModel<AllQuestionViewModel>())
        }
        composable(NavDestination.RandomQuestion.name) {
            QuestionView(viewModel = koinViewModel<RandomQuestionViewModel>())
        }
    }
}