package com.edu.uptc.ejerciciosdidacticos.patient.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edu.uptc.ejerciciosdidacticos.HomeActivity
import com.edu.uptc.ejerciciosdidacticos.R
import com.edu.uptc.ejerciciosdidacticos.ShowPatientActivity
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityMainBinding
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityPatientBinding
import com.edu.uptc.ejerciciosdidacticos.patient.data.PatientDTO
import com.edu.uptc.ejerciciosdidacticos.patient.data.PatientDataBaseHelper

class PatientActivity : AppCompatActivity() {
    private lateinit var binding:  ActivityPatientBinding
    private lateinit var db: PatientDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.binding = ActivityPatientBinding.inflate(layoutInflater)
        this.db = PatientDataBaseHelper(this)
        setContentView(binding.root)

        val operationType = intent.getStringExtra("action_type")
        if (operationType.equals("UPDATE")){
            val patietnId = intent.getIntExtra("patient_id", -1)
            if (patietnId == -1){
                finish()
            }

            val patient = this.db.getPatientById(patietnId)

            this.binding.namePatient.setText(patient?.name)
            this.binding.idPatient.setText(patient?.id.toString())
            this.binding.sexPatient.setText(patient?.sex)
            this.binding.addressPatient.setText(patient?.address)
            this.binding.phonePatient.setText(patient?.phone)
            this.binding.emailPatient.setText(patient?.emailAddress)
        }

        this.binding.buttonBack.setOnClickListener {
            if (operationType.equals("CREATE")){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, ShowPatientActivity::class.java)
                startActivity(intent)
            }
        }

        this.binding.buttonPatient.setOnClickListener {
            if (operationType.equals("CREATE")){
                val patientName = this.binding.namePatient.text.toString()
                val idPatient = this.binding.idPatient.text.toString().toInt()
                val sexPatient = this.binding.sexPatient.text.toString()
                val addressPatient = this.binding.addressPatient.text.toString()
                val phonePatient = this.binding.phonePatient.text.toString()
                val emailPatient = this.binding.emailPatient.text.toString()
                val patientInput = PatientDTO(patientName, idPatient, sexPatient, addressPatient, phonePatient, emailPatient)
                Toast.makeText(this, "Validando", Toast.LENGTH_SHORT).show()
                this.db.insertPatient(patientInput)
                if (this.db.validatePatient(patientInput)){
                    Toast.makeText(this, "Se agrego correctamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, PatientActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "No se pudo agregar", Toast.LENGTH_SHORT).show()
                }
                this.binding.namePatient.setText("")
                this.binding.idPatient.setText("")
                this.binding.sexPatient.setText("")
                this.binding.addressPatient.setText("")
                this.binding.phonePatient.setText("")
                this.binding.emailPatient.setText("")
            }else{
                val patientName = this.binding.namePatient.text.toString()
                val idPatient = this.binding.idPatient.text.toString().toInt()
                val sexPatient = this.binding.sexPatient.text.toString()
                val addressPatient = this.binding.addressPatient.text.toString()
                val phonePatient = this.binding.phonePatient.text.toString()
                val emailPatient = this.binding.emailPatient.text.toString()
                val patientDTO = PatientDTO(patientName, idPatient, sexPatient, addressPatient, phonePatient, emailPatient)
                this.db.updatePatient(patientDTO)
                Toast.makeText(this, "Se actualizo correctamente el paciente",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ShowPatientActivity::class.java)
                startActivity(intent)
            }


        }
    }
}