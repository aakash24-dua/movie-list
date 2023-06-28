package com.nationalconclave.bseb

import android.app.Application
import android.content.ContextWrapper
import com.nationalconclave.bseb.utils.AppComponent
import com.nationalconclave.bseb.utils.AppModule
import com.nationalconclave.bseb.utils.DaggerAppComponent
import com.pixplicity.easyprefs.library.Prefs

class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        Prefs.Builder()
            .setContext(this)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(packageName)
            .setUseDefaultSharedPreference(true)
            .build()

    }
}