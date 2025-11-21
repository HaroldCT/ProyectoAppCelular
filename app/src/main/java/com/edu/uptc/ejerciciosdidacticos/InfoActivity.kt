package com.edu.uptc.ejerciciosdidacticos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityInfoBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(this.binding.root)

        this.binding.btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}