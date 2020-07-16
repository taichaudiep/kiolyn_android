package com.example.kiolyn

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.mukesh.OtpView

class Login : AppCompatActivity() {

    //Root layout
    lateinit var rootLayout: RelativeLayout

    //Component of main layout
    lateinit var loginView: RelativeLayout
    lateinit var ip1Input: EditText
    lateinit var ip2Input: EditText
    lateinit var connectBtn: Button
    lateinit var signInBtn: Button
    lateinit var disconnectVtn: Button

    //Component of popup layout
    lateinit var popupView: RelativeLayout
    lateinit var otpInput: OtpView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_login)
        initializeUi()
    }

    private fun initializeUi() {
        rootLayout = findViewById(R.id.root_layout)

        loginView = findViewById(R.id.login_layout)
        ip1Input = findViewById(R.id.input_1)
        ip2Input = findViewById(R.id.input_2)
        connectBtn = findViewById(R.id.connect_btn)
        signInBtn = findViewById(R.id.signIn_btn)
        disconnectVtn = findViewById(R.id.disconnect_btn)

        popupView = findViewById(R.id.popup_layout)
        otpInput = findViewById(R.id.otp_view)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun connectClick(view: View) {
        isEnabledChild(false)
        showButton(false)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun disconnectClick(view: View) {
        showButton(true)
        ip1Input.setText("")
        ip2Input.setText("")
    }

    fun signIn(view: View) {
        val intent = Intent(this, com.example.kiolyn.List::class.java).apply{}
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun acceptClick(view: View) {
        isEnabledChild(true)
        ip1Input.text = otpInput.text
        otpInput.setText("")
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun isEnabledChild(isEnable: Boolean) {
        TransitionManager.beginDelayedTransition(rootLayout)//Animation when show and hide item

        popupView.visibility = if (isEnable) View.INVISIBLE else View.VISIBLE
        loginView.alpha = if (isEnable) 1f else 0.2f
        for (i in 1..loginView.childCount) {
            val child: View = loginView.getChildAt(i - 1)
            child.isEnabled = isEnable
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun showButton(isConnected: Boolean) {
        TransitionManager.beginDelayedTransition(rootLayout)//Animation when show and hide item

        connectBtn.visibility = if (isConnected) View.VISIBLE else View.INVISIBLE
        signInBtn.visibility = if (isConnected) View.INVISIBLE else View.VISIBLE
        disconnectVtn.visibility = if (isConnected) View.INVISIBLE else View.VISIBLE
    }

}
