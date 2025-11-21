package com.edu.uptc.ejerciciosdidacticos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityFormUserBinding
import com.edu.uptc.ejerciciosdidacticos.login.data.UserDTO
import com.edu.uptc.ejerciciosdidacticos.login.data.UserDatabaseHelper
import com.edu.uptc.ejerciciosdidacticos.ShowUserActivity
import com.edu.uptc.ejerciciosdidacticos.intro.IntroActivity

class FormUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormUserBinding
    private lateinit var bd: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.binding = ActivityFormUserBinding.inflate(layoutInflater)
        this.bd = UserDatabaseHelper(this)
        setContentView(binding.root)

        /* Se obtiene el tipo de operación que se va a ejecutar en la operación */
        val operationType = intent.getStringExtra("action_type")
        if(operationType.equals("UPDATE")){
            /* Se obtiene el identificador de user ID recibido por parámetro */
            val userId = intent.getIntExtra("user_id", -1)
            if(userId == -1){
                finish()
            }

            /* Se consulta la información de usuario */
            val user = bd.getUserById(userId)

            /* Se asigna la información a los componentes visuales */
            this.binding.inputUserName.setText(user?.username)
            this.binding.inputPassword.setText(user?.password)
        }

        this.binding.buttonBack.setOnClickListener {
            if(operationType.equals("CREATE")){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, ShowUserActivity::class.java)
                startActivity(intent)
            }
        }

        this.binding.buttonAccept.setOnClickListener {
            if(operationType.equals("CREATE")){
                val username = this.binding.inputUserName.text.toString()
                val password = this.binding.inputPassword.text.toString()
                val userDTO: UserDTO = UserDTO(0, username, password)

                /* Validar si el usuario existe */
                if(this.bd.validateUser(userDTO)){
                    /* El usuario ya existe con esas credenciales, no se agrega */
                    Toast.makeText(this, "¡El registro ya existe en BD!",
                        Toast.LENGTH_SHORT).show()
                }else{
                    /* Se agrega el registro en BD*/
                    this.bd.insertUser(userDTO)
                    Toast.makeText(this, "¡Registro de usuario agregado!",
                        Toast.LENGTH_SHORT).show()
                }
                /* Se limpian los datos */
                this.binding.inputUserName.setText("")
                this.binding.inputPassword.setText("")

            }else{
                /* Se obtiene el identificador de user ID recibido por parámetro */
                val userId = intent.getIntExtra("user_id", -1)
                val username = this.binding.inputUserName.text.toString()
                val password = this.binding.inputPassword.text.toString()
                val userDTO = UserDTO(userId, username, password)
                this.bd.updateUser(userDTO)
                Toast.makeText(this, "¡Registro de usuario actualizado!",
                    Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ShowUserActivity::class.java)
                startActivity(intent)
            }
        }
    }
}