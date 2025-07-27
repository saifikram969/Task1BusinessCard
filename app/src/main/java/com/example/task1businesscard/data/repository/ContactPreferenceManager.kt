package com.example.task1businesscard.data.repository
import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "contacts")

class ContactPreferenceManager(private val context: Context) {
    private val FAVORITES_KEY = stringSetPreferencesKey("favorites")

    val favoriteContacts: Flow<Set<String>> = context.dataStore.data
        .map { preferences ->
            preferences[FAVORITES_KEY] ?: emptySet()
        }

    suspend fun saveFavorite(name: String) {
        context.dataStore.edit { preferences ->
            val updated = preferences[FAVORITES_KEY]?.toMutableSet() ?: mutableSetOf()
            updated.add(name)
            preferences[FAVORITES_KEY] = updated
        }
    }

    suspend fun removeFavorite(name: String) {
        context.dataStore.edit { preferences ->
            val updated = preferences[FAVORITES_KEY]?.toMutableSet() ?: mutableSetOf()
            updated.remove(name)
            preferences[FAVORITES_KEY] = updated
        }
    }
}

