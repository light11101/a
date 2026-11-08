package com.student.myapplication.labs.readinglist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY title ASC")
    fun getAllBooksTitleAsc(): Flow<List<Book>>

    @Query("SELECT * FROM books ORDER BY timestamp DESC")
    fun getAllBooksNewestFirst(): Flow<List<Book>>

    @Insert
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}