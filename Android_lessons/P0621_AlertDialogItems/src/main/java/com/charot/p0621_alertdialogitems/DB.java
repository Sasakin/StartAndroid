package com.charot.p0621_alertdialogitems;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {

    public static final String DB_NAME = "SOLUTION_CHAROT";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "by_table";
    public static final String COL_ID ="_id";
    public static final String COL_TXT = "txt";

    public static final String DB_CREATE = "create table "+TABLE_NAME+
            "("+COL_ID+" integer primary key, " + COL_TXT+" text);";

    private Context ctx;

    private SQLiteDatabase db;
    private DBHealper dbHealper;

    public DB(Context ctx) {
        this.ctx = ctx;
    }

    public void open() {
        dbHealper = new DBHealper(ctx,DB_NAME, null, DB_VERSION);
        db = dbHealper.getWritableDatabase();
    }

    public void close() {
        dbHealper.close();
    }

    public Cursor getAllData() {
        return db.query(TABLE_NAME,null,null,null,null,null,null);
    }

    public void changeRec(int id, String txt) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TXT, txt);
        db.update(TABLE_NAME,cv,COL_ID +"="+id,null);

    }



    class DBHealper extends SQLiteOpenHelper {

        public DBHealper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String [] data = new String[] {"a1","a2","a3","a4","a5","a6","a7"};

            db.execSQL(DB_CREATE);

            ContentValues cv = new ContentValues();
            for(int i=0; i<data.length; i++) {
                cv.put(COL_ID, i);
                cv.put(COL_TXT, data[i]);

                db.insert(TABLE_NAME,null, cv);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
