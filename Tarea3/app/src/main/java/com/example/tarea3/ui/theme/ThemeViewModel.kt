package com.example.tarea3.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarea3.data.ThemePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class ThemeViewModel(app: Application) : AndroidViewModel(app) {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    init {
        ThemePreferences.isDarkMode(app.applicationContext)
            .onEach { _isDarkMode.value = it }
            .launchIn(viewModelScope)
    }

    fun toggleTheme() {
        viewModelScope.launch {
            ThemePreferences.setDarkMode(getApplication(), !_isDarkMode.value)
        }
    }
}
