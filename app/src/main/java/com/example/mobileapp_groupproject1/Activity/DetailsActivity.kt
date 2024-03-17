package com.example.mobileapp_groupproject1.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mobileapp_groupproject1.Entity.Recruit
import com.example.mobileapp_groupproject1.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recruit = intent.getSerializableExtra("recruit") as? Recruit

        if (recruit != null) {
            // Display the data in the UI
            binding.textName.text = recruit.name
            binding.textDescription.text = recruit.description
            binding.textSkills.text = recruit.skills

            Glide.with(this)
                .load(recruit.image)
                .into(binding.imageView)
        } else {
            finish()
        }

        binding.btnConnect.setOnClickListener {
            val intent = Intent(this@DetailsActivity, FormActivity::class.java)
            intent.putExtra("recruit", recruit)
            startActivity(intent)
        }
        binding.btnBack?.setOnClickListener {
            val intent = Intent(this@DetailsActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
