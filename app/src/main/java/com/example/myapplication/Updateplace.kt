package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.models.PlaceModel
import com.google.firebase.database.FirebaseDatabase

class Updateplace : AppCompatActivity() {

    private lateinit var tvPlsId:TextView
    private lateinit var tvPlsName:TextView
    private lateinit var tvPlsPhone:TextView
    private lateinit var btnUpdate:Button
    private lateinit var btnDelete:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updateplace)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
           openUpdateDialog(
               intent.getStringExtra("placeId").toString(),
               intent.getStringExtra("placename").toString(),
               intent.getStringExtra("placenumber").toString()
           )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("placeId").toString()
            )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Places").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Employee data deleted",Toast.LENGTH_LONG).show()

            val intent = Intent(this, AdminPlaceView::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}",Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("CutPasteId")
    private fun initView(){

        tvPlsId = findViewById(R.id.tvPlsId)
        tvPlsName = findViewById(R.id.tvPlsName)
        tvPlsPhone = findViewById(R.id.tvPlsPhone)


        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

    }

    private fun setValuesToViews(){

        tvPlsId.text = intent.getStringExtra("placeId")
        tvPlsName.text = intent.getStringExtra("plcename")
        tvPlsPhone.text = intent.getStringExtra("placenumber")
    }

    private fun openUpdateDialog(
        placeId:String,
        placename:String,
        placenumber:String
    ){

        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog_place,null)

        mDialog.setView(mDialogView)

        val etPlsName = mDialogView.findViewById<EditText>(R.id.etPlsName)
        val etPlsPhone = mDialogView.findViewById<EditText>(R.id.etPlsPhone)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etPlsName.setText(intent.getStringExtra("placename").toString())
        etPlsPhone.setText(intent.getStringExtra("placenumber").toString())

        mDialog.setTitle("Updating $placename Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updatePlsData(
                placeId,
                etPlsName.text.toString(),
                etPlsPhone.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Update",Toast.LENGTH_LONG).show()

            //we are setting update data to our textviews
            tvPlsName.text = etPlsName.text.toString()
            tvPlsPhone.text = etPlsPhone.text.toString()

            alertDialog.dismiss()
        }


    }

    private fun updatePlsData(
        id:String,
        name: String,
        phone:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Places").child(id)
        val plsInfo = PlaceModel(id,name,phone)
        dbRef.setValue(plsInfo)

    }
}