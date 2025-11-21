package com.edu.uptc.ejerciciosdidacticos.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edu.uptc.ejerciciosdidacticos.FormUserActivity
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityIntroBinding
import com.edu.uptc.ejerciciosdidacticos.login.data.UserDatabaseHelper
import com.edu.uptc.ejerciciosdidacticos.login.ui.LoginActivity
import com.edu.uptc.ejerciciosdidacticos.RegisterActivity

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private lateinit var db: UserDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityIntroBinding.inflate(layoutInflater)
        this.db = UserDatabaseHelper(this)
        setContentView(binding.root)

        this.binding.idButtonLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }
        this.binding.idButtonRegister.setOnClickListener {
            val intent = Intent(this, FormUserActivity::class.java).apply {
                putExtra("action_type", "CREATE")
            }
            startActivity(intent)
        }
    }
}