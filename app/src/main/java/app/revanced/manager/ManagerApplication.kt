package app.revanced.manager

import android.app.Application
import app.revanced.manager.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ManagerApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ManagerApplication)
            modules(
                httpModule,
                preferencesModule,
                viewModelModule,
                repositoryModule,
                patcherModule,
                serviceModule
            )
        }
    }
}