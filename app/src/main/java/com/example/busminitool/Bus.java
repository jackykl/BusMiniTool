/*
 * Copyright (C) 2012 jfrankie (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.busminitool;

public class Bus {

    public static boolean Languare = true;
    private String busNum;
    private String busINFO;
    private int busICON;
    private String StationNo;
    private String StationName;
    private String Fare;
    private String HalfFare;
    private String OrignINFO;
    private String DestINFO;
    private int selectItem;
    private String orignDist;
    private String destDist;
    private String orignArea;
    private String destArea;

    public Bus() {
        super();
    }

    public Bus(String busNum, String busINFO, int busICON) {
        this.busNum = busNum;
        this.busINFO = busINFO;
        this.busICON = busICON;
    }

    public Bus(String busNum, String OrignINFO, String DestINFO, int busICON) {
        this.busNum = busNum;
        this.OrignINFO = OrignINFO;
        this.DestINFO = DestINFO;
        this.busICON = busICON;
    }

    public Bus(String busNUM, String StationNo, String StationName, String Fare, String HalfFare, int busICON) {
        this.busNum = busNUM;
        this.StationNo = StationNo;
        this.StationName = StationName;
        this.Fare = Fare;
        this.HalfFare = HalfFare;
        this.busICON = busICON;
    }

    public static boolean isLanguare() {
        return Languare;
    }

    public static void setLanguare(boolean languare) {
        Languare = languare;
    }

    public String getHalfFare() {
        return HalfFare;
    }

    public void setHalfFare(String halfFare) {
        HalfFare = halfFare;
    }

    public String getBusNum() {
        return busNum;
    }

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public String getBusINFO() {
        return busINFO;
    }

    public int getBusICON() {
        return busICON;
    }

    public String getStationNo() {
        return StationNo;
    }

    public String getStationName() {
        return StationName;
    }

    public String getFare() {
        return Fare;
    }

    public String getOrignDist() {
        return orignDist;
    }

    public void setOrignDist(String orignDist) {
        this.orignDist = orignDist;
    }

    public String getDestDist() {
        return destDist;
    }

    public void setDestDist(String destDist) {
        this.destDist = destDist;
    }

    public String getOrignArea() {
        return orignArea;
    }

    public void setOrignArea(String orignArea) {
        this.orignArea = orignArea;
    }

    public String getDestArea() {
        return destArea;
    }

    public void setDestArea(String destArea) {
        this.destArea = destArea;
    }

    public String getOrignINFO() {
        return OrignINFO;
    }

    public void setOrignINFO(String orignINFO) {
        OrignINFO = orignINFO;
    }

    public String getDestINFO() {
        return DestINFO;
    }

    public void setDestINFO(String destINFO) {
        DestINFO = destINFO;
    }
}



