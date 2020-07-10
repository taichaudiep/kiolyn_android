package com.example.kiolyn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.core.view.isVisible

class Login : AppCompatActivity() {

    lateinit var connect_btn: Button
    lateinit var disconnect_btn: Button
    lateinit var signIn_btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_login)

        connect_btn = findViewById(R.id.connect_btn)
        signIn_btn = findViewById(R.id.signIn_btn)
        disconnect_btn = findViewById(R.id.disconnect_btn)
    }

    fun connectClick(view: View) {
        connect_btn.visibility = View.INVISIBLE
        signIn_btn.visibility = View.VISIBLE
        disconnect_btn.visibility = View.VISIBLE
    }
}
