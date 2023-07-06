package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class shoppingdashbord : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shoppingdashbord)

        var furniturCard = findViewById<CardView>(R.id.furniturCard)
        furniturCard.setOnClickListener{
            val intent = Intent(this,AdminFuritureView::class.java)
            startActivity(intent)
        }

        var teaCard = findViewById<CardView>(R.id.teaCard)
        teaCard.setOnClickListener{
            val intent = Intent(this,AdminteaView::class.java)
            startActivity(intent)
        }

    }
}