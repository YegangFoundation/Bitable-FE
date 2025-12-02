package com.example.bitable_fe.core.ui.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitable_fe.core.data.datastore.UserPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPreferencesViewModel @Inject constructor(
    private val prefs: UserPreferencesDataStore
) : ViewModel() {

    val userIdFlow = prefs.userId

    fun saveUserId(id: Long) {
        viewModelScope.launch {
            prefs.saveUserId(id)
        }
    }
}
