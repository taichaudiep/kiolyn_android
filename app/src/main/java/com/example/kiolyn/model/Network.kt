package com.example.kiolyn.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

//data class Network(val host:String, val port: String)

class Network : BaseObservable() {

    private lateinit var host: String
    private lateinit var port: String

    fun Network(host: String, port: String) {
        this.host = host
        this.port = port
    }

    @Bindable
    fun getHost(): String {
        return host
    }

    fun setHost(host: String) {
        this.host = host
    }

    @Bindable
    fun getPort(): String {
        return port
    }

    fun setPort(port: String) {
        this.port = port
    }
}