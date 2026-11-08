package com.student.myapplication.labs.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment

class DemoFragment : Fragment() {

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_COLOR = "color"

        fun newInstance(title: String, colorInt: Long): DemoFragment {
            return DemoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TITLE, title)
                    putLong(ARG_COLOR, colorInt)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val title = arguments?.getString(ARG_TITLE) ?: "Default Fragment"
        val colorVal = arguments?.getLong(ARG_COLOR) ?: 0xFF4CAF50

        return ComposeView(requireContext()).apply {
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(8.dp),
                    color = Color(colorVal)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}