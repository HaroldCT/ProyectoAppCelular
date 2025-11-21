package com.edu.uptc.ejerciciosdidacticos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edu.uptc.ejerciciosdidacticos.MedicalRecord.ui.MedicalRecordActivity
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityHomeBinding
import com.edu.uptc.ejerciciosdidacticos.patient.ui.PatientActivity
import com.edu.uptc.ejerciciosdidacticos.ShowUserActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(this.binding.root)
        this.binding.idCardShowUsers.setOnClickListener {
            val intent = Intent(this, ShowUserActivity::class.java)
            startActivity(intent)
        }

        this.binding.idCardCreateUsers.setOnClickListener {
            val intent = Intent(this, FormUserActivity::class.java).apply {
                putExtra("action_type", "CREATE")
            }
            startActivity(intent)
        }

        this.binding.idCardCreateObject1.setOnClickListener {
            val intent = Intent(this, PatientActivity::class.java).apply {
                putExtra("action_type", "CREATE")
            }
            startActivity(intent)
        }

        this.binding.idCardViewObject1.setOnClickListener {
            val intent = Intent(this, ShowPatientActivity::class.java)
            startActivity(intent)
        }

        this.binding.idCardCreateObject2.setOnClickListener {
            val intent = Intent(this, MedicalRecordActivity::class.java).apply {
                putExtra("action_type", "CREATE")
            }
            startActivity(intent)
        }

        this.binding.idCardViewObject2.setOnClickListener {
            val intent = Intent(this, ShowMedicalRecordActivity::class.java)
            startActivity(intent)
        }

        this.binding.idCardLogOut.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
        }

        this.binding.idCardSalir.setOnClickListener {
            finishAffinity()
            System.exit(0)
        }

    }
}