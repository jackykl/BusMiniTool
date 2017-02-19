package com.example.busminitool;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.busminitool.DbConstants.*;


public class BusDetail extends Activity {
    private static final int[] busICON = new int[]{R.drawable.kmb_logo_icon, R.drawable.citybus_logo_icon, R.drawable.firstbus_logo_icon};
    static DBHelper SearchHelper;
    private static String busNum;
    private static String busDirection;
    List<Bus> busDetailList = new ArrayList<Bus>();
    private BusDetailAdapter adapter;
    private ListView lv;

    public static Cursor getBusINFOCursor() {
        SQLiteDatabase db = SearchHelper.getReadableDatabase();
        if(Bus.isLanguare()==true) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + BusDetailInfo_TC + " WHERE BusNo='" + busNum + "'and direction='" + busDirection.substring(busDirection.indexOf("往") + 2) + "'", null);
            Log.d("test", "SELECT * FROM " + BusDetailInfo_TC + " WHERE BusNo='" + busNum + "'and direction='" + busDirection.substring(busDirection.indexOf("往") + 2) + "'");
            return cursor;
        }else{
            Cursor cursor = db.rawQuery("SELECT * FROM " + BusDetailInfo_EN + " WHERE BusNo='" + busNum + "'and direction='" + busDirection.substring(busDirection.indexOf("To") + 3) + "'", null);
            Log.d("test", "SELECT * FROM " + BusDetailInfo_EN + " WHERE BusNo='" + busNum + "'and direction='" + busDirection.substring(busDirection.indexOf("To") + 3) + "'");
            return cursor;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bus_detail);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        if (Bus.isLanguare() == true) {
            actionBar.setTitle("巴士路線詳細資料");
        } else {
            actionBar.setTitle("Bus Routes Details");
        }
        if (getIntent().getExtras() != null) {
            Intent sender = getIntent(); //Received the value that intent pass in
            busNum = sender.getExtras().getString("busNum");
            busDirection = sender.getExtras().getString("Direction");
            lv = (ListView) findViewById(R.id.list_view);
            TextView txtBusNum = (TextView) findViewById(R.id.txtBusNum);
            Log.d("busNum", busNum);
            Log.d("busNum2", busDirection);
            txtBusNum.setText(busNum + " " + busDirection);
            adapter = new BusDetailAdapter(busDetailList, this);

            openDatabase();
            QueryBUSINFO();
            closeDatabase();

            lv.setAdapter(adapter);
            onLongClickListviewEvent();
            onClickListviewEvent();
        }
    }

    private void openDatabase() {
        SearchHelper = new DBHelper(this);
    }

    private void closeDatabase() {
        SearchHelper.close();
    }

    public void QueryBUSINFO() {

        Cursor cursor = getBusINFOCursor();

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {
                    Log.d("test", "1");
                    String busNUM = cursor.getString(1);
                    String StationNo = cursor.getString(2);
                    String StationName = cursor.getString(3);
                    String Fare = "$" + cursor.getString(4);
                    String HalfFare = "$" + cursor.getString(5);
                    String Direction = cursor.getString(6);
                    Log.d("test", StationName);
                    if (busNUM.indexOf("九巴") != -1 || busNUM.indexOf("KMB") != -1) {
                        busDetailList.add(new Bus(busNUM, StationNo, StationName, Fare, HalfFare, busICON[0]));
                    } else if (busNUM.indexOf("城巴") != -1 || busNUM.indexOf("CITYBUS") != -1) {
                        busDetailList.add(new Bus(busNUM, StationNo, StationName, Fare, HalfFare, busICON[1]));
                    } else if (busNUM.indexOf("新巴") != -1 || busNUM.indexOf("FIRST BUS") != -1) {
                        busDetailList.add(new Bus(busNUM, StationNo, StationName, Fare, HalfFare, busICON[2]));
                    }


                } while (cursor.moveToNext());

            }
            cursor.close();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ShowMsgDialog(String Msg) {
        Builder MyAlertDialog = new AlertDialog.Builder(this);
        if (Bus.isLanguare() == true) {
            MyAlertDialog.setTitle("預計乘車時間");
        } else {
            MyAlertDialog.setTitle("The estimate time for the ride.");
        }
        MyAlertDialog.setMessage(Msg);
        DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        };
        ;
        if (Bus.isLanguare() == true) {
            MyAlertDialog.setNeutralButton("確認", OkClick);
        } else {
            MyAlertDialog.setNeutralButton("OK", OkClick);
        }

        MyAlertDialog.show();
    }

    public void onClickListviewEvent() {
        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container,
                                    int position, long id) {
                LinearLayout LL0 = (LinearLayout) container;
                LinearLayout LL1 = (LinearLayout) LL0.getChildAt(0);
                RelativeLayout RL0 = (RelativeLayout) LL1.getChildAt(0);
                LinearLayout LL2 = (LinearLayout) RL0.getChildAt(0);
                RelativeLayout RL1 = (RelativeLayout) LL2.getChildAt(0);
                TextView busStationName = (TextView) RL1.getChildAt(2);
                final String StationName = busStationName.getText().toString();
                Log.d("StationName", StationName);
                if (StationName.equals("西區海底隧道收費廣場") || StationName.equals("西區海底隧道") || StationName.equals("WESTERN HARBOUR CROSSING") || StationName.equals("Western Harbour Crossing Toll")) {

                    Intent intent1 = new Intent(getApplication(), InterChangeBus.class);

                    startActivity(intent1);
                }


            }

        };
        lv.setOnItemClickListener(itemClickListener);
    }

    public void onLongClickListviewEvent() {

        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        lv.setMultiChoiceModeListener(new MultiChoiceModeListener() {

            private int nr = 0;

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                Log.d("test", Integer.toString(nr));
                if (Bus.isLanguare() == true) {
                    ShowMsgDialog("乘坐" + nr + "個車站預計需時" + nr * 5 + "分鐘");
                } else {
                    ShowMsgDialog("Take " + nr + " station need " + nr * 5 + " minus (Estimate)");
                }
                adapter.clearSelection();

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                nr = 0;
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // TODO Auto-generated method stub
                switch (item.getItemId()) {

                }
                return false;
            }

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                  long id, boolean checked) {
                // TODO Auto-generated method stub
                if (checked) {
                    nr++;
                    adapter.setNewSelection(position, checked);
                } else {
                    nr--;
                    adapter.removeSelection(position);
                }
                if (Bus.isLanguare() == true) {
                    mode.setTitle("已經選擇" + nr + "車站");
                } else {
                    mode.setTitle("Selected " + nr + " Stations");
                }

            }
        });

        lv.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                lv.setItemChecked(position, adapter.isPositionChecked(position));
                return false;
            }
        });


    }

}
