package com.wh2.reduxLogin.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistanceModule(val context: Context) {

    @Provides @Singleton fun sharedPreferences(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides @Singleton fun rxSharedPreferences(preferences: SharedPreferences): RxSharedPreferences = RxSharedPreferences.create(preferences)


}