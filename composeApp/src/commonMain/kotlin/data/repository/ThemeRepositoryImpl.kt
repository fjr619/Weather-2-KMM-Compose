package data.repository

import data.local.datastore.PreferencesDataSource
import domain.Util
import domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ThemeRepositoryImpl(
    private val preferencesDataSource: PreferencesDataSource
): ThemeRepository {
    override fun isDarkTheme(): Flow<Boolean> = preferencesDataSource.getPreference(
        key = Util.KEY_DARKTHEME,
        defaultValue = false
    )

    override suspend fun setTheme() {
        preferencesDataSource.putPreference(
            key = Util.KEY_DARKTHEME,
            value = !isDarkTheme().first()
        )
    }
}