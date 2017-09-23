package com.gangnam4bungate.nuviseoul.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gangnam4bungate.nuviseoul.data.PlanData;
import com.gangnam4bungate.nuviseoul.data.PlanDetailData;

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
    public long planInsert(PlanData data){
        ContentValues values = new ContentValues();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String start_date = sdf.format(data.getStart_date());
            String end_date = sdf.format(data.getEnd_date());

            values.put(DataBases.CreatePlanDB._NAME, data.getName());
            values.put(DataBases.CreatePlanDB._STARTDATE, start_date);
            values.put(DataBases.CreatePlanDB._ENDDATE, end_date);
        }catch(Exception e){

        }

        return mDB.insert(DataBases.CreatePlanDB._TABLENAME, null, values);
    }

    // Update DB
    public boolean planUpdate(PlanData data){

        ContentValues values = new ContentValues();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String start_date = sdf.format(data.getStart_date());
            String end_date = sdf.format(data.getEnd_date());

            values.put(DataBases.CreatePlanDB._NAME, data.getName());
            values.put(DataBases.CreatePlanDB._STARTDATE, start_date);
            values.put(DataBases.CreatePlanDB._ENDDATE, end_date);
        }catch(Exception e){

        }

        return mDB.update(DataBases.CreatePlanDB._TABLENAME, values, DataBases.CreatePlanDB._ID + "=" + data.getId(), null) > 0;
    }

    // Delete ID
    public boolean planDelete(long id){
        return mDB.delete(DataBases.CreatePlanDB._TABLENAME, "_id="+id, null) > 0;
    }

    // Delete Contact
    public boolean planDelete(String name){
        return mDB.delete(DataBases.CreatePlanDB._TABLENAME, DataBases.CreatePlanDB._NAME + "="+ name, null) > 0;
    }

    // Select All
    public Cursor planAllColumns(){
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
    public long plandetailInsert(PlanDetailData data){
        ContentValues values = new ContentValues();
        try {
            SimpleDateFormat t_sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String str_datetime = t_sdf.format(data.getDatetime());

            values.put(DataBases.CreatePlanDetailDB._PLANID, data.getPlanid());
            values.put(DataBases.CreatePlanDetailDB._DATETIME, str_datetime);
            values.put(DataBases.CreatePlanDetailDB._PLACE_NAME, data.getPlacename());
            values.put(DataBases.CreatePlanDetailDB._PATH_SEQ, data.getPathseq());
            values.put(DataBases.CreatePlanDetailDB._PLACE_GPS_LATITUDE, data.getLatitude());
            values.put(DataBases.CreatePlanDetailDB._PLACE_GPS_LONGITUDE, data.getLongitude());

        }catch(Exception e){

        }

        return mDB.insert(DataBases.CreatePlanDetailDB._TABLENAME, null, values);
    }

    // Update DB
    public boolean plandetailUpdate(PlanDetailData data){

        ContentValues values = new ContentValues();
        try {
            SimpleDateFormat t_sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String str_datetime = t_sdf.format(data.getDatetime());

            values.put(DataBases.CreatePlanDetailDB._DATETIME, str_datetime);
            values.put(DataBases.CreatePlanDetailDB._PLACE_NAME, data.getPlacename());
            values.put(DataBases.CreatePlanDetailDB._PATH_SEQ, data.getPathseq());
            values.put(DataBases.CreatePlanDetailDB._PLACE_GPS_LATITUDE, data.getLatitude());
            values.put(DataBases.CreatePlanDetailDB._PLACE_GPS_LONGITUDE, data.getLongitude());

        }catch(Exception e){

        }

        return mDB.update(DataBases.CreatePlanDetailDB._TABLENAME, values, DataBases.CreatePlanDetailDB._ID + "=" + data.getPlanid(), null) > 0;
    }

    // Delete planid
    public boolean plandetailDelete(long planid){
        return mDB.delete(DataBases.CreatePlanDetailDB._TABLENAME, DataBases.CreatePlanDetailDB._PLANID + "="+ planid, null) > 0;
    }

    // Select All
    public Cursor plandetailAllColumns(){
        return mDB.query(DataBases.CreatePlanDetailDB._TABLENAME, null, null, null, null, null, null);
    }

    // ID 컬럼 얻어 오기
    public Cursor plandetail_getColumn(long planid){
        Cursor c = mDB.query(DataBases.CreatePlanDetailDB._TABLENAME, null,
                DataBases.CreatePlanDetailDB._PLANID + "="+planid, null, null, null, null);
        if(c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

}
