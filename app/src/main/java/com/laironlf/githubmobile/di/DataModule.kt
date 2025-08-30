package com.laironlf.githubmobile.di

import android.content.Context
import android.content.SharedPreferences
import com.laironlf.githubmobile.data.storage.KeyValueStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideKeyValueStorage(sharedPreferences: SharedPreferences): KeyValueStorage {
        return KeyValueStorage(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideKeySharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            KeyValueStorage.SHARED_PREFS_TOKEN_VALUE_KEY,
            Context.MODE_PRIVATE
        )
    }
}