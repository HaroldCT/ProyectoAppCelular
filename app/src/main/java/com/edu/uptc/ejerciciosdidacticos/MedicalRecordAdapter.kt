package com.edu.uptc.ejerciciosdidacticos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.edu.uptc.ejerciciosdidacticos.MedicalRecord.data.MedicalRecordDTO
import com.edu.uptc.ejerciciosdidacticos.MedicalRecord.data.MedicalRecordDataBaseHelper
import com.edu.uptc.ejerciciosdidacticos.MedicalRecord.ui.MedicalRecordActivity
import com.edu.uptc.ejerciciosdidacticos.login.data.UserDTO

class MedicalRecordAdapter(private var medicalRecords: List<MedicalRecordDTO>, private var context: Context) : RecyclerView.Adapter<MedicalRecordAdapter.MedicalRecordViewHolder>() {
    private val db: MedicalRecordDataBaseHelper = MedicalRecordDataBaseHelper(context)

    class MedicalRecordViewHolder(intemView: View) : RecyclerView.ViewHolder(intemView) {
        val medicalRecordId = itemView.findViewById<TextView>(R.id.MedicalRecordId)
        val medicalRecordPatientId = itemView.findViewById<TextView>(R.id.idPatient)
        val medicalRecorDate = itemView.findViewById<TextView>(R.id.dateMedicalRecord)
        val medicalRecordSymptoms = itemView.findViewById<TextView>(R.id.symptomsMedicalRecord)
        val medicalRecordDiagnosis = itemView.findViewById<TextView>(R.id.diagnosisMedicalRecord)
        val medicalRecordNotes = itemView.findViewById<TextView>(R.id.notesMedicalRecord)
        val updateButton = itemView.findViewById<TextView>(R.id.buttonUpdateMR)
        val deleteButton = itemView.findViewById<TextView>(R.id.buttonDeleteMR)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MedicalRecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_medical_record, parent, false)
        return MedicalRecordViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MedicalRecordViewHolder,
        position: Int
    ) {
        val medicalRecord = this.medicalRecords[position]
        holder.medicalRecordId.text = medicalRecord.recordId
        holder.medicalRecordPatientId.text = medicalRecord.patientId.toString()
        holder.medicalRecorDate.text = medicalRecord.date
        holder.medicalRecordSymptoms.text = medicalRecord.symptoms
        holder.medicalRecordDiagnosis.text = medicalRecord.diagnosis
        holder.medicalRecordNotes.text = medicalRecord.notes
        holder.updateButton.setOnClickListener {

        }
        holder.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("Confirmar Accion")
            builder.setMessage("Estas seguro de que quieres eliminar el registro?")

            builder.setPositiveButton("Si") { dialog, _ ->
                db.deleteMedicalRecordByRecordId(medicalRecord.recordId)
                this.refreshData(db.getMedicalRecords())
                Toast.makeText(holder.itemView.context, "Registro eliminado", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, MedicalRecordActivity::class.java).apply {
                putExtra("action_type", "UPDATE")
                putExtra("medical_record_id", medicalRecord.recordId)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return medicalRecords.size
    }

    fun refreshData(newMedicalRecords: List<MedicalRecordDTO>){
        this.medicalRecords = newMedicalRecords
        notifyDataSetChanged()
    }
}