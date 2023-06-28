package com.nationalconclave.bseb.ui.main.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.nationalconclave.bseb.R
import com.nationalconclave.bseb.ui.main.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        Handler().postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            //startActivity(Intent(this,RegistrationActivity::class.java))
            finish()
        },3000)
    }
}
