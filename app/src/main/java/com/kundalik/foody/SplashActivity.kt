package com.kundalik.foody

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    private val SPLASH_DELAY: Long = 2000 // Delay in milliseconds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Use a Handler to delay the transition to the next activity
        Handler().postDelayed({
            // Create an Intent to start the next activity
            val intent = Intent(this@SplashActivity, SignInActivity::class.java)
            startActivity(intent)
            finish() // Finish the splash activity
        }, SPLASH_DELAY)
    }
}