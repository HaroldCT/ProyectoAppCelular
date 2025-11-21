package com.edu.uptc.ejerciciosdidacticos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityShowPatientBinding
import com.edu.uptc.ejerciciosdidacticos.patient.data.PatientDataBaseHelper

class ShowPatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowPatientBinding
    private lateinit var db: PatientDataBaseHelper
    private lateinit var adapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.binding = ActivityShowPatientBinding.inflate(layoutInflater)
        this.db = PatientDataBaseHelper(this)
        this.adapter = PatientAdapter(this.db.getPatients(), this)
        setContentView(binding.root)

        binding.idRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.idRecyclerView.adapter = this.adapter

        binding.idButtonBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.refreshData(db.getPatients())
    }
}/*
package com.edu.uptc.ejerciciosdidacticos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityShowPatientBinding
import com.edu.uptc.ejerciciosdidacticos.patient.data.PatientDataBaseHelper

class ShowPatientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowPatientBinding
    private lateinit var db: PatientDataBaseHelper
    private lateinit var adapter: PatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Log.d("PATIENT_DEBUG", "=== ShowPatientActivity onCreate ===")

        this.binding = ActivityShowPatientBinding.inflate(layoutInflater)
        this.db = PatientDataBaseHelper(this)

        // DEBUG: Verificar pacientes
        val patients = this.db.getPatients()
        Log.d("PATIENT_DEBUG", "Total de pacientes encontrados: ${patients.size}")

        if (patients.isEmpty()) {
            Log.e("PATIENT_DEBUG", "¡ERROR! No hay pacientes en la base de datos")
            Toast.makeText(this, "No hay pacientes registrados", Toast.LENGTH_LONG).show()
        } else {
            patients.forEach { p ->
                Log.d("PATIENT_DEBUG", "Paciente: ${p.name}, ID: ${p.id}, Email: ${p.emailAddress}")
            }
            Toast.makeText(this, "Pacientes encontrados: ${patients.size}", Toast.LENGTH_LONG).show()
        }

        this.adapter = PatientAdapter(patients, this)
        setContentView(binding.root)

        binding.idRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.idRecyclerView.adapter = this.adapter

        Log.d("PATIENT_DEBUG", "RecyclerView configurado con adapter")

        binding.idButtonBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("PATIENT_DEBUG", "=== onResume llamado ===")
        adapter.refreshData(db.getPatients())
    }
}*/