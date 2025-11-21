package com.edu.uptc.ejerciciosdidacticos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.uptc.ejerciciosdidacticos.MedicalRecord.data.MedicalRecordDataBaseHelper
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityShowMedicalRecordBinding

class ShowMedicalRecordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityShowMedicalRecordBinding
    private lateinit var db: MedicalRecordDataBaseHelper
    private lateinit var adapter: MedicalRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.binding = ActivityShowMedicalRecordBinding.inflate(layoutInflater)
        this.db = MedicalRecordDataBaseHelper(this)
        this.adapter = MedicalRecordAdapter(this.db.getMedicalRecords(), this)
        setContentView(this.binding.root)

        binding.idRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.idRecyclerView.adapter = this.adapter

        binding.idButtonBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.refreshData(db.getMedicalRecords())
    }
}