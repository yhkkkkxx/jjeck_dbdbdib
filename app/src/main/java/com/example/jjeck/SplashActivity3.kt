package com.example.jjeck

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akexorcist.localizationactivity.core.LanguageSetting.setLanguage
import java.util.Locale
;

class SplashActivity3 : AppCompatActivity() {
    private lateinit var korean: Button
    private lateinit var english: Button
    private lateinit var chinese: Button
    private lateinit var japanese: Button
    private lateinit var language_code: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash3)
        korean = findViewById(R.id.button1)
        english = findViewById(R.id.button2)
        chinese = findViewById(R.id.button3)
        japanese = findViewById(R.id.button4)


        korean.setOnClickListener {
            //PreferenceManager(this).updateLanguage("ta")
            setAppLocale(this, "ko")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("lang", "ko")
            Toast.makeText(this, "한글을 선택하셨습니다.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        english.setOnClickListener {
            setAppLocale(this, "en")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("lang", "en")
            Toast.makeText(this, "You choose english", Toast.LENGTH_SHORT).show()
            startActivity(intent)

        }

        chinese.setOnClickListener {
            setAppLocale(this, "zh")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("lang", "zh")
            Toast.makeText(this, "您选择了中文.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
        japanese.setOnClickListener {
            setAppLocale(this, "ja")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("lang", "ja")
            Toast.makeText(this, "日本語を選びました.", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        if (action == MotionEvent.ACTION_DOWN) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        return super.onTouchEvent(event)
    }
    fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.configuration.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

    }


}