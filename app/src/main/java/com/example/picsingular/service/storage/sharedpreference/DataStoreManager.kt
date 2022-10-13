package com.example.picsingular.service.storage.sharedpreference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.picsingular.bean.User
import com.example.picsingular.common.constants.PreferencesConstants
import com.example.picsingular.common.constants.TokenConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class DataStoreManager(appContext: Context, private var dataStore: DataStore<Preferences>) {

    // save token
    suspend fun saveToken(token: String){
        dataStore.edit{
            it[TOKEN_KEY] = token
        }
    }

    // get token
    fun getToken(): Flow<String> = dataStore.data.map {
        it[TOKEN_KEY] ?: ""
    }.flowOn(Dispatchers.IO)

    // get user info
    fun getUser(): Flow<User> = dataStore.data.map {
        User(
            it[USER_ID_KEY] ?: 0L,
            it[USERNAME_KEY] ?: "",
            it[USER_SIGNATURE_KEY] ?: "",
            it[USER_AVATAR_KEY] ?: ""
        )
    }.flowOn(Dispatchers.IO)

    // save user info
    suspend fun saveUser(user: User?){
        dataStore.edit{
            it[USER_ID_KEY] = user?.userId ?: 0L
            it[USERNAME_KEY] = user?.username ?: ""
            it[USER_SIGNATURE_KEY] = user?.signature ?: ""
            it[USER_AVATAR_KEY] = user?.avatar ?: ""
        }
    }

    // save PicBedConfig
    suspend fun savePicBedConfig(picBedBaseUrl: String, picBedToken: String){
        dataStore.edit {
            it[PIC_BED_URL_KEY] = picBedBaseUrl
            it[PIC_BED_TOKEN] = picBedToken
        }
    }

    // get PicBedBaseUrl
    fun getPicBedBaseUrl(): Flow<String> = dataStore.data.map { it[PIC_BED_URL_KEY] ?: TokenConstants.PIC_BED_TOKEN }.flowOn(Dispatchers.IO)

    // get PicBedToken
    fun getPicBedToken(): Flow<String> = dataStore.data.map { it[PIC_BED_TOKEN] ?: TokenConstants.PIC_BED_TOKEN}.flowOn(Dispatchers.IO)
    companion object {
        val TOKEN_KEY = stringPreferencesKey(PreferencesConstants.TOKEN)
        val USER_ID_KEY = longPreferencesKey(PreferencesConstants.USER_ID)
        val USERNAME_KEY = stringPreferencesKey(PreferencesConstants.USER_NAME)
        val USER_SIGNATURE_KEY = stringPreferencesKey(PreferencesConstants.USER_SIGNATURE)
        val USER_AVATAR_KEY = stringPreferencesKey(PreferencesConstants.USER_AVATAR)
        val PIC_BED_URL_KEY = stringPreferencesKey(PreferencesConstants.PIC_BED_BASE_URL)
        val PIC_BED_TOKEN = stringPreferencesKey(PreferencesConstants.PIC_BED_TOKEN)
    }

}