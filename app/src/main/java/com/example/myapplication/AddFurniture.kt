package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.models.FurnitureModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddFurniture : AppCompatActivity() {

    private lateinit var addfurniturename:EditText
    private lateinit var addfurniturebranchername:EditText
    private lateinit var addfurnitureprice:EditText
    private lateinit var addfurniturretingtext:EditText
    private lateinit var  addopeninghourstext:EditText
    private lateinit var  addfurniturebtn:Button


    private lateinit var dbRef : DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_furniture)

        addfurniturename = findViewById(R.id.addfurniturename)
        addfurniturebranchername = findViewById(R.id.addfurniturebranchername)
        addfurnitureprice = findViewById(R.id.addfurnitureprice)
        addfurniturretingtext = findViewById(R.id.addfurniturretingtext)
        addopeninghourstext = findViewById(R.id.addopeninghourstext)

        addfurniturebtn = findViewById(R.id.addfurniturebtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Furniture")

        addfurniturebtn.setOnClickListener{
            saveFurnitureData()
        }
    }


    private fun saveFurnitureData(){

        //getting values
        val furStoreName = addfurniturename.text.toString()
        val furBranchName = addfurniturebranchername.text.toString()
        val furPrice = addfurnitureprice.text.toString()
        val furRating = addfurniturretingtext.text.toString()
        val furOpenHour = addopeninghourstext.text.toString()

        if(furStoreName.isEmpty()){
            addfurniturename.error = "Please enter Store Name"
        }

        if(furBranchName.isEmpty()){
            addfurniturebranchername.error = "Please enter Branch Name"
        }

        if(furPrice.isEmpty()){
            addfurnitureprice.error = "Please enter  Price"
        }

        if(furRating.isEmpty()){
            addfurniturretingtext.error = "Please enter Rating"
        }

        if(furOpenHour.isEmpty()){
            addopeninghourstext.error = "Please enter Opening Hours"
        }

        val furId = dbRef.push().key!!

        val  furniture = FurnitureModel(furId,furStoreName,furBranchName,furPrice,furRating,furOpenHour,)

        dbRef.child(furId).setValue(furniture)
            .addOnCompleteListener{
                Toast.makeText(this,"Data insert successfully", Toast.LENGTH_LONG).show()
                addfurniturename.text.clear()
                addfurniturebranchername.text.clear()
                addfurnitureprice.text.clear()
                addfurniturretingtext.text.clear()
                addopeninghourstext.text.clear()
            }.addOnFailureListener{err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }

    }
}

