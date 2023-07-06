package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.models.HotModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Addhotel : AppCompatActivity() {

    private lateinit var addhotelname : EditText
    private lateinit var addhotledistrict : EditText
    private lateinit var addhoteltextphone : EditText
    private lateinit var addhotelretingtext : EditText
    private lateinit var addhotlebtn : Button

    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addhotel)

        addhotelname = findViewById(R.id.addhotelname)
        addhotledistrict = findViewById(R.id.addhotledistrict)
        addhoteltextphone = findViewById(R.id.addhoteltextphone)
        addhotelretingtext= findViewById(R.id.addhotelretingtext)
        addhotlebtn =  findViewById(R.id.addhotlebtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Hotels")

        addhotlebtn.setOnClickListener{
            saveHotelData()
        }

    }

    private fun saveHotelData(){

        //getting values
        val hotName = addhotelname.text.toString()
        val hotDistrict = addhotledistrict.text.toString()
        val hotPhone = addhoteltextphone.text.toString()
        val hotRetting = addhotelretingtext.text.toString()

        if(hotName.isEmpty()){
            addhotelname.error = "Please enter the Name"
        }

        if(hotDistrict.isEmpty()){
            addhotledistrict.error = "Please enter the District"
        }

        if(hotPhone.isEmpty()){
            addhoteltextphone.error = "Please enter the Hotel Name"
        }

        if(hotRetting.isEmpty()){
            addhotelretingtext.error = "Please enter the Hotel Retting"
        }

        val hotId = dbRef.push().key!!

        val hotel = HotModel(hotId,hotName,hotDistrict,hotPhone,hotRetting)

        dbRef.child(hotId).setValue(hotel)
            .addOnCompleteListener{
                Toast.makeText(this,"Data insert successfully",Toast.LENGTH_LONG).show()

                addhotelname.text.clear()
                addhotledistrict.text.clear()
                addhoteltextphone.text.clear()
                addhotelretingtext.text.clear()

            }.addOnFailureListener{ err->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }


    }
}