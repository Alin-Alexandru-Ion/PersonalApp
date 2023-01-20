package com.example.personalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.serialization.Serializable
import okhttp3.*
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val urlDump = findViewById <TextView> (R.id.urlDump)
        val urlButton = findViewById <Button> (R.id.urlButton)

        var localitate = "Vedea"

        urlButton.setOnClickListener()
        {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("https://weatherapi-com.p.rapidapi.com/forecast.json?q=$localitate&days=1")
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
                            val jsonResponse = JSONTokener(response.body?.string()).nextValue() as JSONObject
                            urlDump.text = jsonResponse.toString()

                            val loc = jsonResponse.getString("location")
                            Log.i("ID: ", loc)

                            val curr = jsonResponse.getString("current")
                            Log.i("ID: ", curr)

                            val bazaGrade = jsonResponse.getJSONObject("current")
                            val grade = bazaGrade.getString("temp_c")
                            Log.i("Celsius Degrees: ", grade)
                        }
                    }
                }
            })
        }
    }
}