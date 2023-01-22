package com.example.personalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import okhttp3.*
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar = supportActionBar
        actionBar!!.title = "Alin AI"

        val intent = getIntent()
        var intrare = intent.getStringExtra("Test").toString()

        val imageView = findViewById<ImageView>(R.id.iconUrl)
        var temperatura = findViewById<TextView>(R.id.tempDegree)
        var locatie = findViewById<TextView>(R.id.locationBox)
        var regiune = findViewById<TextView>(R.id.regionBox)
        var urlPoza = "Blank"
        var grade = "0"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://weatherapi-com.p.rapidapi.com/forecast.json?q=$intrare&days=1")
            .get()
            .addHeader("X-RapidAPI-Key", "7a584c54b7msh85a521175a1bcefp15860cjsn74e20d2d80e2")
            .addHeader("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if(!response.isSuccessful) {
                        Log.e("HTTP Error", "Something didn't went well")
                    }
                    else {
                        val jsonResponse = JSONTokener(response.body?.string()).nextValue() as JSONObject

                        val bazaLocation = jsonResponse.getJSONObject("location")
                        val bazaCurr = jsonResponse.getJSONObject("current")
                            val bazaCond = bazaCurr.getJSONObject("condition")

                        locatie.text = bazaLocation.getString("name")
                        regiune.text = bazaLocation.getString("region") + ", " + bazaLocation.getString("country")
                        grade = bazaCurr.getString("temp_c")
                            urlPoza = bazaCond.getString("icon")
                    }
                }
            }
        })
        Thread.sleep(1_000)

        var imageURL = "https:" + urlPoza
        Picasso.with(this).load(imageURL).into(imageView)
        temperatura.text = grade + " C"

        Picasso.with(this).load(imageURL).into(imageView)
        temperatura.text = grade + " C"
    }
}