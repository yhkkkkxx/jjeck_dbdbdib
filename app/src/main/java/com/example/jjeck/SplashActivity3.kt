package com.example.jjeck

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.Locale

class SplashActivity3 : AppCompatActivity() {
    private lateinit var korean: Button
    private lateinit var english: Button
    private lateinit var chinese: Button
    private lateinit var japanese: Button
    private lateinit var language_code: String

    var textView: TextView? = null
    var button: Button? = null
    @SuppressLint("MissingInflatedId")
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
            intent.putExtra("lang", "ko")
            Toast.makeText(this, "한글을 선택하셨습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
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

        setContentView(R.layout.activity_main)

        textView = findViewById<View>(R.id.textView_main_result) as TextView
        button = findViewById<View>(R.id.listView_button) as Button
        button!!.setOnClickListener( { sendRequest() })
    }


    private fun sendRequest() {
        val url = "http://10.0.2.2/accom_info_kor.php"

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response: String ->
                textView!!.text = "response: $response"
            }
        ) { error: VolleyError -> textView!!.text = "error: ${error.message}" }

        queue.add(stringRequest)
    }

    fun println(data: String) {
        textView!!.text = """
             $data
             
             """.trimIndent()
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