package com.example.lorelocker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDatabaseHelper(context: Context, private val tableName: String) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
    }

    fun tableValidation(){
        val db = writableDatabase
        val createTableQuery = """
            CREATE TABLE IF NOT EXISTS $tableName (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_TITLE TEXT,
            $COLUMN_CONTENT TEXT
            )
            """.trimIndent()
        db.execSQL(createTableQuery)
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertNote(note: Note){
        tableValidation()
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }
        db.insert(tableName, null, values)
        db.close()
    }

    fun getAllNotes(): List<Note>{
        tableValidation()
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName", null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            notesList.add(Note(id,title,content))
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateNote(note: Note){
        tableValidation()
        val db = writableDatabase
        val values = ContentValues().apply{
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
        }
        db.update(tableName, values, "$COLUMN_ID = ?", arrayOf(note.id.toString()))
        db.close()
    }

    fun getNoteByID(noteId: Int): Note{
        tableValidation()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $COLUMN_ID = $noteId", null)
        cursor.moveToFirst()
        val note = Note(
            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        )
        cursor.close()
        db.close()
        return note
    }

    fun deleteNote(noteId: Int){
        tableValidation()
        val db = writableDatabase
        db.delete(tableName, "$COLUMN_ID = ?", arrayOf(noteId.toString()))
        db.close()
    }

}