package com.fetch.rewards.exercise


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.fetch.rewards.exercise.nav.ExerciseAppNavHost
import com.fetch.rewards.exercise.ui.theme.FetchRewardsExerciseTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashscreen = installSplashScreen()


        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(2000)
            keepSplashScreen = false
        }

        enableEdgeToEdge()
        setContent {
            FetchRewardsExerciseTheme {
                ExerciseAppNavHost(navController = rememberNavController())
            }
        }

    }

}






