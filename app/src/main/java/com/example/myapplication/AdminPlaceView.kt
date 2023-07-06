package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.PlsAdapter
import com.example.myapplication.models.PlaceModel
import com.google.firebase.database.*

class AdminPlaceView : AppCompatActivity() {

    private lateinit var plsRecyclerView: RecyclerView
    private lateinit var tvLoadingData:TextView
    private lateinit var plsList: ArrayList<PlaceModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_place_view)

        // intent
        var imageaddplaces= findViewById<ImageView>(R.id.imageaddplaces)
        imageaddplaces.setOnClickListener {
            val intent = Intent(this, Addplace::class.java)
            startActivity(intent)
        }


            plsRecyclerView = findViewById(R.id.rvpls)
            plsRecyclerView.layoutManager = LinearLayoutManager(this)
            plsRecyclerView.setHasFixedSize(true)
            tvLoadingData = findViewById(R.id.tvLoadingData)

        plsList = arrayListOf<PlaceModel>()

        getPlaceData()
    }

    private fun getPlaceData() {


        plsRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Places")

        dbRef.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                plsList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(PlaceModel::class.java)
                        plsList.add(empData!!)
                    }
                    val mAdapter = PlsAdapter(plsList)
                    plsRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : PlsAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@AdminPlaceView,Updateplace::class.java)

                            //put extras
                            intent.putExtra("placeId",plsList[position].placeId)
                            intent.putExtra("plcename",plsList[position].plcename)
                            intent.putExtra("placenumber",plsList[position].placenumber)

                            startActivity(intent)
                        }

                    })

                    plsRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }




}
