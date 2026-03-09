package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val received = intent?.getStringExtra(EXTRA_TEXT).orEmpty()

        setContent {
            val textToShow = remember(received) { received.ifBlank { "Пусто (ничего не передано)" } }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Вторая Activity",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Получено:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = textToShow,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }

    companion object {
        const val EXTRA_TEXT = "extra_text"
    }
}