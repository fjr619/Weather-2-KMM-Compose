package di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import presentation.ui.app.AppViewModel
import presentation.ui.root.RootViewModel


val viewModelModule = module {
    viewModel { AppViewModel(get()) }
    viewModel {
        RootViewModel(
            permissionsController = get(),
            locationRepository = get(),
            forecastRepository = get(),
            themeRepository = get()
        )
    }
}