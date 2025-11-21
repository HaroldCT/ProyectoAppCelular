package com.edu.uptc.ejerciciosdidacticos.login.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private var DATABASE_NAME = "users.db"
        private var DATABASE_VERSION = 2
        private var TABLE_NAME = "user"
        private var COLUMN_ID = "id"
        private var COLUMN_USERNAME = "username"
        private var COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        /* 1. Crear la tabla */
        val queryCreate = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT)"
        db?.execSQL(queryCreate)

        /* 2. Crear un usuario por defecto */
        val queryInsert = "INSERT INTO $TABLE_NAME ($COLUMN_USERNAME, $COLUMN_PASSWORD) VALUES ('jose', '1234')"
        db?.execSQL(queryInsert)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        /* 1. Elimina la tabla anterior */
        val sqlDrop = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(sqlDrop)
        /* 2. Crea de nuevo la tabla */
        this.onCreate(db)
    }

    /* 1. Validar si un usuario existe en la tabla */
    fun validateUser(user: UserDTO): Boolean{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(user.username, user.password))
        val exists = cursor.count > 0
        db.close()
        cursor.close()
        return exists;
    }

    fun userExists(userId: Int): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun insertUser(user: UserDTO){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_PASSWORD, user.password)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateUser(user: UserDTO){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, user.username)
            put(COLUMN_PASSWORD, user.password)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(user.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getUsers(): List<UserDTO>{
        val usersList = mutableListOf<UserDTO>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            val user: UserDTO = UserDTO(id, username, password)
            usersList.add(user)
        }
        cursor.close()
        db.close()
        return usersList
    }

    fun deleteUserById(nodeId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(nodeId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun getUserById(userId: Int): UserDTO? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(userId.toString()))
        var user: UserDTO? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val username = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            user = UserDTO(id, username, password)
        }
        cursor.close()
        db.close()
        return user
    }


}