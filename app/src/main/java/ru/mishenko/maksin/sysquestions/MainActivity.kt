package ru.mishenko.maksin.sysquestions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import ru.mishenko.maksin.sysquestions.ui.navigation.NavigationHost
import ru.mishenko.maksin.sysquestions.ui.theme.SysQuestionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SysQuestionsTheme {
                Surface {
                    NavigationHost()
                }
            }
        }
    }
}