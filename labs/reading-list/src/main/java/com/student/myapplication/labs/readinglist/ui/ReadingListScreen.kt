package com.student.myapplication.labs.readinglist.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@Composable
fun ReadingListScreen() {
    val context = LocalContext.current
    val db = remember { BookDatabase.getDatabase(context) }
    val bookDao = remember { db.bookDao() }
    val scope = rememberCoroutineScope()

    val currentSortOrder by remember(context) {
        SortPreferences.getSortOrder(context)
    }.collectAsState(initial = SortPreferences.SORT_NEWEST)

    val booksList by remember(currentSortOrder) {
        if (currentSortOrder == SortPreferences.SORT_TITLE) {
            bookDao.getAllBooksTitleAsc()
        } else {
            bookDao.getAllBooksNewestFirst()
        }
    }.collectAsState(initial = emptyList())

    var bookTitle by remember { mutableStateOf("") }
    var bookAuthor by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reading List (Room + DataStore)") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Form to Add Book
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Add Book to Library",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = bookTitle,
                        onValueChange = { bookTitle = it },
                        label = { Text("Book Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = bookAuthor,
                        onValueChange = { bookAuthor = it },
                        label = { Text("Author Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            if (bookTitle.isNotBlank() && bookAuthor.isNotBlank()) {
                                val newBook = Book(title = bookTitle, author = bookAuthor)
                                scope.launch {
                                    bookDao.insertBook(newBook)
                                    bookTitle = ""
                                    bookAuthor = ""
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Save to Room DB")
                    }
                }
            }

            // Sorting Selection (Persisted in DataStore)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Books (${booksList.size})",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = currentSortOrder == SortPreferences.SORT_NEWEST,
                        onClick = {
                            scope.launch {
                                SortPreferences.saveSortOrder(context, SortPreferences.SORT_NEWEST)
                            }
                        },
                        label = { Text("Newest") }
                    )
                    FilterChip(
                        selected = currentSortOrder == SortPreferences.SORT_TITLE,
                        onClick = {
                            scope.launch {
                                SortPreferences.saveSortOrder(context, SortPreferences.SORT_TITLE)
                            }
                        },
                        label = { Text("Title A-Z") }
                    )
                }
            }

            // Book List
            if (booksList.isEmpty()) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "No books in list. Add a book using the form above.",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(booksList, key = { it.id }) { book ->
                        BookRowItem(
                            book = book,
                            onDelete = {
                                scope.launch {
                                    bookDao.deleteBook(book)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookRowItem(book: Book, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = book.title, style = MaterialTheme.typography.titleMedium)
                Text(text = "By ${book.author}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = onDelete) {
                Text("🗑️", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}