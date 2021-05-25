package com.example.dog_inder.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dog_inder.R
import com.example.dog_inder.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()

        //if (savedInstanceState == null) {
        //    supportFragmentManager.beginTransaction()
        //        .replace(R.id.activity_main_container, homeFragment)
        //        .commitAllowingStateLoss()
        //}
    }
}