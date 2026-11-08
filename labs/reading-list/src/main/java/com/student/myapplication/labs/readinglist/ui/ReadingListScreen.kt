package com.student.myapplication.labs.readinglist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.student.myapplication.labs.readinglist.data.Book
import com.student.myapplication.labs.readinglist.data.BookDatabase
import com.student.myapplication.labs.readinglist.data.SortPreferences
import kotlinx.coroutines.launch

@Composable
fun ReadingListScreen() {
    val context = LocalContext.current
    val db = remember { BookDatabase.getDatabase(context) }
    val bookDao = remember { db.bookDao() }
    val scope = rememberCoroutineScope()

    val sortOrder by remember(context) {
        SortPreferences.getSortOrder(context)
    }.collectAsState(initial = SortPreferences.SORT_NEWEST)

    val booksList by remember(sortOrder) {
        if (sortOrder == SortPreferences.SORT_TITLE) {
            bookDao.getAllBooksTitleAsc()
        } else {
            bookDao.getAllBooksNewestFirst()
        }
    }.collectAsState(initial = emptyList())

    var titleInput by remember { mutableStateOf("") }
    var authorInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Reading List App",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = titleInput,
            onValueChange = { titleInput = it },
            label = { Text("Book Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = authorInput,
            onValueChange = { authorInput = it },
            label = { Text("Author Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (titleInput.isNotBlank() && authorInput.isNotBlank()) {
                    val book = Book(title = titleInput, author = authorInput)
                    scope.launch {
                        bookDao.insertBook(book)
                        titleInput = ""
                        authorInput = ""
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Book")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        SortPreferences.saveSortOrder(context, SortPreferences.SORT_NEWEST)
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Sort: Newest")
            }
            OutlinedButton(
                onClick = {
                    scope.launch {
                        SortPreferences.saveSortOrder(context, SortPreferences.SORT_TITLE)
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Sort: Title A-Z")
            }
        }

        HorizontalDivider()

        Text(text = "Books Count: ${booksList.size}")

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(booksList, key = { it.id }) { book ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Author: ${book.author}", style = MaterialTheme.typography.bodyMedium)
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                bookDao.deleteBook(book)
                            }
                        }
                    ) {
                        Text("Delete")
                    }
                }
                HorizontalDivider()
            }
        }
    }
}