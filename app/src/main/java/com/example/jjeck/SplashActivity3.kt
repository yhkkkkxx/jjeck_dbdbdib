package com.example.jjeck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent

class SplashActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash3)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        if (action == MotionEvent.ACTION_DOWN) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onTouchEvent(event)
    }
}