package com.example.lorelocker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val wlayout = findViewById<LinearLayout>(R.id.worldlayout)
        val llayout = findViewById<LinearLayout>(R.id.lorelayout)
        val dlayout = findViewById<LinearLayout>(R.id.detailslayout)
        val slayout = findViewById<LinearLayout>(R.id.storylayout)

        val homenav = findViewById<ImageButton>(R.id.homebtn)
        val worldnav = findViewById<ImageButton>(R.id.worldbtn)
        val lorenav = findViewById<ImageButton>(R.id.lorebtn)
        val detailnav = findViewById<ImageButton>(R.id.detailbtn)
        val storynav = findViewById<ImageButton>(R.id.storybtn)

        val worldvaultchange = View.OnClickListener {
            val intent = Intent(this, WorldVault::class.java)
            startActivity(intent)
        }
        val lorevaultchange = View.OnClickListener {
            val intent = Intent(this, LoreVault::class.java)
            startActivity(intent)
        }
        val detailvaultchange = View.OnClickListener {
            val intent = Intent(this, DetailVault::class.java)
            startActivity(intent)
        }
        val storyvaultchange = View.OnClickListener {
            val intent = Intent(this, StoryVault::class.java)
            startActivity(intent)
        }

        wlayout.setOnClickListener(worldvaultchange)
        llayout.setOnClickListener(lorevaultchange)
        dlayout.setOnClickListener(detailvaultchange)
        slayout.setOnClickListener(storyvaultchange)

        homenav.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        worldnav.setOnClickListener(worldvaultchange)
        lorenav.setOnClickListener(lorevaultchange)
        detailnav.setOnClickListener(detailvaultchange)
        storynav.setOnClickListener(storyvaultchange)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}