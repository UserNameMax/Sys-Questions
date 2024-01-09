package ru.mishenko.maksin.sysquestions.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.mishenko.maksin.sysquestions.ui.navigation.NavDestination

@Composable
fun MainMenuView(nav: (NavDestination) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { nav(NavDestination.AllQuestion) }) {
            Text("All questions")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { nav(NavDestination.RandomQuestion) }) {
            Text("Random questions")
        }
    }
}