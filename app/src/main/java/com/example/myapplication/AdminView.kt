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
import com.example.myapplication.adapters.HotAdapter
import com.example.myapplication.models.HotModel
import com.example.myapplication.models.PlaceModel
import com.google.firebase.database.*

class AdminView : AppCompatActivity() {

    private lateinit var rvhot : RecyclerView
    private lateinit var tvLoadingData :TextView
    private lateinit var hotList: ArrayList<HotModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_view)


        var imageaddhotle = findViewById<ImageView>(R.id.imageaddhotle)
        imageaddhotle.setOnClickListener{
            val intent = Intent(this,Addhotel::class.java)
            startActivity(intent)
        }

        rvhot = findViewById(R.id.rvhot)
        rvhot.layoutManager = LinearLayoutManager(this)
        rvhot.setHasFixedSize(true)
        tvLoadingData= findViewById(R.id.tvLoadingData)

        hotList = arrayListOf<HotModel>()

        getHotelsData()
    }

    private fun getHotelsData(){

        rvhot.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

         dbRef = FirebaseDatabase.getInstance().getReference("Hotels")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                hotList.clear()
                if(snapshot.exists()){
                    for(hotSnap in snapshot.children){
                        val hotData = hotSnap.getValue(HotModel::class.java)
                        hotList.add(hotData!!)
                    }

                    val mAdapter = HotAdapter(hotList)
                    rvhot.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : HotAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@AdminView,Updatehotel::class.java)

                            intent.putExtra("hodId",hotList[position].hodId)
                            intent.putExtra("hotName",hotList[position].hotName)
                            intent.putExtra("hotDistrict",hotList[position].hotDistrict)
                            intent.putExtra("hotPhone",hotList[position].hotPhone)
                            intent.putExtra("hotRetting",hotList[position].hotRetting)

                            startActivity(intent)
                        }

                    })

                    rvhot.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}