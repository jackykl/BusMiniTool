package com.example.busminitool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.busminitool.Prefix_Data_SQL.*;

public class DBHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "busData.db";
    private final static int DATABASE_VERSION = 17;
    static SQLiteDatabase dbhelper;

    public DBHelper(Context sqLiteDBQuery) {
        super(sqLiteDBQuery, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table SQL execute
        db.execSQL(busBasicINFO_TC);
        db.execSQL(busDetailINFO_TC);
        db.execSQL(busBasicINFO_EN);
        db.execSQL(busDetailINFO_EN);
        db.execSQL(busInterChangeINFO_TC);
        db.execSQL(busInterChangeINFO_EN);

        //Insert data SQL execute
        db.execSQL(INSERT_BUS_DETAIL_INFO_TC_SQL);
        db.execSQL(INSERT_BUS_BASIC_INFO_TC_SQL);
        db.execSQL(INSERT_BUS_DETAIL_INFO_EN_SQL);
        db.execSQL(INSERT_BUS_BASIC_INFO_EN_SQL);
        db.execSQL(INSERT_INTERCHANGEBUS_INFO_TC_SQL);
        db.execSQL(INSERT_INTERCHANGEBUS_INFO_EN_SQL);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop table SQL execute
        db.execSQL(DROP_TABLE_BusBasicInfo_TC);
        db.execSQL(DROP_TABLE_BusDetailInfo_TC);
        db.execSQL(DROP_TABLE_BusBasicInfo_EN);
        db.execSQL(DROP_TABLE_BusDetailInfo_EN);
        db.execSQL(DROP_TABLE_busInterChangeINFO_TC);
        db.execSQL(DROP_TABLE_busInterChangeINFO_EN);
        onCreate(db);
    }


}