package com.example.kiolyn

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.mukesh.OtpView
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    lateinit var connect_btn: Button
    lateinit var disconnect_btn: Button
    lateinit var signIn_btn: Button

    lateinit var popupWindow: PopupWindow

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

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun connectClick(view: View) {
        connect_btn.visibility = View.INVISIBLE
        signIn_btn.visibility = View.VISIBLE
        disconnect_btn.visibility = View.VISIBLE

        val layoutInflater = baseContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val popupView = layoutInflater.inflate(R.layout.popup_login, null)

        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        TransitionManager.beginDelayedTransition(root_layout)
        popupWindow.showAtLocation(root_layout, Gravity.CENTER, 0, 0)
        popupWindow.isFocusable = true;
        popupWindow.update();
    }

    fun acceptClick(view: View) {
        Toast.makeText(applicationContext, "Connected", Toast.LENGTH_SHORT).show()
        popupWindow.dismiss()
    }
}
