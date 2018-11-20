package com.inducesmile.temptoday.views

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.inducesmile.temptoday.common.BaseActivity
import java.util.*

/**
 * Created by BalvirJha on 10-11-2018.
 */

class MainActivity : BaseActivity() {
    var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        timer = Timer(true)
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@MainActivity, WeatherActivity::class.java))
                this@MainActivity.finish()
                overridePendingTransitionExit()
            }
        }, 0L, 4000L)
    }

    companion object {

        private val TAG = MainActivity::class.java.getSimpleName()
    }

    override fun onDestroy() {
        timer?.cancel()
        timer = null
        super.onDestroy()
    }

}
