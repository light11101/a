package com.student.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.student.myapplication.labs.announcements.AnnouncementsApp
import com.student.myapplication.labs.backgroundsync.ui.BackgroundSyncApp
import com.student.myapplication.labs.commentsapi.ui.CommentsApiApp
import com.student.myapplication.labs.contactdirectory.ContactDirectoryApp
import com.student.myapplication.labs.foodordering.FoodOrderingApp
import com.student.myapplication.labs.fragments.FragmentsApp
import com.student.myapplication.labs.layouts.LayoutsApp
import com.student.myapplication.labs.productstore.ProductStoreApp
import com.student.myapplication.labs.readinglist.ui.ReadingListApp
import com.student.myapplication.ui.theme.MyApplicationTheme

enum class LabProject(val title: String) {
    READING_LIST("Reading List (Room + DataStore)"),
    FOOD_ORDERING("Food Ordering (Intents & Animations)"),
    COMMENTS_API("Comments App (Retrofit & REST API)"),
    PRODUCT_STORE("Product Store (Navigation Compose)"),
    ANNOUNCEMENTS("Announcements (Dynamic UI & LazyColumn)"),
    CONTACT_DIRECTORY("Contact Directory (Lists & Snackbar)"),
    LAYOUTS("Compose Layouts (Text, Button, Box)"),
    BACKGROUND_SYNC("Background Sync (WorkManager)"),
    FRAGMENTS("Fragments & Dynamic UI")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {

                // ReadingListApp()
                // FoodOrderingApp()
                // CommentsApiApp()
//                 ProductStoreApp()
                // AnnouncementsApp()
                // ContactDirectoryApp()
                // LayoutsApp()
                // BackgroundSyncApp()
//                 FragmentsApp()

            }
        }
    }
}
