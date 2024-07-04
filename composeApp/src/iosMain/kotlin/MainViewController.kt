import androidx.compose.ui.window.ComposeUIViewController
import presentation.ui.app.App

fun MainViewController() = ComposeUIViewController {

//    val isDarkTheme =
//        UIScreen.mainScreen.traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark

    App()
}