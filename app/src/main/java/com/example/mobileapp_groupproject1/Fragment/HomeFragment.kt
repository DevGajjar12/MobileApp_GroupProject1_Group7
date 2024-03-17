package com.example.mobileapp_groupproject1.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp_groupproject1.DetailsActivity
import com.example.mobileapp_groupproject1.R
import com.example.mobileapp_groupproject1.Recruit
import com.example.mobileapp_groupproject1.RecruitAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() , RecruitAdapter.OnItemClickListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var recruitsList: MutableList<Recruit>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onItemClick(recruit: Recruit) {
        // Handle item click, navigate to detail activity
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra("recruit", recruit)
        startActivity(intent)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        recruitsList = mutableListOf()
        val adapter = RecruitAdapter(requireContext(), recruitsList, this)
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

}