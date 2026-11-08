package com.student.myapplication.labs.commentsapi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.student.myapplication.labs.commentsapi.data.Comment
import com.student.myapplication.labs.commentsapi.network.RetrofitClient
import kotlinx.coroutines.launch

sealed interface CommentsUiState {
    object Loading : CommentsUiState
    data class Success(val comments: List<Comment>) : CommentsUiState
    data class Error(val message: String) : CommentsUiState
    object Empty : CommentsUiState
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsApiApp() {
    var uiState by remember { mutableStateOf<CommentsUiState>(CommentsUiState.Loading) }
    val scope = rememberCoroutineScope()

    fun fetchComments() {
        uiState = CommentsUiState.Loading
        scope.launch {
            try {
                val response = RetrofitClient.apiService.getComments()
                uiState = if (response.isEmpty()) CommentsUiState.Empty else CommentsUiState.Success(response)
            } catch (e: Exception) {
                uiState = CommentsUiState.Error(e.localizedMessage ?: "Failed to fetch comments")
            }
        }
    }

    LaunchedEffect(Unit) {
        fetchComments()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comments API (Retrofit)") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val state = uiState) {
                is CommentsUiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading comments from REST API...")
                    }
                }

                is CommentsUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Error Loading API",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = state.message, style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { fetchComments() }) {
                            Text("Retry API Request")
                        }
                    }
                }

                is CommentsUiState.Empty -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No comments found (Empty state)")
                    }
                }

                is CommentsUiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        item {
                            Text(
                                text = "Fetched ${state.comments.size} Comments",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        items(state.comments, key = { it.id }) { comment ->
                            CommentRowItem(comment = comment)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommentRowItem(comment: Comment) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = comment.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Email: ${comment.email}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = comment.body, style = MaterialTheme.typography.bodyMedium)
        }
    }
}