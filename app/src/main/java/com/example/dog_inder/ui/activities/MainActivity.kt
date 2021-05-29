package com.example.dog_inder.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.dog_inder.R
import com.example.dog_inder.databinding.MainActivityBinding
import com.example.dog_inder.utils.activityViewBinding

class MainActivity : AppCompatActivity() {

    private val binding by activityViewBinding(MainActivityBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.button.setOnClickListener {
            val act = Intent(this@MainActivity, DashboardActivity::class.java)
            startActivity(act)
        }
    }
}