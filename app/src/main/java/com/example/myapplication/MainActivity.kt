package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.jvm.java
import androidx.core.net.toUri

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current

            var input by remember { mutableStateOf("") }

            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            fun showError(message: String) {
                scope.launch { snackbarHostState.showSnackbar(message) }
            }

            Scaffold(
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
            ) { padding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {


                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = input,
                        onValueChange = { input = it },
                        label = { Text("Введите текст или номер телефона") },
                        singleLine = true
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val text = input.trim()
                            if (text.isEmpty()) {
                                showError("Введите текст для передачи во вторую Activity")
                                return@Button
                            }

                            val intent = Intent(context, SecondActivity::class.java)
                                .putExtra(SecondActivity.EXTRA_TEXT, text)
                            context.startActivity(intent)
                        }
                    ) {
                        Text("Открыть вторую Activity")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val phone = input.trim()
                            if (phone.isEmpty()) {
                                showError("Введите номер телефона")
                                return@Button
                            }
                            if (!Patterns.PHONE.matcher(phone).matches()) {
                                showError("Некорректный номер телефона")
                                return@Button
                            }

                            val dialIntent = Intent(
                                Intent.ACTION_DIAL,
                                "tel:${Uri.encode(phone)}".toUri()
                            )

                            if (dialIntent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(dialIntent)
                            } else {
                                showError("Нет приложения для совершения звонка")
                            }
                        }
                    ) {
                        Text("Позвонить другу")
                    }

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            val text = input.trim()
                            if (text.isEmpty()) {
                                showError("Введите текст для отправки")
                                return@Button
                            }

                            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, text)
                            }

                            val chooser = Intent.createChooser(sendIntent, "Поделиться через…")
                            if (sendIntent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(chooser)
                            } else {
                                showError("Нет приложения для отправки текста")
                            }
                        }
                    ) {
                        Text("Поделиться текстом")
                    }
                }
            }
        }
    }
}