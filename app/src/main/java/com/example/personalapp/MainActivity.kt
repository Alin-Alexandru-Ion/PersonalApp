package com.example.personalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val urlDump = findViewById <TextView> (R.id.urlDump)
        val urlButton = findViewById <Button> (R.id.urlButton)

        var city = "Giurgiu"

        urlButton.setOnClickListener()
        {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://weatherapi-com.p.rapidapi.com/search.json?q=$city")
                .get()
                .addHeader("X-RapidAPI-Key", "7a584c54b7msh85a521175a1bcefp15860cjsn74e20d2d80e2")
                .addHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .build()

            client.newCall(request).enqueue(object: Callback{
                override fun onFailure(call: Call, e: IOException)
                {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response)
                {
                    Log.i("Response", "Response received")
                    response.use {
                        if(!response.isSuccessful)
                        {
                            Log.e("HTTP Error", "Something didn't went well")
                        }
                        else
                        {
                            val body = response?.body?.string()
                            urlDump.text = body
                        }
                    }
                }
            })
        }
    }
}