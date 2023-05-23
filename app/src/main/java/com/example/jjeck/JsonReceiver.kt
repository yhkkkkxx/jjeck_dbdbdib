package com.example.jjeck

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class JsonReceiver : AppCompatActivity()  {
    var textView: TextView? = null
    var button: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        textView = findViewById<View>(R.id.textView_main_result) as TextView
        button = findViewById<View>(R.id.listView_button) as Button
        button!!.setOnClickListener( { sendRequest() })
    }

    fun sendRequest() {
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
}
