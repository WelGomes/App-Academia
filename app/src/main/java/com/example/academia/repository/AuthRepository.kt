package com.example.academia.repository

import com.example.academia.datasource.Auth
import com.example.academia.listener.ListenerAuth
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class AuthRepository @Inject constructor(private val auth: Auth) {

    fun register(name: String, email: String, password: String, listenerAuth: ListenerAuth) {
        auth.register(name, email, password, listenerAuth)
    }

    fun login(email: String, password: String, listenerAuth: ListenerAuth) {
        auth.login(email, password,listenerAuth)
    }

    fun isUserLogin(): Flow<Boolean> {
        return auth.isUserLogin()
    }

    fun perfilUser(): Flow<String> {
        return auth.perfilUser()
    }

}