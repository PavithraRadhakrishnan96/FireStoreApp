package com.example.firestore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast


class MainActivity : BaseActivity() {

    lateinit var tvData: TextView
    var dataDetails: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvData = findViewById(R.id.tvData)
        dataDetails=intent.getStringExtra("Data")
        if(dataDetails.equals(""))
            tvData.text="No Data Found"
        else tvData.text = dataDetails
    }



}
