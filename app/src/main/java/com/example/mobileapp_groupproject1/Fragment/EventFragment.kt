package com.example.mobileapp_groupproject1.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapp_groupproject1.Activity.DetailsActivity
import com.example.mobileapp_groupproject1.Adapter.EventAdapter
import com.example.mobileapp_groupproject1.R
import com.example.mobileapp_groupproject1.Entity.Recruit
import com.example.mobileapp_groupproject1.Adapter.RecruitAdapter
import com.example.mobileapp_groupproject1.Entity.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EventFragment : Fragment(), EventAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference
    private lateinit var eventsList: MutableList<Event>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }


    override fun onItemClick(event: Event) {
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(activity,3)

        eventsList = mutableListOf()
        val adapter = EventAdapter(requireContext(), eventsList, this)
        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance().getReference("event")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    eventsList.clear()
                    for (eventSnapshot in snapshot.children) {
                        val event = eventSnapshot.getValue(Event::class.java)
                        event?.let { eventsList.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}