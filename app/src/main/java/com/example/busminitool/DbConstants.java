package com.example.busminitool;

import android.provider.BaseColumns;

public interface DbConstants extends BaseColumns {
    public static final String BusBasicInfo_TC = "busbasicinfo_tc";
    public static final String BusDetailInfo_TC = "busdetailinfo_tc";
    public static final String BusBasicInfo_EN = "busbasicinfo_en";
    public static final String BusDetailInfo_EN = "busdetailinfo_en";
    public static final String BUSNO = "BusNo";
    public static final String BUSINFO = "businfo";
    public static final String LOCATION = "location";
    public static final String STATIONNAME = "stationName";
    public static final String STATIONNO = "stationNo";
    public static final String DIRECTION = "direction";
    public static final String ADDRESS = "address";
    public static final String FARE = "Fare";
    public static final String HALFFARE = "HalfFare";
    public static final String BusInterChangeInfo_TC = "businterchangeinfo_tc";
    public static final String ORIGIN = "origin";
    public static final String DESTINAION = "destinaion";
    public static final String BusInterChangeInfo_EN = "businterchangeinfo_en";
}
