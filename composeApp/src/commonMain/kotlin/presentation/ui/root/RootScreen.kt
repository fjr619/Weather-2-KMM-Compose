package presentation.ui.root

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.route.MaterialNavRoute
import presentation.ui.daily.DailyScreen
import presentation.ui.home.HomeScreen
import weathercmp.composeapp.generated.resources.Res
import weathercmp.composeapp.generated.resources.moon_icon
import weathercmp.composeapp.generated.resources.sun_icon

@OptIn(KoinExperimentalAPI::class)
@Composable
fun RootScreen(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    val rootViewModel = koinViewModel<RootViewModel>()
    val rootState by rootViewModel.rootState.collectAsStateWithLifecycle()

    // Toggle variable to switch between dark and light themes
    val isDarkTheme by rootViewModel.isDarkTheme.collectAsStateWithLifecycle()

    BindEffect(rootViewModel.permissionsController)

    LaunchedEffect(Unit) {
        rootViewModel.onEvent(RootEvent.onRequestPermission)
    }

    LifecycleResumeEffect(Unit) {
        rootViewModel.onEvent(RootEvent.onCheckPermission)
        onPauseOrDispose { }
    }

    RootContent(
        modifier = modifier,
        navController = navController,
        isDarkTheme = isDarkTheme,
        rootState = rootState,
        onRootEvent = rootViewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isDarkTheme: Boolean,
    rootState: RootState,
    onRootEvent: (RootEvent) -> Unit,
) {
    // Determine which icon to display based on the theme
    val iconRes = if (isDarkTheme) Res.drawable.sun_icon else Res.drawable.moon_icon

    // Animate the rotation of the icon when theme is switched
    val rotationAngle by animateFloatAsState(targetValue = if (isDarkTheme) 180f else 0f)

    val items = listOf(
        MaterialNavRoute.Home,
        MaterialNavRoute.Daily,
    )

    if (!rootState.isPermissionGranted && rootState.permissionState == PermissionState.DeniedAlways) {
        Surface {
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
        }

    } else if (rootState.isLoading) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text("Loading")
            }
        }

    } else if (rootState.isError) {
        Surface {
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
        }

    } else {
        Scaffold(
            modifier = modifier,
            topBar = {
                TopAppBar(
                    title = {},
                    actions = {
                        IconButton(content = {
                            Icon(
                                painterResource(resource = iconRes), 
                                contentDescription = "",
                                modifier = Modifier.graphicsLayer(rotationZ = rotationAngle )
                            )
                        }, onClick = {
                            onRootEvent(RootEvent.onUpdateTheme)
                        })
                    }
                )
            },
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
                            onClick = dropUnlessResumed {
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
                    HomeScreen(weather = rootState.weatherData)
                }
                composable(MaterialNavRoute.Daily.route) {
                    DailyScreen(weather = rootState.weatherData)
                }
            }
        }
    }
}