package com.example.claudio_garrido_20240522_continuidad_ing_informatica

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class AttendanceRepository(context: Context) {
    private val dbHelper = AttendanceDatabaseHelper(context)

    fun insertAttendanceRecord(record: AttendanceRecord): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(AttendanceDatabaseHelper.COLUMN_RUT, record.rut)
            put(AttendanceDatabaseHelper.COLUMN_NAME, record.name)
            put(AttendanceDatabaseHelper.COLUMN_LASTNAME, record.lastname)
            put(AttendanceDatabaseHelper.COLUMN_DATE_TIME, record.dateTime)
            put(AttendanceDatabaseHelper.COLUMN_TYPE, record.type)
        }
        return db.insert(AttendanceDatabaseHelper.TABLE_NAME, null, values)
    }

    fun getAllAttendanceRecords(): List<AttendanceRecord> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            AttendanceDatabaseHelper.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "${AttendanceDatabaseHelper.COLUMN_DATE_TIME} DESC"
        )
        return cursorToAttendanceList(cursor)
    }

    private fun cursorToAttendanceList(cursor: Cursor): List<AttendanceRecord> {
        val attendanceList = mutableListOf<AttendanceRecord>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(AttendanceDatabaseHelper.COLUMN_ID))
                val rut = getString(getColumnIndexOrThrow(AttendanceDatabaseHelper.COLUMN_RUT))
                val name = getString(getColumnIndexOrThrow(AttendanceDatabaseHelper.COLUMN_NAME))
                val lastname = getString(getColumnIndexOrThrow(AttendanceDatabaseHelper.COLUMN_LASTNAME))
                val dateTime = getString(getColumnIndexOrThrow(AttendanceDatabaseHelper.COLUMN_DATE_TIME))
                val type = getString(getColumnIndexOrThrow(AttendanceDatabaseHelper.COLUMN_TYPE))
                attendanceList.add(AttendanceRecord(id, rut, name, lastname, dateTime, type))
            }
        }
        cursor.close()
        return attendanceList
    }
}
