package com.appilab.loginsignupmvvm.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.appilab.loginsignupmvvm.data.local.AutoAuthPrefsManager.PrefrencesKeys.PREVIOUS_AUTH_USER
import com.appilab.loginsignupmvvm.util.Constants.Companion.AUTO_AUTH_PREFS
import com.appilab.loginsignupmvvm.util.Constants.Companion.PREVIOUS_AUTO_AUTH_PREFS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class AutoAuthPrefsManager @Inject constructor(context: Context) {

    private val dataStore = context.createDataStore(name = AUTO_AUTH_PREFS )

    suspend fun readPreviousAuthUserEmail():String?{
        val preferences = dataStore.data.first()
        return preferences[PREVIOUS_AUTH_USER]
    }

    val preferencesFlow:Flow<String> = dataStore.data
        .catch { exception->
            if (exception is IOException)
            {
                emit(emptyPreferences())
            }
            else{
                throw exception
            }
        }.map { pref->
            pref[PREVIOUS_AUTH_USER]?:""
        }

    suspend fun saveAuthenticatedUserToDataStore(email:String)
    {
        dataStore.edit {
            it[PREVIOUS_AUTH_USER] = email
        }
    }

    private object PrefrencesKeys{
        val PREVIOUS_AUTH_USER = preferencesKey<String>(PREVIOUS_AUTO_AUTH_PREFS)
    }
}