package com.example.academia.listener


interface ListenerAuth {
    fun onSucess(mensseger: String, screen: String)
    fun onFailure(error: String)
}