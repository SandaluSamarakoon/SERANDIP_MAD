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
import com.example.myapplication.models.TeaModel
import com.google.firebase.database.FirebaseDatabase

class Updatetea : AppCompatActivity() {


    private lateinit var tvTeaId : TextView
    private lateinit var tvTeaName : TextView
    private lateinit var tvTeaType : TextView
    private lateinit var tvTeaPrice : TextView
    private lateinit var tvTeaRet : TextView
    private lateinit var btnUpdate  : Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updatetea)

        initView()
        setValuesToViews()


        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("teaId").toString(),
                intent.getStringExtra("teaBrand").toString(),
                //intent.getStringExtra("placenumber").toString()
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("teaId").toString()
            )
        }

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Tea").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Tea data deleted",Toast.LENGTH_LONG).show()

            val intent = Intent(this, AdminteaView::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}",Toast.LENGTH_LONG).show()
        }
    }




    private fun initView(){

        tvTeaId = findViewById(R.id.tvTeaId)
        tvTeaName = findViewById(R.id.tvTeaName)
        tvTeaType = findViewById(R.id.tvTeaType)
        tvTeaPrice = findViewById(R.id.tvTeaPrice)
        tvTeaRet = findViewById(R.id.tvTeaRet)

        btnDelete= findViewById(R.id.btnDelete)
        btnUpdate= findViewById(R.id.btnUpdate)

    }

    private fun setValuesToViews(){

        tvTeaId.text = intent.getStringExtra("teaId")
        tvTeaName.text = intent.getStringExtra("teaBrand")
        tvTeaType.text = intent.getStringExtra("teaType")
        tvTeaPrice.text = intent.getStringExtra("teaPrice")
        tvTeaRet.text = intent.getStringExtra("teaRetting")
    }

    @SuppressLint("MissingInflatedId")
    private fun openUpdateDialog(
        teaId:String,
        teaBrand:String
    ){

        val mDialog =  AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog_tea,null)

        mDialog.setView(mDialogView)

        val etTeaName = mDialogView.findViewById<EditText>(R.id.etTeaName)
        val etTeaType = mDialogView.findViewById<EditText>(R.id.etTeaType)
        val etTeaPri = mDialogView.findViewById<EditText>(R.id.etTeaPri)
        val etTeaRetting = mDialogView.findViewById<EditText>(R.id.etTeaRetting)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)


        etTeaName.setText(intent.getStringExtra("teaBrand").toString())
        etTeaType.setText(intent.getStringExtra("teaType").toString())
        etTeaPri.setText(intent.getStringExtra("teaPrice").toString())
        etTeaRetting.setText(intent.getStringExtra("teaRetting").toString())

        mDialog.setTitle("Updating $teaBrand Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateTeaData(
                teaId,
                etTeaName.text.toString(),
                etTeaType.text.toString(),
                etTeaPri.text.toString(),
                etTeaRetting.text.toString()
            )

            Toast.makeText(applicationContext,"Data Updateed",Toast.LENGTH_LONG).show()

            tvTeaName.text = etTeaName.text.toString()
            tvTeaType.text = etTeaType.text.toString()
            tvTeaPrice.text = etTeaPri.text.toString()
            tvTeaRet.text = etTeaRetting.text.toString()

            alertDialog.dismiss()
        }

//        Toast.makeText(applicationContext,"Data Updateed",Toast.LENGTH_LONG).show()
//
//        tvTeaName.text = etTeaName.text.toString()
//        tvTeaType.text = etTeaType.text.toString()
//        tvTeaPrice.text = etTeaPri.text.toString()
//        tvTeaRet.text = etTeaRetting.text.toString()
//
//        alertDialog.dismiss()
    }

    private  fun updateTeaData(
        id:String,
        name:String,
        type:String,
        price:String,
        rating:String,

    ){
        val dbRef= FirebaseDatabase.getInstance().getReference("Tea").child(id)
        val teaInfo = TeaModel(id,name,type,price,rating)
        dbRef.setValue(teaInfo)

    }
}