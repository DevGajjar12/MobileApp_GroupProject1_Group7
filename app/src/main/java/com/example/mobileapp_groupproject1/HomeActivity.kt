package com.example.mobileapp_groupproject1
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity(), RecruitAdapter.OnItemClickListener {

    //view created by Krushang
    private lateinit var li_home : LinearLayout
    private lateinit var li_about : LinearLayout
    private lateinit var li_event : LinearLayout

    private lateinit var iv_home : ImageView
    private lateinit var iv_about : ImageView
    private lateinit var iv_event : ImageView

    private lateinit var tv_home : TextView
    private lateinit var tv_about : TextView
    private lateinit var tv_event : TextView


    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var recruitsList: MutableList<Recruit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Declaration()

        HandleClick()

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

    private fun HandleClick() {
        li_home.setOnClickListener {
            startActivity(Intent(applicationContext,HomeActivity))
        }
        li_about.setOnClickListener {

        }
        li_event.setOnClickListener {

        }
        iv_home.setOnClickListener {

        }
        iv_about.setOnClickListener {

        }
        iv_event.setOnClickListener {

        }
        tv_home.setOnClickListener {

        }
        tv_about.setOnClickListener {

        }
        tv_event.setOnClickListener {

        }
    }

    //declare All variable created by Krushang
    private fun Declaration() {
        li_home = findViewById(R.id.li_home)
        li_about = findViewById(R.id.li_about)
        li_event = findViewById(R.id.li_event)
        iv_home = findViewById(R.id.iv_home)
        iv_about = findViewById(R.id.iv_about)
        iv_event = findViewById(R.id.iv_event)
        tv_home = findViewById(R.id.tv_home)
        tv_about = findViewById(R.id.tv_about)
        tv_event = findViewById(R.id.tv_event)
    }

    override fun onItemClick(recruit: Recruit) {
        // Handle item click, navigate to detail activity
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra("recruit", recruit)
        startActivity(intent)
    }


}
