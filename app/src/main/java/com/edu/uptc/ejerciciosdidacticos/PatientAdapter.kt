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
import com.edu.uptc.ejerciciosdidacticos.login.data.UserDTO
import com.edu.uptc.ejerciciosdidacticos.patient.data.PatientDTO
import com.edu.uptc.ejerciciosdidacticos.patient.data.PatientDataBaseHelper
import com.edu.uptc.ejerciciosdidacticos.patient.ui.PatientActivity

class PatientAdapter(private var patients: List<PatientDTO>, private var context: Context) : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {
    private val db: PatientDataBaseHelper = PatientDataBaseHelper(context)
    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namePatient = itemView.findViewById<TextView>(R.id.namePatient)
        val idPatient = itemView.findViewById<TextView>(R.id.idPatient)
        val sexPatient = itemView.findViewById<TextView>(R.id.sexPatient)
        val addressPatient = itemView.findViewById<TextView>(R.id.addressPatient)
        val phonePatient = itemView.findViewById<TextView>(R.id.phonePatient)
        val emailPatient = itemView.findViewById<TextView>(R.id.emailPatient)
        val updateButton = itemView.findViewById<TextView>(R.id.buttonUpdate)
        val deleteButton = itemView.findViewById<TextView>(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PatientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_patient, parent, false)
        return PatientViewHolder(view)
    }
    override fun onBindViewHolder(
        holder: PatientViewHolder,
        position: Int
    ) {
        val patient =  this.patients[position]
        holder.namePatient.text = patient.name
        holder.idPatient.text = patient.id.toString()
        holder.sexPatient.text = patient.sex
        holder.addressPatient.text = patient.address
        holder.phonePatient.text = patient.phone
        holder.emailPatient.text = patient.emailAddress
        holder.updateButton.setOnClickListener {
        }
        holder.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("Confirmar accion")
            builder.setMessage("Estas seguro de que quieres eliminar este registro?")
            builder.setPositiveButton("Si") { dialog, _ ->
                db.deletePatientById(patient.id)
                this.refreshData(db.getPatients())
                Toast.makeText(holder.itemView.context, "Usuario eliminado", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, PatientActivity::class.java ).apply {
                putExtra("action_type", "UPDATE")
                putExtra("patient_id", patient.id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return patients.size
    }
    fun refreshData(newPatients: List<PatientDTO>){
        this.patients = newPatients
        notifyDataSetChanged()
    }
}/*
package com.edu.uptc.ejerciciosdidacticos

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.edu.uptc.ejerciciosdidacticos.patient.data.PatientDTO
import com.edu.uptc.ejerciciosdidacticos.patient.data.PatientDataBaseHelper
import com.edu.uptc.ejerciciosdidacticos.patient.ui.PatientActivity

class PatientAdapter(private var patients: List<PatientDTO>, private var context: Context) : RecyclerView.Adapter<PatientAdapter.PatientViewHolder>() {

    private val db: PatientDataBaseHelper = PatientDataBaseHelper(context)

    init {
        Log.d("ADAPTER_DEBUG", "PatientAdapter creado con ${patients.size} pacientes")
    }

    class PatientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namePatient = itemView.findViewById<TextView>(R.id.namePatient)
        val idPatient = itemView.findViewById<TextView>(R.id.idPatient)
        val sexPatient = itemView.findViewById<TextView>(R.id.sexPatient)
        val addressPatient = itemView.findViewById<TextView>(R.id.addressPatient)
        val phonePatient = itemView.findViewById<TextView>(R.id.phonePatient)
        val emailPatient = itemView.findViewById<TextView>(R.id.emailPatient)
        val updateButton = itemView.findViewById<TextView>(R.id.buttonUpdate)
        val deleteButton = itemView.findViewById<TextView>(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatientViewHolder {
        Log.d("ADAPTER_DEBUG", "onCreateViewHolder llamado")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_patient, parent, false)
        return PatientViewHolder(view)
    }

    override fun onBindViewHolder(holder: PatientViewHolder, position: Int) {
        Log.d("ADAPTER_DEBUG", "onBindViewHolder llamado para posición $position")
        val patient = this.patients[position]
        Log.d("ADAPTER_DEBUG", "Binding paciente: ${patient.name}")

        holder.namePatient.text = patient.name
        holder.idPatient.text = patient.id.toString()
        holder.sexPatient.text = patient.sex
        holder.addressPatient.text = patient.address
        holder.phonePatient.text = patient.phone
        holder.emailPatient.text = patient.emailAddress

        holder.deleteButton.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            builder.setTitle("Confirmar accion")
            builder.setMessage("Estas seguro de que quieres eliminar este registro?")
            builder.setPositiveButton("Si") { dialog, _ ->
                db.deletePatientById(patient.id)
                this.refreshData(db.getPatients())
                Toast.makeText(holder.itemView.context, "Paciente eliminado", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        }

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, PatientActivity::class.java).apply {
                putExtra("action_type", "UPDATE")
                putExtra("patient_id", patient.id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        Log.d("ADAPTER_DEBUG", "getItemCount retorna: ${patients.size}")
        return patients.size
    }

    fun refreshData(newPatients: List<PatientDTO>) {
        Log.d("ADAPTER_DEBUG", "refreshData llamado con ${newPatients.size} pacientes")
        this.patients = newPatients
        notifyDataSetChanged()
    }
}*/