package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.FurAdapter
import com.example.myapplication.adapters.TeaAdapter
import com.example.myapplication.models.PlaceModel
import com.example.myapplication.models.TeaModel
import com.google.firebase.database.*

class AdminteaView : AppCompatActivity() {

    private lateinit var teaRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var teaList: ArrayList<TeaModel>
    private lateinit var dbRef: DatabaseReference



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admintea_view)



        var  imageaddteas = findViewById<ImageView>(R.id.imageaddteas )
        imageaddteas.setOnClickListener{
            val intent = Intent(this,AddTea::class.java)
            startActivity(intent)
        }


        teaRecyclerView = findViewById(R.id.rvBrand)
        teaRecyclerView.layoutManager = LinearLayoutManager(this)
        teaRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)


        teaList = arrayListOf<TeaModel>()

        getTeaData()

    }

    private fun getTeaData(){
        teaRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

         dbRef = FirebaseDatabase.getInstance().getReference("Tea")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                teaList.clear()
                if(snapshot.exists()){
                    for(teaSnap in snapshot.children){
                        val teaData = teaSnap.getValue(TeaModel::class.java)
                        teaList.add(teaData!!)
                    }
                    val  mAdapter = TeaAdapter(teaList)
                    teaRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : TeaAdapter.onItemClickListener{
                        @SuppressLint("SuspiciousIndentation")
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@AdminteaView,Updatetea::class.java)

                            intent.putExtra("teaId", teaList[position].teaId)
                            intent.putExtra("teaBrand", teaList[position].teaBrand)
                            intent.putExtra("teaType", teaList[position].teaType)
                            intent.putExtra("teaPrice", teaList[position].teaPrice)
                            intent.putExtra("teaRetting", teaList[position].teaRetting)

                            startActivity(intent)


                        }
                    })

                    teaRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}