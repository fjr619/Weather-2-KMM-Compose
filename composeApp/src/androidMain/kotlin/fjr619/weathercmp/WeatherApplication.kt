package fjr619.weathercmp

import android.app.Application
import di.initKoin
import org.koin.android.ext.koin.androidContext

class WeatherApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@WeatherApplication)
        }
    }
}