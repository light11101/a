package com.student.myapplication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.student.myapplication.labs.announcements.AnnouncementsApp
import com.student.myapplication.labs.backgroundsync.ui.BackgroundSyncApp
import com.student.myapplication.labs.commentsapi.ui.CommentsApiApp
import com.student.myapplication.labs.contactdirectory.ContactDirectoryApp
import com.student.myapplication.labs.eventregistration.EventRegistrationApp
import com.student.myapplication.labs.feedbackcollector.FeedbackCollectorApp
import com.student.myapplication.labs.firebaseauth.FirebaseAuthApp
import com.student.myapplication.labs.foodordering.FoodOrderingApp
import com.student.myapplication.labs.productstore.ProductStoreApp
import com.student.myapplication.labs.readinglist.ui.ReadingListApp
import com.student.myapplication.labs.shoppingcart.ShoppingCartApp
import com.student.myapplication.labs.tipcalculator.TipCalculatorApp
import com.student.myapplication.ui.theme.MyApplicationTheme

enum class LabProject(val title: String) {
    Q1_FOOD_ORDERING("Q1: Food Ordering (Explicit Intent & Extras)"),
    Q2_ANNOUNCEMENTS("Q2: Announcement Feed (Dynamic UI)"),
    Q3_PRODUCT_STORE("Q3/Q6: Product Store (Navigation Compose)"),
    Q4_CONTACT_DIRECTORY("Q4: Contact Directory (LazyColumn & Snackbar)"),
    Q5_BACKGROUND_SYNC("Q5: Background Sync (WorkManager Tasks)"),
    Q7_READING_LIST("Q7: Reading List (Room DB & DataStore Sort)"),
    Q8_COMMENTS_API("Q8: Comments List (REST API & Retrofit)"),
    Q9_FEEDBACK_COLLECTOR("Q9: Feedback Collector (Real-Time List)"),
    Q10_TIP_CALCULATOR("Q10: Tip Calculator (Logic & Unit Tests)"),
    Q11_SHOPPING_CART("Q11: Shopping Cart (Quantities & Discounts)"),
    Q12_FIREBASE_AUTH("Q12: User Authentication (Login & Home)"),
    Q13_EVENT_REGISTRATION("Q13: Event Registration (Forms & List)")
}

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                // For direct exam submission, uncomment your target lab composable below:
                // FoodOrderingApp()
                // AnnouncementsApp()
                // ProductStoreApp()
                // ContactDirectoryApp()
                // BackgroundSyncApp()
                // ReadingListApp()
                // CommentsApiApp()
                // FeedbackCollectorApp()
                // TipCalculatorApp()
                // ShoppingCartApp()
                // FirebaseAuthApp()
                // EventRegistrationApp()

                LabProjectSwitcher()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabProjectSwitcher() {
    var selectedProject by remember { mutableStateOf(LabProject.Q1_FOOD_ORDERING) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(top = 24.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedProject.title,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Practice Lab Question") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    LabProject.entries.forEach { project ->
                        DropdownMenuItem(
                            text = { Text(project.title) },
                            onClick = {
                                selectedProject = project
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when (selectedProject) {
                LabProject.Q1_FOOD_ORDERING -> FoodOrderingApp()
                LabProject.Q2_ANNOUNCEMENTS -> AnnouncementsApp()
                LabProject.Q3_PRODUCT_STORE -> ProductStoreApp()
                LabProject.Q4_CONTACT_DIRECTORY -> ContactDirectoryApp()
                LabProject.Q5_BACKGROUND_SYNC -> BackgroundSyncApp()
                LabProject.Q7_READING_LIST -> ReadingListApp()
                LabProject.Q8_COMMENTS_API -> CommentsApiApp()
                LabProject.Q9_FEEDBACK_COLLECTOR -> FeedbackCollectorApp()
                LabProject.Q10_TIP_CALCULATOR -> TipCalculatorApp()
                LabProject.Q11_SHOPPING_CART -> ShoppingCartApp()
                LabProject.Q12_FIREBASE_AUTH -> FirebaseAuthApp()
                LabProject.Q13_EVENT_REGISTRATION -> EventRegistrationApp()
            }
        }
    }
}
