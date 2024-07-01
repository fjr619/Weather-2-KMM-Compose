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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.resources.stringResource
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.route.MaterialNavRoute

@OptIn(KoinExperimentalAPI::class)
@Composable
fun RootScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val permissionFactory = rememberPermissionsControllerFactory()
    val permissionsController =
        remember(permissionFactory) { permissionFactory.createPermissionsController() }
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()
    val rootViewModel = viewModel {
        RootViewModel(
            permissionsController = permissionsController,
        )
    }
    val permissionState by rootViewModel.permissionState.collectAsStateWithLifecycle()
    val permissionGranted by rootViewModel.permissionGranted.collectAsStateWithLifecycle()


    val items = listOf(
        MaterialNavRoute.Home,
        MaterialNavRoute.Daily,
    )

    LaunchedEffect(Unit) {
        rootViewModel.requestPermission()
    }

    LifecycleResumeEffect(Unit) {
        rootViewModel.checkPermissionStatus()
        onPauseOrDispose {  }
    }

    BindEffect(permissionsController)

    println("permissionState $permissionState")

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