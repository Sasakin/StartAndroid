package com.charot.example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {

    public static final String DB_NAME = "my_db";
    public static final int DB_VERSION = 1;

    public static final String COMPANY_TABLE = "company";
    public static final String COMPANY_COLUMN_ID = "_id";
    public static final String COMPANY_COLUMN_NAME = "name";
    public static final String CREATE_COMPANY_TABLE = "create table "+ COMPANY_TABLE +"("+COMPANY_COLUMN_ID
    +" integer primary key, " + COMPANY_COLUMN_NAME +" text);";


    public static final String PHONE_TABLE = "phone";
    public static final String PHONE_COLUMN_ID = "_id";
    public static final String PHONE_COLUMN_NAME = "name";
    public static final String PHONE_COLUMN_COMPANY = "company";
    public static final String PHONE_TABLE_CREATE = "create table "+PHONE_TABLE +
            "("+PHONE_COLUMN_ID+" integer primary key autoincrement, " + PHONE_COLUMN_NAME + " text, "
            + PHONE_COLUMN_COMPANY+" integer );";




    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private final Context ctx;

    public DB(Context ctx) {
        this.ctx = ctx;
    }

    public void open() {
        dbHelper = new DBHelper(ctx, DB_NAME, null, DB_VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if(dbHelper != null)
            dbHelper.close();
    }

    public Cursor getCompanyData() {
        return db.query(COMPANY_TABLE,null,null,null,null,null,null);
    }

    public Cursor getPhonesData(long companyID) {
        return db.query(PHONE_TABLE,null,PHONE_COLUMN_COMPANY + "=" + companyID,null,null,null,null);
    }

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            ContentValues cv = new ContentValues();

            String[] companyes = new String[] {"HTC", "Samsung", "LG" };

            db.execSQL(CREATE_COMPANY_TABLE);

            for(int i = 0; i < companyes.length; i++) {
                cv.put(COMPANY_COLUMN_ID, i + 1);
                cv.put(COMPANY_COLUMN_NAME, companyes[i]);
                db.insert(COMPANY_TABLE,null, cv);
            }

            cv.clear();
            db.execSQL(PHONE_TABLE_CREATE);

            String[] phonesHTC = new String[] { "Sensation", "Desire",
                    "Wildfire", "Hero" };
            String[] phonesSams = new String[] { "Galaxy S II", "Galaxy Nexus",
                    "Wave" };
            String[] phonesLG = new String[] { "Optimus", "Optimus Link",
                    "Optimus Black", "Optimus One" };

            for(int i = 0; i<phonesHTC.length; i++) {
                cv.put(PHONE_COLUMN_COMPANY, 1);
                cv.put(PHONE_COLUMN_NAME, phonesHTC[i]);
                db.insert(PHONE_TABLE, null, cv);
            }


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
