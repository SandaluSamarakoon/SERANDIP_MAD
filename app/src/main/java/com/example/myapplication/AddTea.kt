package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.myapplication.models.PlaceModel
import com.example.myapplication.models.TeaModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddTea : AppCompatActivity() {


    private lateinit var addTeaBrand : EditText
    private lateinit var addTeaType: EditText
    private lateinit var addPrice : EditText
    private lateinit var addTeaRet : EditText
    private lateinit var addData: Button

    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tea)



        addTeaBrand = findViewById(R.id.addTeaBrand)
        addTeaType = findViewById(R.id.addTeaType)
        addPrice = findViewById(R.id.addPrice)
        addTeaRet = findViewById(R.id.addTeaRet)
        addData= findViewById(R.id.addData)

        dbRef = FirebaseDatabase.getInstance().getReference("Tea")

        addData.setOnClickListener{
            saveTeaData()
        }
    }


    private fun saveTeaData(){

        //getting values
        val teaBrand = addTeaBrand.text.toString()
        val teaType = addTeaType.text.toString()
        val teaPrice = addPrice.text.toString()
        val teaRetting = addTeaRet.text.toString()

        if(teaBrand .isEmpty()){
            addTeaBrand.error = "Please enter the Brand"
        }

        if(teaType.isEmpty()){
            addTeaType.error = "Please enter the Type"
        }

        if(teaPrice.isEmpty()){
            addPrice.error = "Please enter the Price"
        }

        if(teaRetting.isEmpty()){
            addTeaRet.error = "Please enter the Retting"
        }

        val teaId = dbRef.push().key!!

        val tea = TeaModel(teaId,teaBrand,teaType,teaPrice,teaRetting)

        dbRef.child(teaId).setValue(tea)
            .addOnCompleteListener{
                Toast.makeText(this,"Data insert successfull", Toast.LENGTH_LONG).show()

                addTeaBrand.text.clear()
                addTeaType.text.clear()
                addPrice.text.clear()
                addTeaRet.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }


    }
}