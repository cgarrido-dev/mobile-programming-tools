package com.example.claudio_garrido_20240522_continuidad_ing_informatica

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class AttendanceContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.controltotal.attendanceprovider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/${AttendanceDatabaseHelper.TABLE_NAME}")
        private const val ATTENDANCE = 1
        private const val ATTENDANCE_ID = 2

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, AttendanceDatabaseHelper.TABLE_NAME, ATTENDANCE)
            addURI(AUTHORITY, "${AttendanceDatabaseHelper.TABLE_NAME}/#", ATTENDANCE_ID)
        }
    }

    private lateinit var dbHelper: AttendanceDatabaseHelper

    override fun onCreate(): Boolean {
        dbHelper = AttendanceDatabaseHelper(context as Context)
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val db = dbHelper.readableDatabase
        return when (uriMatcher.match(uri)) {
            ATTENDANCE -> db.query(AttendanceDatabaseHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder)
            ATTENDANCE_ID -> {
                val id = ContentUris.parseId(uri)
                db.query(AttendanceDatabaseHelper.TABLE_NAME, projection, "${AttendanceDatabaseHelper.COLUMN_ID}=?", arrayOf(id.toString()), null, null, sortOrder)
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            ATTENDANCE -> "vnd.android.cursor.dir/vnd.$AUTHORITY.${AttendanceDatabaseHelper.TABLE_NAME}"
            ATTENDANCE_ID -> "vnd.android.cursor.item/vnd.$AUTHORITY.${AttendanceDatabaseHelper.TABLE_NAME}"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        val id = db.insert(AttendanceDatabaseHelper.TABLE_NAME, null, values)
        context?.contentResolver?.notifyChange(uri, null)
        return ContentUris.withAppendedId(CONTENT_URI, id)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        return db.delete(AttendanceDatabaseHelper.TABLE_NAME, selection, selectionArgs)
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        return db.update(AttendanceDatabaseHelper.TABLE_NAME, values, selection, selectionArgs)
    }
}
