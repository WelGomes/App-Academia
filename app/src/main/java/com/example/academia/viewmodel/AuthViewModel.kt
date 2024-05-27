package com.example.academia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.academia.listener.ListenerAuth
import com.example.academia.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {

    private val _nome = MutableStateFlow("")
    private val nome: StateFlow<String> = _nome

    fun register(name: String, email: String, password: String, listenerAuth: ListenerAuth) {
        viewModelScope.launch {
            authRepository.register(name, email, password, listenerAuth)
        }
    }

    fun login(email: String, password: String, listenerAuth: ListenerAuth) {
        viewModelScope.launch {
            authRepository.login(email, password, listenerAuth)
        }
    }

    fun isUserLogin(): kotlinx.coroutines.flow.Flow<Boolean> {
        return authRepository.isUserLogin()
    }

    fun perfilUser(): Flow<String> {
        viewModelScope.launch {
            authRepository.perfilUser().collect{
                _nome.value = it
            }
        }
        return nome
    }

}
