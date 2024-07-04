package presentation.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.theme.AppTheme
import presentation.ui.root.RootScreen

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App(
    dynamicColor: Boolean = false,
    updateSystemBarTheme: @Composable (Boolean) -> Unit = {},
) {

    val appViewModel = koinViewModel<AppViewModel>()
    val isDarkTheme by appViewModel.isDarkTheme.collectAsStateWithLifecycle()

    updateSystemBarTheme(isDarkTheme)

    AppTheme(
        darkTheme = isDarkTheme,
        dynamicColor = dynamicColor
    ) {
        RootScreen()
    }
}