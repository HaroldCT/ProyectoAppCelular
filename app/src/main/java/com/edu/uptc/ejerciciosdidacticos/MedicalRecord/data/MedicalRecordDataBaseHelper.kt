package com.edu.uptc.ejerciciosdidacticos.MedicalRecord.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.edu.uptc.ejerciciosdidacticos.patient.data.PatientDataBaseHelper

class MedicalRecordDataBaseHelper(private val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "medical_record.db"
        private const val DATABASE_VERSION = 3
        private const val TABLE_NAME = "medical_records"
        private const val COLUMN_RECORD_ID = "record_id"
        private const val COLUMN_PATIENT_ID = "patient_id"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_SYMPTOMS = "symptoms"
        private const val COLUMN_DIAGNOSIS = "diagnosis"
        private const val COLUMN_NOTES = "notes"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val queryCreate = "CREATE TABLE $TABLE_NAME($COLUMN_RECORD_ID TEXT PRIMARY KEY, $COLUMN_PATIENT_ID INTEGER, $COLUMN_DATE TEXT, $COLUMN_SYMPTOMS TEXT, $COLUMN_DIAGNOSIS TEXT, $COLUMN_NOTES TEXT)"
        db?.execSQL(queryCreate)

        val queryInsert = "INSERT INTO $TABLE_NAME($COLUMN_RECORD_ID, $COLUMN_PATIENT_ID, $COLUMN_DATE, $COLUMN_SYMPTOMS, $COLUMN_DIAGNOSIS, $COLUMN_NOTES) VALUES('01', '1049', '20/11/2025', 'Fiebre, dolor de cabeza', 'Gripe', 'Tomar antivirales')"
        db?.execSQL(queryInsert)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sqlDrop = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(sqlDrop)
        this.onCreate(db)
    }

    fun validateMedicalRecord(medicalRecord: MedicalRecordDTO): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_RECORD_ID = ? AND $COLUMN_PATIENT_ID = ? AND $COLUMN_DATE = ? AND $COLUMN_SYMPTOMS = ? AND $COLUMN_DIAGNOSIS = ? AND $COLUMN_NOTES = ?"
        val cursor = db.rawQuery(query, arrayOf(medicalRecord.recordId, medicalRecord.patientId.toString(), medicalRecord.date, medicalRecord.symptoms, medicalRecord.diagnosis, medicalRecord.notes))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun medicalRecordExists(recordId: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_RECORD_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(recordId))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun insertMedicalRecord(medicalRecord: MedicalRecordDTO): Boolean {
        val patientDbHelper = PatientDataBaseHelper(context)
        if (!patientDbHelper.patientExists(medicalRecord.patientId)) {
            return false
        }

        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RECORD_ID, medicalRecord.recordId)
            put(COLUMN_PATIENT_ID, medicalRecord.patientId)
            put(COLUMN_DATE, medicalRecord.date)
            put(COLUMN_SYMPTOMS, medicalRecord.symptoms)
            put(COLUMN_DIAGNOSIS, medicalRecord.diagnosis)
            put(COLUMN_NOTES, medicalRecord.notes)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    fun updateMedicalRecord(medicalRecord: MedicalRecordDTO): Boolean {
        val patientDbHelper = PatientDataBaseHelper(context)
        if (!patientDbHelper.patientExists(medicalRecord.patientId)) {
            return false
        }

        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RECORD_ID, medicalRecord.recordId)
            put(COLUMN_PATIENT_ID, medicalRecord.patientId)
            put(COLUMN_DATE, medicalRecord.date)
            put(COLUMN_SYMPTOMS, medicalRecord.symptoms)
            put(COLUMN_DIAGNOSIS, medicalRecord.diagnosis)
            put(COLUMN_NOTES, medicalRecord.notes)
        }
        val whereClause = "$COLUMN_RECORD_ID = ?"
        val whereArgs = arrayOf(medicalRecord.recordId)
        val result = db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
        return result > 0
    }

    fun getMedicalRecords(): List<MedicalRecordDTO> {
        val medicalRecordList = mutableListOf<MedicalRecordDTO>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val recordId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECORD_ID))
            val patientId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_ID))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val symptoms = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SYMPTOMS))
            val diagnosis = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIAGNOSIS))
            val notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES))
            val medicalRecord = MedicalRecordDTO(recordId, patientId, date, symptoms, diagnosis, notes)
            medicalRecordList.add(medicalRecord)
        }
        cursor.close()
        db.close()
        return medicalRecordList
    }

    fun deleteMedicalRecordByRecordId(recordId: String) {
        val db = writableDatabase
        val whereClause = "$COLUMN_RECORD_ID = ?"
        val whereArgs = arrayOf(recordId)
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun getMedicalRecordByRecordId(recordId: String): MedicalRecordDTO? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_RECORD_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(recordId))
        var medicalRecord: MedicalRecordDTO? = null

        if (cursor.moveToFirst()) {
            val recordId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECORD_ID))
            val patientId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_ID))
            val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
            val symptoms = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SYMPTOMS))
            val diagnosis = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIAGNOSIS))
            val notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES))
            medicalRecord = MedicalRecordDTO(recordId, patientId, date, symptoms, diagnosis, notes)
        }

        cursor.close()
        db.close()
        return medicalRecord
    }
}