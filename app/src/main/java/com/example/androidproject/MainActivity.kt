package com.example.androidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.google.android.gms.dynamic.SupportFragmentWrapper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}