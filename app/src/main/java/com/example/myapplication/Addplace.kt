package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.models.PlaceModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Addplace : AppCompatActivity() {

    private lateinit var addplacename : EditText
    private lateinit var adddistricname: EditText
    private lateinit var addtextplacephone : EditText
    private lateinit var placedescription : EditText
    private lateinit var addplacebtn: Button

    private lateinit var dbRef: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addplace)

        addplacename = findViewById(R.id.addplacename)
        adddistricname = findViewById(R.id.adddistricname)
        addtextplacephone = findViewById(R.id.addtextplacephone)
        placedescription = findViewById(R.id.placedescription)
        addplacebtn = findViewById(R.id.addplacebtn)

        dbRef = FirebaseDatabase.getInstance().getReference("Places")

        addplacebtn.setOnClickListener{
            savePlaceData()
        }
    }

    private fun savePlaceData(){

        //getting values
        val placename = addplacename.text.toString()
        val dictrict = adddistricname.text.toString()
        val phoneno = addtextplacephone.text.toString()
        val description = placedescription.text.toString()


        //validetion

        if(placename.isEmpty()){
            addplacename.error = "Please enter the Name"
        }

        if(dictrict.isEmpty()){
            adddistricname.error = "Please enter the district"
        }

        if(phoneno.isEmpty()){
            addtextplacephone.error = "Please enter the Phone Number"
        }

        if(description.isEmpty()){
            placedescription.error = "Please enter the Discription"
        }

        val plsId = dbRef.push().key!!

        val place = PlaceModel(plsId,placename,dictrict,phoneno,description)

        dbRef.child(plsId).setValue(place)
            .addOnCompleteListener{
                Toast.makeText(this,"Data insert successfull",Toast.LENGTH_LONG).show()

                addplacename.text.clear()
                adddistricname.text.clear()
                addtextplacephone.text.clear()
                placedescription.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }


    }
}