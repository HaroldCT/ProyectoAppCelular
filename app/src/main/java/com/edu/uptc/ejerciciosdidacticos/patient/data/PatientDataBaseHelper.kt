package com.edu.uptc.ejerciciosdidacticos.patient.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PatientDataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "patient.db"
        private const val DATABASE_VERSION = 3
        private const val TABLE_NAME = "patient"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_ID = "id"
        private const val COLUMN_SEX = "sex"
        private const val COLUMN_ADDRESS = "address"
        private const val COLUMN_PHONE = "phone"
        private const val COLUMN_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val queryCreate = "CREATE TABLE $TABLE_NAME($COLUMN_NAME TEXT, $COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_SEX TEXT, $COLUMN_ADDRESS TEXT, $COLUMN_PHONE TEXT, $COLUMN_EMAIL TEXT)"
        db?.execSQL(queryCreate)

        val queryInsert = "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_ID, $COLUMN_SEX, $COLUMN_ADDRESS, $COLUMN_PHONE, $COLUMN_EMAIL) VALUES('harold', '1049', 'M', 'Carrera 13', '3123345465', 'harold@gmail.com')"
        db?.execSQL(queryInsert)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sqlDrop = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(sqlDrop)
        this.onCreate(db)
    }

    fun validatePatient(patient: PatientDTO): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME = ? AND $COLUMN_ID = ? AND $COLUMN_SEX = ? AND $COLUMN_ADDRESS = ? AND $COLUMN_PHONE = ? AND $COLUMN_EMAIL = ?"
        val cursor = db.rawQuery(query, arrayOf(patient.name, patient.id.toString(), patient.sex, patient.address, patient.phone, patient.emailAddress))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun patientExists(patientId: Int): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(patientId.toString()))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun insertPatient(patient: PatientDTO) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, patient.name)
            put(COLUMN_ID, patient.id)
            put(COLUMN_SEX, patient.sex)
            put(COLUMN_ADDRESS, patient.address)
            put(COLUMN_PHONE, patient.phone)
            put(COLUMN_EMAIL, patient.emailAddress)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updatePatient(patient: PatientDTO) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, patient.name)
            put(COLUMN_ID, patient.id)
            put(COLUMN_SEX, patient.sex)
            put(COLUMN_ADDRESS, patient.address)
            put(COLUMN_PHONE, patient.phone)
            put(COLUMN_EMAIL, patient.emailAddress)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(patient.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun getPatients(): List<PatientDTO> {
        val patientList = mutableListOf<PatientDTO>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val sex = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEX))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val patient = PatientDTO(name, id, sex, address, phone, email)
            patientList.add(patient)
        }
        cursor.close()
        db.close()
        return patientList
    }

    fun deletePatientById(patientId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(patientId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun getPatientById(patientId: Int): PatientDTO? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(patientId.toString()))
        var patient: PatientDTO? = null

        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val sex = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEX))
            val address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS))
            val phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            patient = PatientDTO(name, id, sex, address, phone, email)
        }

        cursor.close()
        db.close()
        return patient
    }
}