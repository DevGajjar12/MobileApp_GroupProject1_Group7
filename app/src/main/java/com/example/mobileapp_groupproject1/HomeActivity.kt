package com.example.mobileapp_groupproject1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity(), RecruitAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var recruitsList: MutableList<Recruit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recruitsList = mutableListOf()
        val adapter = RecruitAdapter(this, recruitsList, this)
        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance().getReference("recruits")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    recruitsList.clear()
                    for (recruitSnapshot in snapshot.children) {
                        val recruit = recruitSnapshot.getValue(Recruit::class.java)
                        recruit?.let { recruitsList.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    override fun onItemClick(recruit: Recruit) {
        // Handle item click, navigate to detail activity
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("recruit", recruit)
        startActivity(intent)
    }


}
