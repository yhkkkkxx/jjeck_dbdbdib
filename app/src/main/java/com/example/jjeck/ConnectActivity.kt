package com.example.jjeck

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.jjeck.AppHelper.requestQueue


class ConnectActivity : AppCompatActivity() {
    var requestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //요청 큐가 없으면 요청 큐 생성하기
        //나중에 여기다가 데이터 담으면 알아서!!!!!!! 통신함 ㅋ
        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext())
        }
        login()
    }

    fun login() {
        //php url 입력
        val URL = "https://google.co.kr"
        val request: StringRequest = object : StringRequest(
            Method.POST, URL,
            Response.Listener<String> { response -> //응답이 되었을때 response로 값이 들어옴
                Toast.makeText(getApplicationContext(), "응답:$response", Toast.LENGTH_SHORT).show()
            }, Response.ErrorListener { error -> //에러나면 error로 나옴
                Toast.makeText(getApplicationContext(), "에러:" + error.message, Toast.LENGTH_SHORT)
                    .show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String>? {
                //php로 설정값을 보낼 수 있음
                return HashMap()
            }
        }
        request.setShouldCache(false)
        AppHelper.requestQueue.add(request)
    }
}


/*

class ConnectActivity : AppCompatActivity() {
    var textView: TextView? = null
    var button: Button? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        textView = findViewById<View>(R.id.textView_main_result) as TextView
        button = findViewById<View>(R.id.listView_button) as Button
        button!!.setOnClickListener({ sendRequest() })

        if (AppHelper.requestQueue != null) {
            AppHelper.requestQueue = Volley.newRequestQueue(applicationContext)


            */
/*    super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_main)

            textView = findViewById<View>(R.id.textView_main_result) as TextView
            button = findViewById<View>(R.id.listView_button) as Button
            button!!.setOnClickListener( { sendRequest() })

            if(AppHelper.requestQueue != null) {
                AppHelper.requestQueue = Volley.newRequestQueue(applicationContext)
            }
        }

        private fun sendRequest() {
            val url = "https://www.figma.com/file/qvkUwU14iomx4swD6pT6KT/Untitled?type=design&node-id=2-30&t=yUiROyxzsAjnp5wa-0"
            val request: StringRequest = object : StringRequest(
                Method.GET,
                url,
                Response.Listener { response ->
                    //응답을 잘 받았을 때 이 메소드가 자동으로 호출
                    println("응답 -> $response")
                },
                Response.ErrorListener { error ->
                    //에러 발생시 호출될 리스너 객체
                    println("에러 -> " + error.message)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    return HashMap()
                }
            }
            request.setShouldCache(false) //이전 결과 있어도 새로 요청하여 응답을 보여준다.
            AppHelper.requestQueue = Volley.newRequestQueue(this) // requestQueue 초기화 필수
            AppHelper.requestQueue.add(request)
            println("요청 보냄.")*//*

        }
        */
/*
    fun println(data: String) {
        textView!!.text = """
             $data
             
             """.trimIndent()
    }*//*



    }

    private fun sendRequest() {
        val url = "https://www.google.co.kr/imghp?hl=en&tab=ri&ogbl"

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response: String ->
                textView!!.text = "response: " + response.substring(0, 500)
            }
        ) { error: VolleyError -> textView!!.text = "error: " + error.message }

        queue.add(stringRequest)
    }
}

*/
/*

fun login() {
    //php url 입력
    val URL = "https://google.co.kr"
    val request: StringRequest = object : StringRequest(
        Method.POST, URL,
        Response.Listener<String> { response -> //응답이 되었을때 response로 값이 들어옴
            Toast.makeText(ConnectActivity.ApplicationContext(), "응답:$response", Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener { error -> //에러나면 error로 나옴
            Toast.makeText(ConnectActivity.ApplicationContext(), "에러:" + error.message, Toast.LENGTH_SHORT)
                .show()
        }) {
        @Throws(AuthFailureError::class)
        override fun getParams(): Map<String, String>? {
            //php로 설정값을 보낼 수 있음
            return HashMap()
        }
    }
    request.setShouldCache(false)
    requestQueue.add(request)
}*//*

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //요청 큐가 없으면 요청 큐 생성하기
        //나중에 여기다가 데이터 담으면 알아서!!!!!!! 통신함 ㅋ
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        login();

    }

    public void login() {
        //php url 입력
        String URL = "http://godlove5949.dothome.co.kr/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //응답이 되었을때 response로 값이 들어옴
                Toast.makeText(getApplicationContext(), "응답:" + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //에러나면 error로 나옴
                Toast.makeText(getApplicationContext(), "에러:" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                //php로 설정값을 보낼 수 있음
                return param;
            }
        };


        request.setShouldCache(false);
        requestQueue.add(request);
    }*/