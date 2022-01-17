package com.example.waqay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper
{
    SQLiteDatabase database;
    public MyDatabase( Context context) {
        super(context, "college", null, 1);
        database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
  db.execSQL("create table student(id INTEGER, name TEXT , mobile TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STUDENT");
        onCreate(db);

    }
    public void insert(int id , String name , String mobile)
    {
        ContentValues c = new ContentValues();
        c.put("id",1);
        c.put("name","mazhar");
        c.put("mobile","12345");
        database.insert("student",null,c);
    }
    public String selectRow()
    {
        String[] cols=new String[3];
        cols[0]="id";
        cols[1]="name";
        cols[2]="mobile";
        String row="";
        Cursor c= database.query("lords",cols,null,null,null,null,null);
        while(c.isAfterLast())
        {
            row=row + c.getInt(0)+" "+c.getString(1)+" "+c.getString(2);
            c.moveToNext();
        }
        return row;
    }
    public void deleteRow()
    {
        String[] cols = new String[1];
        cols[0]="id";
        database.delete("college","id==1",cols);
    }
}
