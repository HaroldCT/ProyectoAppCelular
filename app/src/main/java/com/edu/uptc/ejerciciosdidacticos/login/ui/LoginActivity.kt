package com.edu.uptc.ejerciciosdidacticos.login.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edu.uptc.ejerciciosdidacticos.HomeActivity
import com.edu.uptc.ejerciciosdidacticos.R
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityLoginBinding
import com.edu.uptc.ejerciciosdidacticos.login.data.UserDTO
import com.edu.uptc.ejerciciosdidacticos.login.data.UserDatabaseHelper

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(layoutInflater)
        this.db = UserDatabaseHelper(this)
        setContentView(binding.root)

        this.binding.buttonLogin.setOnClickListener {
            /* Manejo el evento del click en el botón */
            val username = this.binding.inputUserName.text.toString()
            val password = this.binding.inputPassword.text.toString()
            val userInput = UserDTO(0, username, password)
            Toast.makeText(this, "ANTES", Toast.LENGTH_SHORT).show()
            if(this.db.validateUser(userInput)){
                Toast.makeText(this, "¡El usuario existe! Continúa con la pantalla de main", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "¡El usuario ingresado no existe",
                    Toast.LENGTH_SHORT).show()
            }
        }

    }
}