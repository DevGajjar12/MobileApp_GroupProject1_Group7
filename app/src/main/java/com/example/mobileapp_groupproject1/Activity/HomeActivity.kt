package com.example.mobileapp_groupproject1.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.mobileapp_groupproject1.Fragment.AboutFragment
import com.example.mobileapp_groupproject1.Fragment.EventFragment
import com.example.mobileapp_groupproject1.Fragment.HomeFragment
import com.example.mobileapp_groupproject1.R

class HomeActivity : AppCompatActivity(){

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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Declaration()

        HandleClick()

        replaceFragment(HomeFragment())
        iv_home.setImageResource(R.drawable.ic_home_active)
        iv_about.setImageResource(R.drawable.ic_about)
        iv_event.setImageResource(R.drawable.ic_event)

        tv_home.setTextColor(Color.parseColor("#3F51B5"));
        tv_about.setTextColor(Color.parseColor("#FF000000"));
        tv_event.setTextColor(Color.parseColor("#FF000000"));

    }

    private fun HandleClick() {
        li_home.setOnClickListener {
            replaceFragment(HomeFragment())
            iv_home.setImageResource(R.drawable.ic_home_active)
            iv_about.setImageResource(R.drawable.ic_about)
            iv_event.setImageResource(R.drawable.ic_event)

            tv_home.setTextColor(Color.parseColor("#3F51B5"));
            tv_about.setTextColor(Color.parseColor("#FF000000"));
            tv_event.setTextColor(Color.parseColor("#FF000000"));
        }
        li_about.setOnClickListener {
            replaceFragment(AboutFragment())
            iv_home.setImageResource(R.drawable.ic_home)
            iv_about.setImageResource(R.drawable.ic_about_active)
            iv_event.setImageResource(R.drawable.ic_event)

            tv_home.setTextColor(Color.parseColor("#FF000000"));
            tv_about.setTextColor(Color.parseColor("#3F51B5"));
            tv_event.setTextColor(Color.parseColor("#FF000000"));

        }
        li_event.setOnClickListener {
            replaceFragment(EventFragment())
            iv_home.setImageResource(R.drawable.ic_home)
            iv_about.setImageResource(R.drawable.ic_about)
            iv_event.setImageResource(R.drawable.ic_event_active)

            tv_home.setTextColor(Color.parseColor("#FF000000"));
            tv_about.setTextColor(Color.parseColor("#FF000000"));
            tv_event.setTextColor(Color.parseColor("#3F51B5"));

        }
    }

    fun replaceFragment(fragment: Fragment) {
        // Get a reference to the FragmentManager
        val fragmentManager = supportFragmentManager

        // Start a new FragmentTransaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the current fragment with the new fragment
        fragmentTransaction.replace(R.id.frame_layout, fragment)

        // Commit the FragmentTransaction
        fragmentTransaction.commit()
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




}
