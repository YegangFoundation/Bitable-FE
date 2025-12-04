package com.example.bitable_fe.core.data.datastore


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.userPrefsDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        val USER_ID = longPreferencesKey("user_id")
    }

    val userId: Flow<Long> = context.userPrefsDataStore.data.map { prefs ->
        prefs[USER_ID] ?: -1L
    }

    suspend fun saveUserId(id: Long) {
        context.userPrefsDataStore.edit { prefs ->
            prefs[USER_ID] = id
        }
    }
}
