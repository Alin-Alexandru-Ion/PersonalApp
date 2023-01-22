package com.example.personalapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LocationSearch : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_search)
        val actionBar = supportActionBar
        actionBar!!.title = "Alin AI"

        val buttonClick = findViewById<Button>(R.id.searchButton)
        val searchBox = findViewById <EditText> (R.id.searchBox)

        buttonClick.setOnClickListener {
            val passable = searchBox.text.toString()
            val intent = Intent (applicationContext, MainActivity::class.java)
            intent.putExtra("Test", passable)
            startActivity(intent)
        }
    }
}