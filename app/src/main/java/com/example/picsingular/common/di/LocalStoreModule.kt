package com.example.picsingular.common.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.picsingular.common.constants.PreferencesConstants
import com.example.picsingular.service.storage.sharedpreference.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { appContext.preferencesDataStoreFile(PreferencesConstants.PREFERENCES_NAME) }
    )

    @Provides
    @Singleton
    fun providePreferencesManager(@ApplicationContext context: Context,dataStore: DataStore<Preferences>) = DataStoreManager(appContext = context,dataStore = dataStore)

}