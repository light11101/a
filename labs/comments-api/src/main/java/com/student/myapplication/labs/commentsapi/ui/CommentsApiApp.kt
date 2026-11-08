package com.student.myapplication.labs.commentsapi.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

sealed interface CommentsState {
    object Loading : CommentsState
    data class Success(val list: List<Comment>) : CommentsState
    data class Error(val msg: String) : CommentsState
}

@Composable
fun CommentsApiApp() {
    var state by remember { mutableStateOf<CommentsState>(CommentsState.Loading) }
    val scope = rememberCoroutineScope()

    fun loadData() {
        state = CommentsState.Loading
        scope.launch {
            try {
                val data = RetrofitClient.apiService.getComments()
                state = CommentsState.Success(data)
            } catch (e: Exception) {
                state = CommentsState.Error(e.localizedMessage ?: "Error loading comments")
            }
        }
    }

    LaunchedEffect(Unit) {
        loadData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Comments REST API App", style = MaterialTheme.typography.headlineSmall)

        when (val curr = state) {
            is CommentsState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Loading API Comments...")
                }
            }
            is CommentsState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = curr.msg)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { loadData() }) {
                        Text("Retry")
                    }
                }
            }
            is CommentsState.Success -> {
                Text(text = "Loaded ${curr.list.size} comments")
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(curr.list, key = { it.id }) { comment ->
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(text = comment.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Email: ${comment.email}", style = MaterialTheme.typography.bodySmall)
                            Text(text = comment.body, style = MaterialTheme.typography.bodyMedium)
                            HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                }
            }
        }
    }
}