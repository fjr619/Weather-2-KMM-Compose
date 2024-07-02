package presentation.ui.root

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.route.MaterialNavRoute

@OptIn(KoinExperimentalAPI::class)
@Composable
fun RootScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val rootViewModel = koinViewModel<RootViewModel>()
    val permissionState by rootViewModel.permissionState.collectAsStateWithLifecycle()
    val permissionGranted by rootViewModel.permissionGranted.collectAsStateWithLifecycle()


    val items = listOf(
        MaterialNavRoute.Home,
        MaterialNavRoute.Daily,
    )

    BindEffect(rootViewModel.permissionsController)

    LaunchedEffect(Unit) {
        rootViewModel.requestPermission()
    }

    LifecycleResumeEffect(Unit) {
        rootViewModel.checkPermissionStatus()
        onPauseOrDispose { }
    }

    if (!permissionGranted && permissionState == PermissionState.DeniedAlways) {
        Column {
            Button(
                content = {
                    Text("Request Permission")
                },
                onClick = {
                    rootViewModel.requestPermission()
                }
            )
        }
    } else {
        Scaffold(
            modifier = modifier,
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(imageVector = screen.icon, contentDescription = screen.route)
                            },
                            label = {
                                Text(stringResource(screen.resourceTitle))
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    navController.graph.findStartDestination().route?.let {
                                        popUpTo(it) {
                                            saveState
                                        }

                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        )
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = MaterialNavRoute.Home.route,
                modifier = Modifier.padding(it)
            ) {
                composable(MaterialNavRoute.Home.route) {
                    Text("Home")
                }
                composable(MaterialNavRoute.Daily.route) {
                    Text("daily")
                }
            }
        }
    }


}