package com.example.kiolyn

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.kiolyn.databinding.ActivityLoginBinding
import com.example.kiolyn.model.Network
import de.mannodermaus.rxbonjour.BonjourBroadcastConfig
import de.mannodermaus.rxbonjour.BonjourEvent
import de.mannodermaus.rxbonjour.RxBonjour
import de.mannodermaus.rxbonjour.drivers.jmdns.JmDNSDriver
import de.mannodermaus.rxbonjour.platforms.android.AndroidPlatform
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers


class LoginActivity : AppCompatActivity() {

    private var nsdDisposable = Disposables.empty()
    lateinit var binding: ActivityLoginBinding

    private lateinit var network: Network

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        network = Network()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun connectClick(view: View) {
        isEnabledChild(false)
        showButton(false)
        binding.otpView.requestFocusFromTouch()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun disconnectClick(view: View) {
        showButton(true)
        binding.input1.setText("")
        binding.input2.setText("")
    }

    fun signIn(view: View) {
        val intent = Intent(this, com.example.kiolyn.ListActivity::class.java).apply {}
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun acceptClick(view: View) {
        isEnabledChild(true)
        binding.otpView.setText("")
        dns()
    }

    private fun dns() {
        val rxBonjour = RxBonjour.Builder()
            .platform(AndroidPlatform.create(this))
            .driver(JmDNSDriver.create())
            .create()

        val broadcastConfig = BonjourBroadcastConfig(
            type = "_http._tcp",
            name = "My Bonjour Service",
            address = null,
            txtRecords = mapOf(
                "my.record" to "my value",
                "other.record" to "0815"
            )
        )

        val disposable = rxBonjour.newBroadcast(broadcastConfig)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        nsdDisposable = rxBonjour.newDiscovery("_http._tcp")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { event ->
                    when (event) {
                        is BonjourEvent.Added -> {
                            network.setHost("${event.service.host}")
                            network.setPort("${event.service.port}")
                            binding.setVariable(BR.network, network)
                            binding.executePendingBindings()

                            Log.i("LOG_TAG", "host: ${event.service.host}")
                            Log.i("LOG_TAG", "name: ${event.service.name}")
                            Log.i("LOG_TAG", "port: ${event.service.port}")
                            Log.i("LOG_TAG", "txtRecords: ${event.service.txtRecords}")
                            Log.i("LOG_TAG", "type: ${event.service.type}")
                            Log.i("LOG_TAG", "v4Host: ${event.service.v4Host}")
                            Log.i("LOG_TAG", "v6Host: ${event.service.v6Host}")
                        }
                        is BonjourEvent.Removed -> Log.i("LOG_TAG", "Removed Event: $event")
                    }
                },
                { error ->
                    Log.i("LOG_TAG", "Error: $error")
                }
            )
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun isEnabledChild(isEnable: Boolean) {
        TransitionManager.beginDelayedTransition(binding.rootLayout)//Animation when show and hide item

        binding.popupLayout.visibility = if (isEnable) View.INVISIBLE else View.VISIBLE
        binding.loginLayout.alpha = if (isEnable) 1f else 0.2f
        for (i in 1..binding.loginLayout.childCount) {
            val child: View = binding.loginLayout.getChildAt(i - 1)
            child.isEnabled = isEnable
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun showButton(isConnected: Boolean) {
        TransitionManager.beginDelayedTransition(binding.rootLayout)//Animation when show and hide item

        binding.connectBtn.visibility = if (isConnected) View.VISIBLE else View.INVISIBLE
        binding.signInBtn.visibility = if (isConnected) View.INVISIBLE else View.VISIBLE
        binding.disconnectBtn.visibility = if (isConnected) View.INVISIBLE else View.VISIBLE
    }
}