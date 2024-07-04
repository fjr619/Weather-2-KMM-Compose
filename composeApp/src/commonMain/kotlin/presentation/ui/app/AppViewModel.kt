package presentation.ui.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.repository.ThemeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    themeRepository: ThemeRepository
) : ViewModel() {

    val isDarkTheme = themeRepository.isDarkTheme()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

}