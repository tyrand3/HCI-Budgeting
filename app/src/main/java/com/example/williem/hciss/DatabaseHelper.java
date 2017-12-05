package com.example.williem.hciss;

/**
 * Created by Williem on 12/2/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.Date;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import static android.content.ContentValues.TAG;

/**
 * Created by Ujang Wahyu on 28/01/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "budgeting.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "expense";

    public static final String COL_1 = "_id";
    public static final String COL_2 = "D_TITLE";

    public static final String COL_3 = "D_VALUE";
    public static final String COL_4 = "D_TYPE";

    public static final String COL_5 = "D_DATE";
    public static final String COL_6 = "D_NOTE";



    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    // @Override
    /*
    public void onCreate(SQLiteDatabase db){
        String sql = "create table" + TABLE_NAME +" (no integer primary key, nama text, tgl text, jk text, alamat text);";
        Log.d("Data","onCreate: "+sql);
        db.execSQL(sql);
    }
*/
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "D_TITLE TEXT NOT NULL, "
                + "D_VALUE INTEGER NOT NULL, " + "D_TYPE INTEGER NOT NULL, " + "D_DATE DATE NOT NULL, " + "D_NOTE TEXT NOT NULL);";

        db.execSQL(createTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2){

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }



    public boolean addData (String title, int value, int type ,String date, String note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2, title);
        cv.put(COL_3, value);
        cv.put(COL_4, type);

        cv.put(COL_5, date);
        cv.put(COL_6, note);

      ///  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      //  String sCertDate = dateFormat.format(new Date());
      //  cv.put(COL_4, sCertDate);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result==-1){
            return false;
        }
        else return true;
    }


    public Integer sumAllToday()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM(D_Value) FROM "+TABLE_NAME+" WHERE D_Date='"+sCertDate+"'", null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        else return 0;
    }

    public Integer sumByDate(String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT SUM(D_Value) FROM expense WHERE D_Date='"+date+"'", null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        else return 0;
    }

    public Integer sumByMonth(String date, String year)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(

                "SELECT SUM(D_Value) FROM expense WHERE strftime('%m', D_DATE) = '"+date+"' AND strftime('%Y', D_DATE) = '"+year+"'", null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        else return 0;
    }


    public Integer sumAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM expense", null);
        if(cursor.moveToFirst()) {
            return cursor.getInt(2);
        }
        else return 0;
    }




    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String sCertDate = dateFormat.format(new Date());

    public Cursor getTodayListContents()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE D_Date='"+sCertDate+"'", null);
        return data;
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }



    public boolean editExpense(String title, int value,int type, String note, String id) {

        String update = "UPDATE "+ TABLE_NAME +" SET D_TITLE='"+title+"', D_VALUE='"+value+"', D_TYPE='"+type+"', D_NOTE='"+note+"' WHERE _id=" + id +";";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        return true;
    }





    public Cursor fetchByNotes(String inputText) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null || inputText.length () == 0) {
            mCursor = db.query(TABLE_NAME, new String[] {COL_1,COL_2,COL_3,COL_4},
                    null, null, null, null, null);

        }
        else {
            mCursor = db.query(true, TABLE_NAME, new String[] {COL_1,
                            COL_2, COL_3, COL_4},
                    COL_3 + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }


    public Cursor getListContents(String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE D_Date='"+date+"'", null);
        return data;
    }

    public boolean deleteExpense(String id) {

        String update = "DELETE FROM "+ TABLE_NAME +" WHERE _id=" + id +";";
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        return true;
    }

    public boolean deleteAllData() {

        String update = "DELETE FROM "+ TABLE_NAME ;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(update);
        return true;
    }




}
