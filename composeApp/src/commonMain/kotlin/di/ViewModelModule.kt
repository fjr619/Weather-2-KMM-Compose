package di

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module
import presentation.ui.root.RootViewModel


val viewModelModule = module {
    viewModel {
        RootViewModel(
            permissionsController = get(),
            locationRepository = get(),
            forecastRepository = get()
        )
    }
}