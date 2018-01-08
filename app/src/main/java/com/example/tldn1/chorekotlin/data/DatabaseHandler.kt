package com.example.tldn1.chorekotlin.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.tldn1.chorekotlin.model.ChoreModel
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by tldn1 on 1/7/2018.
 */
class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {


        var CREATE_CHORE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +

                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_CHORE_NAME + " TEXT," +
                KEY_CHORE_ASSIGNED_TO + " TEXT," +
                KEY_CHORE_ASSIGNED_BY + " TEXT," +
                KEY_CHORE_ASSIGNED_TIME + " LONG)"


        db?.execSQL(CREATE_CHORE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db)

    }


    fun createChore(chore: ChoreModel) {

        var db: SQLiteDatabase = writableDatabase

        var contentV = ContentValues()
        contentV.put(KEY_CHORE_NAME, chore.choreName)
        contentV.put(KEY_CHORE_ASSIGNED_TO, chore.choreAssignedTo)
        contentV.put(KEY_CHORE_ASSIGNED_BY, chore.choreAssignedBy)
        contentV.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        db.insert(TABLE_NAME, null, contentV)
        db.close()

    }


    fun readSpecificChore(id: Int): ChoreModel {

        var db: SQLiteDatabase = writableDatabase

        var c: Cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_ID + " = " + id.toString(), null)

        if (c != null)
            c.moveToFirst()

        var chore = ChoreModel()

        chore.id = c.getInt(0)
        chore.choreName = c.getString(1)
        chore.choreAssignedTo = c.getString(2)
        chore.choreAssignedBy = c.getString(3)
        chore.timeAssigned = 0

        var dateFormat: DateFormat = DateFormat.getDateInstance()
        var formattedDate = dateFormat.format(Date(c.getLong(4)).time)
        return chore;

    }

    fun readAllChores(): ArrayList<ChoreModel> {
        var db: SQLiteDatabase = writableDatabase

        var c: Cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

        var arraylist: ArrayList<ChoreModel> = ArrayList<ChoreModel>()

        if (c.moveToFirst()) {
            do {
                var chore = ChoreModel()

                chore.id = c.getInt(0)
                chore.choreName = c.getString(1)
                chore.choreAssignedTo = c.getString(2)
                chore.choreAssignedBy = c.getString(3)
                chore.timeAssigned = 0

                arraylist.add(chore)


            } while (c.moveToNext())

        }

        return arraylist
    }

    fun deleteChore(id: Int) {
        var db: SQLiteDatabase = writableDatabase
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE id =" + id.toString())
    }


    fun editChore(id: Int, choreName: String, choreAssignedTo: String, choreAssignedBy: String) {

        var db: SQLiteDatabase = writableDatabase

        db.execSQL("UPDATE " + TABLE_NAME + " SET " + KEY_CHORE_NAME + " = '" + choreName + "' , " +
                KEY_CHORE_ASSIGNED_TO + " = '" + choreAssignedTo +"' , "+
                KEY_CHORE_ASSIGNED_BY + " = '" + choreAssignedBy + "' WHERE id =  " + id.toString()
        )

        db.close()

    }

}