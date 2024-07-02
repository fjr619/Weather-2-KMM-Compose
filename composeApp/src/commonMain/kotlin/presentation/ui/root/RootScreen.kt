package presentation.ui.root

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
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
    val rootState by rootViewModel.rootState.collectAsStateWithLifecycle()



    BindEffect(rootViewModel.permissionsController)

    LaunchedEffect(Unit) {
        rootViewModel.onEvent(RootEvent.onRequestPermission)
//        rootViewModel.requestPermission()
    }

    LifecycleResumeEffect(Unit) {
        rootViewModel.onEvent(RootEvent.onCheckPermission)
//        rootViewModel.checkPermissionStatus()
        onPauseOrDispose { }
    }

    RootContent(
        modifier = modifier,
        navController,
        rootState,
        rootViewModel::onEvent
    )


}

@Composable
fun RootContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    rootState: RootState,
    onRootEvent: (RootEvent) -> Unit
) {
    val items = listOf(
        MaterialNavRoute.Home,
        MaterialNavRoute.Daily,
    )

    if (!rootState.isPermissionGranted && rootState.permissionState == PermissionState.DeniedAlways) {
        Column {
            Button(
                content = {
                    Text("Request Permission")
                },
                onClick = {
                    onRootEvent(RootEvent.onRequestPermission)
                }
            )
        }
    } else if (rootState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Text("Loading")
        }
    } else if (rootState.isError) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Error : ${rootState.error}")
            ElevatedButton(
                onClick = {
                    onRootEvent(RootEvent.onRequestPermission)
                },
                content = {
                    Text("Retry")
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