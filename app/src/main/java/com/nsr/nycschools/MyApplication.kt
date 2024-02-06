package com.nsr.nycschools
import android.app.Application
import com.nsr.nycschools.koin.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@MyApplication)
            modules(myModule)
        }
    }
}