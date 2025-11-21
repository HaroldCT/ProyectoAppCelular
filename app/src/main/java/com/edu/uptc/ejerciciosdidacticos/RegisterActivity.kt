package com.edu.uptc.ejerciciosdidacticos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityRegisterBinding
import com.edu.uptc.ejerciciosdidacticos.intro.IntroActivity
import com.edu.uptc.ejerciciosdidacticos.login.data.UserDTO
import com.edu.uptc.ejerciciosdidacticos.login.data.UserDatabaseHelper

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: UserDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityRegisterBinding.inflate(layoutInflater)
        this.db = UserDatabaseHelper(this)
        setContentView(binding.root)

        this.binding.buttonRegister.setOnClickListener {
            val username = this.binding.inputUserName.text.toString()
            val password = this.binding.inputPassword.text.toString()
            val userInput = UserDTO(0, username, password)
            this.db.insertUser(userInput)
            Toast.makeText(this, "Validando datos", Toast.LENGTH_SHORT).show()
            if (this.db.validateUser(userInput) == false){
                this.db.insertUser(userInput)
                Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                this.binding.inputUserName.text.clear()
                this.binding.inputPassword.text.clear()

            }else{
                Toast.makeText(this, "Usuario ya existe", Toast.LENGTH_SHORT).show()
            }

        }
        this.binding.btnBack.setOnClickListener {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }

    }
}