package com.gangnam4bungate.nuviseoul.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wsseo on 2017. 6. 27..
 */

public class DBOpenHelper {
    private static final String DATABASE_NAME = "plan.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper{

        // 생성자
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreatePlanDB._CREATE);
            db.execSQL(DataBases.CreatePlanDetailDB._CREATE);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.CreatePlanDB._TABLENAME);
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.CreatePlanDetailDB._TABLENAME);
            onCreate(db);
        }
    }

    public DBOpenHelper(Context context){
        this.mCtx = context;
    }

    public DBOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDB.close();
    }


    // Insert DB
    public long plan_insertColumn(String name, Date startdate, Date enddate){
        ContentValues values = new ContentValues();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String start_date = sdf.format(startdate);
            String end_date = sdf.format(enddate);

            values.put(DataBases.CreatePlanDB._NAME, name);
            values.put(DataBases.CreatePlanDB._STARTDATE, start_date);
            values.put(DataBases.CreatePlanDB._ENDDATE, end_date);
        }catch(Exception e){

        }

        return mDB.insert(DataBases.CreatePlanDB._TABLENAME, null, values);
    }

    // Update DB
    public boolean plan_updateColumn(long id , String name, Date startdate, Date enddate){

        ContentValues values = new ContentValues();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String start_date = sdf.format(startdate);
            String end_date = sdf.format(enddate);

            values.put(DataBases.CreatePlanDB._NAME, name);
            values.put(DataBases.CreatePlanDB._STARTDATE, start_date);
            values.put(DataBases.CreatePlanDB._ENDDATE, end_date);
        }catch(Exception e){

        }

        return mDB.update(DataBases.CreatePlanDB._TABLENAME, values, DataBases.CreatePlanDB._ID + "=" + id, null) > 0;
    }

    // Delete ID
    public boolean plan_deleteColumn(long id){
        return mDB.delete(DataBases.CreatePlanDB._TABLENAME, "_id="+id, null) > 0;
    }

    // Delete Contact
    public boolean plan_deleteColumn(String name){
        return mDB.delete(DataBases.CreatePlanDB._TABLENAME, DataBases.CreatePlanDB._NAME + "="+ name, null) > 0;
    }

    // Select All
    public Cursor plan_getAllColumns(){
        return mDB.query(DataBases.CreatePlanDB._TABLENAME, null, null, null, null, null, null);
    }

    // ID 컬럼 얻어 오기
    public Cursor plan_getColumn(long id){
        Cursor c = mDB.query(DataBases.CreatePlanDB._TABLENAME, null,
                "_id="+id, null, null, null, null);
        if(c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

    // 이름 검색 하기 (rawQuery)
    public Cursor plan_getMatchName(String name){
        Cursor c = mDB.rawQuery( "select * from " + DataBases.CreatePlanDB._TABLENAME + "where " + DataBases.CreatePlanDB._NAME + " =" + "'" + name + "'" , null);
        return c;
    }

    // Insert DB
    public long plandetail_insertColumn(long plankey,Date date, Date time,String placename, String distance, double latitude, double longitude){
        ContentValues values = new ContentValues();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat t_sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String str_date = sdf.format(date);
            String str_time = t_sdf.format(time);

            values.put(DataBases.CreatePlanDetailDB._PLANKEY, plankey);
            values.put(DataBases.CreatePlanDetailDB._DATE, str_date);
            values.put(DataBases.CreatePlanDetailDB._TIME, str_time);
            values.put(DataBases.CreatePlanDetailDB._PLACE_NAME, placename);
            values.put(DataBases.CreatePlanDetailDB._DISTANCE, distance);
            values.put(DataBases.CreatePlanDetailDB._PLACE_GPS_LATITUDE, latitude);
            values.put(DataBases.CreatePlanDetailDB._PLACE_GPS_LONGITUDE, longitude);

        }catch(Exception e){

        }

        return mDB.insert(DataBases.CreatePlanDetailDB._TABLENAME, null, values);
    }

    // Update DB
    public boolean plandetail_updateColumn(long id, long plankey,Date date, Date time,String placename, String distance, double latitude, double longitude){

        ContentValues values = new ContentValues();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat t_sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String str_date = sdf.format(date);
            String str_time = t_sdf.format(time);

            values.put(DataBases.CreatePlanDetailDB._PLANKEY, plankey);
            values.put(DataBases.CreatePlanDetailDB._DATE, str_date);
            values.put(DataBases.CreatePlanDetailDB._TIME, str_time);
            values.put(DataBases.CreatePlanDetailDB._PLACE_NAME, placename);
            values.put(DataBases.CreatePlanDetailDB._DISTANCE, distance);
            values.put(DataBases.CreatePlanDetailDB._PLACE_GPS_LATITUDE, latitude);
            values.put(DataBases.CreatePlanDetailDB._PLACE_GPS_LONGITUDE, longitude);

        }catch(Exception e){

        }

        return mDB.update(DataBases.CreatePlanDetailDB._TABLENAME, values, DataBases.CreatePlanDetailDB._ID + "=" + id, null) > 0;
    }

    // Delete plankey
    public boolean plandetail_deleteColumn(long plankey){
        return mDB.delete(DataBases.CreatePlanDetailDB._TABLENAME, DataBases.CreatePlanDetailDB._PLANKEY + "="+ plankey, null) > 0;
    }

    // Select All
    public Cursor plandetail_getAllColumns(){
        return mDB.query(DataBases.CreatePlanDetailDB._TABLENAME, null, null, null, null, null, null);
    }

    // ID 컬럼 얻어 오기
    public Cursor plandetail_getColumn(long id){
        Cursor c = mDB.query(DataBases.CreatePlanDetailDB._TABLENAME, null,
                "_id="+id, null, null, null, null);
        if(c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

    // 이름 검색 하기 (rawQuery)
    public Cursor plandetail_getMatchPlankey(long plankey){
        Cursor c = mDB.rawQuery( "select * from " + DataBases.CreatePlanDetailDB._TABLENAME + " where "
                + DataBases.CreatePlanDetailDB._PLANKEY + " =" + "'" + plankey + "'" , null);
        return c;
    }

}
