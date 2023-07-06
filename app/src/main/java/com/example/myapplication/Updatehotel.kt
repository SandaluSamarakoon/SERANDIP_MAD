package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.models.HotModel
import com.google.firebase.database.FirebaseDatabase

class Updatehotel : AppCompatActivity() {

    private lateinit var tvHotId : TextView
    private lateinit var tvHotName : TextView
    private lateinit var tvHotDes :TextView
    private lateinit var tvHotPhone : TextView
    private lateinit var tvHotRet : TextView
    private lateinit var btnUpdate : Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_updatehotel)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(

                       intent.getStringExtra("hodId").toString(),
                       intent.getStringExtra("hotName").toString(),


            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("hodId").toString()
            )
        }
    }

    private fun  deleteRecord(
        id: String
    ){
        val dbRer = FirebaseDatabase.getInstance().getReference("Hotels").child(id)
        val mTask = dbRer.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Employee data deleted",Toast.LENGTH_LONG).show()
            val intent = Intent(this, AdminView::class.java )
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this,"Deleting Err ${error.message}",Toast.LENGTH_LONG).show()
        }
    }

    private fun initView(){

        tvHotId = findViewById(R.id.tvHotId)
        tvHotName = findViewById(R.id.tvHotName)
        tvHotDes = findViewById(R.id.tvHotDes)
        tvHotPhone = findViewById(R.id.tvHotPhone)
        tvHotRet = findViewById(R.id.tvHotRet)

        btnDelete= findViewById(R.id.btnDelete)
        btnUpdate= findViewById(R.id.btnUpdate)

    }

    private fun setValuesToViews(){

        tvHotId.text = intent.getStringExtra("hodId")
        tvHotName.text = intent.getStringExtra("hotName")
        tvHotDes.text = intent.getStringExtra("hotDistrict")
        tvHotPhone.text = intent.getStringExtra("hotPhone")
        tvHotRet.text = intent.getStringExtra("hotRetting")
    }

    private fun openUpdateDialog(
       hodId : String,
       hotName: String
    ){
       val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog_hotel,null)

        mDialog.setView(mDialogView)

        val etHotName = mDialogView.findViewById<EditText>(R.id.etHotName)
        val etHotDes = mDialogView.findViewById<EditText>(R.id.etHotDes)
        val etHotPhone = mDialogView.findViewById<EditText>(R.id.etHotPhone)
        val etHotRet = mDialogView.findViewById<EditText>(R.id.etHotRet)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etHotName.setText(intent.getStringExtra("hotName").toString())
        etHotDes.setText(intent.getStringExtra("hotDistrict").toString())
        etHotPhone.setText(intent.getStringExtra("hotPhone").toString())
        etHotRet.setText(intent.getStringExtra("hotRetting").toString())

        mDialog.setTitle("Updating $hotName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

       btnUpdateData.setOnClickListener{
           updateHotData(

               hodId,
               etHotName.text.toString(),
               etHotDes.text.toString(),
               etHotPhone.text.toString(),
               etHotRet.text.toString()

           )

           Toast.makeText(applicationContext,"Hotel Data Update",Toast.LENGTH_LONG).show()

           // we are setting update Data
           tvHotName.text = etHotName.text.toString()
           tvHotDes.text = etHotDes.text.toString()
           tvHotPhone.text = etHotPhone.text.toString()
           tvHotRet.text =  etHotRet.text.toString()

           alertDialog.dismiss()


       }

//        Toast.makeText(applicationContext,"Hotel Data Update",Toast.LENGTH_LONG).show()
//
//        // we are setting update Data
//        tvHotName.text = etHotName.text.toString()
//        tvHotDes.text = etHotDes.text.toString()
//        tvHotPhone.text = etHotPhone.text.toString()
//        tvHotRet.text =  etHotRet.text.toString()

        //alertDialog.dismiss()

    }

    private fun updateHotData(
        id:String,
        name : String,
        Descrption : String,
        Phone : String,
        Retting : String
    ){

        val dbRef = FirebaseDatabase.getInstance().getReference("Hotels").child(id)
        val hotInfo = HotModel(id,name,Descrption,Phone,Retting)
        dbRef.setValue(hotInfo)

    }
}