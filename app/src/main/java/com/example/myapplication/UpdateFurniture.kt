package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Path.Op
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R.id.*
import com.example.myapplication.models.FurnitureModel
import com.google.firebase.database.FirebaseDatabase

class UpdateFurniture : AppCompatActivity() {

    private lateinit var tvFurId : TextView
    private lateinit var tvFurName : TextView
    private lateinit var tvFurBra : TextView
    private lateinit var tvFurPrice: TextView
    private lateinit var tvFurRet : TextView
    private lateinit var tvFurOpn : TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_furniture)

        initView()
        setValuerToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("FurnId").toString(),
                intent.getStringExtra("Furnstore").toString()
            )
        }

        btnDelete.setOnClickListener{
           deleteRecord(
               intent.getStringExtra("FurnId").toString()
           )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Furniture").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Furniture data deleted",Toast.LENGTH_LONG).show()

            val intent = Intent(this,AdminFuritureView::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->

            Toast.makeText(this,"Deleting Err ${error.message}",Toast.LENGTH_LONG).show()
        }

    }
    private fun initView(){

        tvFurId = findViewById(R.id.tvFurId)
        tvFurName = findViewById(R.id.tvFurName)
        tvFurBra = findViewById(R.id.tvFurBra)
        tvFurPrice = findViewById(R.id.tvFurPrice)
        tvFurRet = findViewById(R.id.tvFurRet)
        tvFurOpn = findViewById(R.id.tvFurOpn)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

    }
    private fun setValuerToViews(){
        tvFurId.text = intent.getStringExtra("FurnId")
        tvFurName.text = intent.getStringExtra("Furnstore")
        tvFurBra.text = intent.getStringExtra("FurBrancher")
        tvFurPrice.text = intent.getStringExtra("FurnPrice")
        tvFurRet.text = intent.getStringExtra("FurnRating")
        tvFurOpn.text = intent.getStringExtra("FurnHours")
    }

    private fun openUpdateDialog(
        FurnId : String,
        Furnstore: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val  inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog_furniture,null)

        mDialog.setView(mDialogView)

        val etFurName = mDialogView.findViewById<EditText>(etFurName)
        val etFurBur = mDialogView.findViewById<EditText>(etFurBur)
        val etFurPrice = mDialogView.findViewById<EditText>(etFurPrice)
        val etFurRet = mDialogView.findViewById<EditText>(etFurRet)
        val etFurOpen = mDialogView.findViewById<EditText>(etFurOpen)
        val  btnUpdateData = mDialogView.findViewById<Button>(btnUpdateData)

        etFurName.setText(intent.getStringExtra("Furnstore").toString())
        etFurBur.setText(intent.getStringExtra("FurBrancher").toString())
        etFurPrice.setText(intent.getStringExtra("FurnPrice").toString())
        etFurRet.setText(intent.getStringExtra("FurnRating").toString())
        etFurOpen.setText(intent.getStringExtra("FurnHours").toString())

        mDialog.setTitle("Updating $Furnstore Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateFurData(
                FurnId,
                etFurName.text.toString(),
                etFurBur.text.toString(),
                etFurPrice.text.toString(),
                etFurRet.text.toString(),
                etFurOpen.text.toString()
            )

            Toast.makeText(applicationContext, "Furniture Data Update", Toast.LENGTH_LONG).show()

            tvFurName.text = etFurName.text.toString()
            tvFurBra.text = etFurBur.text.toString()
            tvFurPrice.text = etFurPrice.text.toString()
            tvFurRet.text = etFurRet.text.toString()
            tvFurOpn.text =  etFurOpen.text.toString()

            alertDialog.dismiss()
        }

//        Toast.makeText(applicationContext, "Furniture Data Update", Toast.LENGTH_LONG).show()
//
//        tvFurName.text = etFurName.text.toString()
//        tvFurBra.text = etFurBur.text.toString()
//        tvFurPrice.text = etFurPrice.text.toString()
//        tvFurRet.text = etFurRet.text.toString()
//        tvFurOpn.text =  etFurOpen.text.toString()
//
//        alertDialog.dismiss()

    }

    private fun updateFurData(
        id : String,
        Name : String,
        Branch : String,
        Price : String,
        Retting : String,
        Open : String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Furniture").child(id)
        val furInfo = FurnitureModel(id,Name,Branch,Price,Retting,Open)
        dbRef.setValue(furInfo)

    }

}

