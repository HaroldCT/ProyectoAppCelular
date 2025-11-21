package com.edu.uptc.ejerciciosdidacticos

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindig: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.bindig = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
    }
}