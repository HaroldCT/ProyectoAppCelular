package com.edu.uptc.ejerciciosdidacticos.MedicalRecord.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edu.uptc.ejerciciosdidacticos.HomeActivity
import com.edu.uptc.ejerciciosdidacticos.MedicalRecord.data.MedicalRecordDTO
import com.edu.uptc.ejerciciosdidacticos.MedicalRecord.data.MedicalRecordDataBaseHelper
import com.edu.uptc.ejerciciosdidacticos.R
import com.edu.uptc.ejerciciosdidacticos.ShowMedicalRecordActivity
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityMedicalRecordBinding

class MedicalRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedicalRecordBinding
    private lateinit var db : MedicalRecordDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.binding = ActivityMedicalRecordBinding.inflate(layoutInflater)
        this.db = MedicalRecordDataBaseHelper(this)
        setContentView(binding.root)

        val operationType = intent.getStringExtra("action_type")
        if (operationType.equals("UPDATE")){
            val medicalRecordId = intent.getIntExtra("medical_record_id", -1)
            if (medicalRecordId == -1){
                finish()
            }

            val medicalRecord = this.db.getMedicalRecordByRecordId(medicalRecordId.toString())

            this.binding.idMedicalRecordId.setText(medicalRecord?.recordId)
            this.binding.idMedicalRecordIdPatient.setText(medicalRecord?.patientId.toString())
            this.binding.idMedicalRecordDate.setText(medicalRecord?.date)
            this.binding.idMedicalRecordSymptoms.setText(medicalRecord?.symptoms)
            this.binding.idMedicalRecordDiagnosis.setText(medicalRecord?.diagnosis)
            this.binding.idMedicalRecordNotes.setText(medicalRecord?.notes)
        }

        this.binding.btnBack.setOnClickListener {
            if (operationType.equals("CREATE")){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, ShowMedicalRecordActivity::class.java)
                startActivity(intent)

            }
        }

        this.binding.btnAccept.setOnClickListener {
            if (operationType.equals("CREATE")){
                val recordId = this.binding.idMedicalRecordId.text.toString()
                val patientId = this.binding.idMedicalRecordIdPatient.text.toString()
                val date = this.binding.idMedicalRecordDate.text.toString()
                val symptoms = this.binding.idMedicalRecordSymptoms.text.toString()
                val diagnosis = this.binding.idMedicalRecordDiagnosis.text.toString()
                val notes = this.binding.idMedicalRecordNotes.text.toString()
                val medicalRecord = MedicalRecordDTO(recordId, patientId. toInt(), date, symptoms, diagnosis, notes)
                Toast.makeText(this, "Validando datos", Toast.LENGTH_SHORT).show()
                this.db.insertMedicalRecord(medicalRecord)
                if (this.db.validateMedicalRecord(medicalRecord)){
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MedicalRecordActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "No se pudo agregar este historial medico", Toast.LENGTH_SHORT).show()
                }
                this.binding.idMedicalRecordId.setText("")
                this.binding.idMedicalRecordIdPatient.setText("")
                this.binding.idMedicalRecordDate.setText("")
                this.binding.idMedicalRecordSymptoms.setText("")
                this.binding.idMedicalRecordDiagnosis.setText("")
                this.binding.idMedicalRecordNotes.setText("")
            }else{
                val recordId = this.binding.idMedicalRecordId.text.toString()
                val patientId = this.binding.idMedicalRecordIdPatient.text.toString()
                val date = this.binding.idMedicalRecordDate.text.toString()
                val symptoms = this.binding.idMedicalRecordSymptoms.text.toString()
                val diagnosis = this.binding.idMedicalRecordDiagnosis.text.toString()
                val notes = this.binding.idMedicalRecordNotes.text.toString()
                val medicalRecord = MedicalRecordDTO(recordId, patientId. toInt(), date, symptoms, diagnosis, notes)
                if (this.db.updateMedicalRecord(medicalRecord)){
                    Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ShowMedicalRecordActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "No se pudo actualizar este historial medico", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}