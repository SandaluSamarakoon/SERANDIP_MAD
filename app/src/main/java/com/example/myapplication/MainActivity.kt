package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        var tvSignUpSecond = findViewById<TextView>(R.id.tvSignUpSecond)
        tvSignUpSecond.setOnClickListener{

            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        var tvSignUp = findViewById<TextView>(R.id.tvSignup)
        tvSignUp.setOnClickListener{

            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        var btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener{
            val intent = Intent(this,DashBordNew::class.java)
            startActivity(intent)
        }




    }
}