package com.project.Jack.kotlin.sqldata

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.project.Jack.kotlin.app.BaseAppliciton
import org.jetbrains.anko.db.*

/**
 * Created by vslimit on 17/1/23.
 */
class DatabaseOpenHelper(ctx: Context = BaseAppliciton.instance) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    companion object {
        val DB_NAME = "person"
        val DB_VERSION = 1
        val instance by lazy { DatabaseOpenHelper() }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(NotePadTable.TABLE_NAME, true,
                NotePadTable.ID to INTEGER + PRIMARY_KEY + UNIQUE,
                NotePadTable.NAME to TEXT,
                NotePadTable.ADDRESS to TEXT,
                NotePadTable.CONTENT to TEXT,
                NotePadTable.TIME to TEXT,
                NotePadTable.TITLE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(NotePadTable.TABLE_NAME, true)
        onCreate(db)
    }
}