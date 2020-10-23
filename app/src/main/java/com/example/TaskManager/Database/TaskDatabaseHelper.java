package com.example.TaskManager.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.TaskManager.Database.TaskDatabaseSchema.*;

import androidx.annotation.Nullable;

public class TaskDatabaseHelper extends SQLiteOpenHelper {
    public TaskDatabaseHelper(@Nullable Context context) {
        super(context, TaskDatabaseSchema.NAME,null, TaskDatabaseSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(String.format("create table %s(%s integer primary key autoincrement,%s text,%s integer,%s text,%s text);", TaskTable.NAME, TaskTable.Columns.ID, TaskTable.Columns.TITLE, TaskTable.Columns.STATE, TaskTable.Columns.DESCRIPTION, TaskTable.Columns.DATE));

        sqLiteDatabase.execSQL(String.format("create table %s(%s integer primary key autoincrement,%s text,%s );", UserTable.NAME, UserTable.Columns.ID, UserTable.Columns.USERNAME, UserTable.Columns.PASSWORD));

//        sqLiteDatabase.execSQL("create table " + UserTable.NAME + "(" +
//                UserTable.Columns.ID +" integer primary key autoincrement,"+
//                UserTable.Columns.USERNAME + " text,"+
//                UserTable.Columns.PASSWORD + " );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
