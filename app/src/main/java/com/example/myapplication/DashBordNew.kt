package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView

class DashBordNew : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_bord_new)


        var placeCard = findViewById<CardView>(R.id.placeCard)
        placeCard.setOnClickListener{
            val intent = Intent(this,AdminPlaceView::class.java)
            startActivity(intent)
        }

        var shoppingCard = findViewById<CardView>(R.id.shoppingCard)
        shoppingCard.setOnClickListener{
            val intent = Intent(this,shoppingdashbord::class.java)
            startActivity(intent)
        }

        var hotelCard = findViewById<CardView>(R.id.hotelCard)
        hotelCard.setOnClickListener{
            val intent = Intent(this,AdminView::class.java)
            startActivity(intent)
        }

        var logoutCard= findViewById<CardView>(R.id.logoutCard)
        logoutCard.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}