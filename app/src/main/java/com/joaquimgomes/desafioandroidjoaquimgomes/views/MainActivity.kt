package com.joaquimgomes.desafioandroidjoaquimgomes.views

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joaquimgomes.desafioandroidjoaquimgomes.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}