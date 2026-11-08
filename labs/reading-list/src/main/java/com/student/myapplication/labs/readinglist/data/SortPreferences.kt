package com.student.myapplication.labs.readinglist.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.readingListStore by preferencesDataStore(name = "reading_list_preferences")

object SortPreferences {
    val SORT_ORDER_KEY = stringPreferencesKey("sort_order")
    const val SORT_NEWEST = "NEWEST_FIRST"
    const val SORT_TITLE = "TITLE_ASC"

    fun getSortOrder(context: Context): Flow<String> {
        return context.readingListStore.data.map { prefs ->
            prefs[SORT_ORDER_KEY] ?: SORT_NEWEST
        }
    }

    suspend fun saveSortOrder(context: Context, sortOrder: String) {
        context.readingListStore.edit { prefs ->
            prefs[SORT_ORDER_KEY] = sortOrder
        }
    }
}