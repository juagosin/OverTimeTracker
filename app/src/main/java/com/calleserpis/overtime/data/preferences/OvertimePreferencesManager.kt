package com.calleserpis.overtime.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "overtime_preferences")

@Singleton
class OvertimePreferencesManager @Inject constructor(@ApplicationContext private val context: Context) {
    companion object{
        private val LAST_COMPANY = stringPreferencesKey("last_company")
        private val LAST_START_DATE = stringPreferencesKey("last_start_data")
        private val LAST_END_DATE = stringPreferencesKey("last_end_date")
    }

    val lastCompany: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[LAST_COMPANY] ?: ""
    }
    val lastStartDate: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[LAST_START_DATE] ?: ""
    }
    val lastEndDate: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[LAST_END_DATE] ?: ""
    }

    suspend fun saveLastCompany(company: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_COMPANY] = company
        }

    }


}