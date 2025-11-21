package com.edu.uptc.ejerciciosdidacticos

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.edu.uptc.ejerciciosdidacticos.databinding.ActivityShowUserBinding
import com.edu.uptc.ejerciciosdidacticos.login.data.UserDatabaseHelper

class ShowUserActivity : AppCompatActivity() {

    /* Creación del binding */
    private lateinit var binding: ActivityShowUserBinding
    /* Creación del utilitario de BD */
    private lateinit var bd: UserDatabaseHelper
    /* Creación del adaptador */
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        this.binding = ActivityShowUserBinding.inflate(layoutInflater)
        this.bd = UserDatabaseHelper(this)
        this.adapter = UserAdapter(this.bd.getUsers(), this)
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
        adapter.refreshData(bd.getUsers())
    }
}