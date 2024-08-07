package domain.repository

import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
   fun isDarkTheme(): Flow<Boolean>
   suspend fun setTheme()
}