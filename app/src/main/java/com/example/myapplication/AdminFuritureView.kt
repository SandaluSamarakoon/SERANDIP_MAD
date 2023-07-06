package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.FurAdapter
import com.example.myapplication.models.FurnitureModel
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class AdminFuritureView : AppCompatActivity() {

    private lateinit var furRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var furList: ArrayList<FurnitureModel>
    private lateinit var dbRef : DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_furiture_view)



        var  imageaddfurniture = findViewById<ImageView>(R.id. imageaddfurniture)
        imageaddfurniture.setOnClickListener{
            val intent = Intent(this,AddFurniture::class.java)
            startActivity(intent)
        }

        furRecyclerView =findViewById(R.id.rvFur)
        furRecyclerView.layoutManager = LinearLayoutManager(this)
        furRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        furList = arrayListOf<FurnitureModel>()

        getFurnitureData()

    }

    private fun getFurnitureData(){
        furRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Furniture")


        dbRef.addValueEventListener(object  : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                furList.clear()
                if(snapshot.exists()){
                    for(furSnap in snapshot.children){
                        val furData = furSnap.getValue(FurnitureModel::class.java)
                        furList.add(furData!!)
                    }

                    val mAdapter = FurAdapter(furList)
                    furRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object :FurAdapter.onItemClickListener{
                        @SuppressLint("SuspiciousIndentation")
                        override fun onItemClick(position: Int) {
                          val intent = Intent(this@AdminFuritureView,UpdateFurniture::class.java)

                            //put extras
                            intent.putExtra("FurnId", furList[position].FurnId)
                            intent.putExtra("Furnstore", furList[position].Furnstore)
                            intent.putExtra("FurBrancher", furList[position].FurBrancher)
                            intent.putExtra("FurnPrice", furList[position].FurnPrice)
                            intent.putExtra("FurnRating", furList[position].FurnRating)
                            intent.putExtra("FurnHours", furList[position].FurnHours)
                            startActivity(intent)


                        }
                    })

                    furRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View. GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}