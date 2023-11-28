package com.example.closetgremlin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class SQLite extends SQLiteOpenHelper {

    private static final String tableName = "closet";

    public SQLite(Context context) {
        super(context, "clothing.db", null, 1);
    }

    private void createTableCommand(SQLiteDatabase db) {
        db.execSQL("create Table " + tableName + "(_id INTEGER PRIMARY KEY, imagePath TEXT, name TEXT, type TEXT, occasion TEXT, location TEXT, bitmap INTEGER)");
    }

    private void dropTableCommand(SQLiteDatabase db) {
        db.execSQL("drop Table if exists " + tableName);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        createTableCommand(db);
        createTests(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        dropTableCommand(db);
    }

    public void reset(){
        SQLiteDatabase db = this.getWritableDatabase();
        dropTableCommand(db);
        createTableCommand(db);
    }

    /*
     * Method insert Add data to the database, can be called anywhere
     */
    public boolean insert(String imagePath, String name, String type, String occasion, String location, boolean bitmap){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("imagePath", imagePath);
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("occasion", occasion);
        contentValues.put("location", location);
        contentValues.put("bitmap", (bitmap)? 1 : 0);
        long result = db.insert(tableName, null, contentValues);
        if(result == -1)
            return false;
        return true;
    }

    public boolean insert(String imagePath, String name, String type, String occasion, String location, boolean bitmap, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("imagePath", imagePath);
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("occasion", occasion);
        contentValues.put("location", location);
        contentValues.put("bitmap", (bitmap)? 1 : 0);
        long result = db.insert(tableName, null, contentValues);
        if(result == -1)
            return false;
        return true;
    }

    /*
     * Method update Updates certain task.
     *
     * @param id, The id of the task in the database.
     */
    public boolean update(String id, String imagePath, String name, String type, String occasion, String location, Boolean bitmap){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("imagePath", imagePath);
        contentValues.put("name", name);
        contentValues.put("type", type);
        contentValues.put("occasion", occasion);
        contentValues.put("location", location);
        contentValues.put("bitmap", (bitmap)? 1 : 0);
        long result = db.update(tableName, contentValues, "_id=?", new String[] {id});
        if(result == -1)
            return false;
        return true;
    }

    /*
     * Method delete Deletes certain task.
     *
     * @param id, The id of the task to delete.
     */
    public boolean delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(tableName, "_id=?", new String[] {id});
        if(result == -1)
            return false;
        return true;
    }

    /*
     * Method getData Gets all the data from the database and returns it to be used.
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from " + tableName, null);
    }


    public void createTests(SQLiteDatabase db) {
        insert(String.valueOf(R.drawable.jacket_1), "Northface Jacket", "Jacket", "Winter", "College", false, db);
        insert(String.valueOf(R.drawable.jacket_2), "Stussy Jacket", "Jacket", "Regular", "Home", false, db);
        insert(String.valueOf(R.drawable.pants), "Gap Thrifted Pants", "Pants", "Fancy", "College", false, db);
        insert(String.valueOf(R.drawable.shirt_1), "Northeastern Shirt", "Shirt", "PJ", "College", false, db);
        insert(String.valueOf(R.drawable.shirt_2), "EADS Shirt", "Shirt", "Regular", "Home", false, db);
    }
}
