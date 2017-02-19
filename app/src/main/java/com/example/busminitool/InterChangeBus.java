package com.example.busminitool;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.busminitool.DbConstants.*;

public class InterChangeBus extends Activity {

    private static final int[] busICON = new int[]{R.drawable.kmb_logo_icon,
            R.drawable.citybus_logo_icon, R.drawable.firstbus_logo_icon};
    static DBHelper SearchHelper;
    static ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    private static String OrignArea;
    private static String DestArea;
    List<Bus> busList = new ArrayList<Bus>();
    private ListView lv;
    private TextView InterChangeTV;
    private InterchangeAdapter adapter;

    public static Cursor getInterChangeInfoCursor() {
        SQLiteDatabase db = SearchHelper.getReadableDatabase();
        if(Bus.isLanguare()==true) {
            Cursor cursor = db.rawQuery("Select * FROM " + BusInterChangeInfo_TC, null);
            return cursor;
        }else{
            Cursor cursor = db.rawQuery("Select * FROM " + BusInterChangeInfo_EN, null);
            return cursor;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_interchanege);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        if (Bus.isLanguare() == true) {
            actionBar.setTitle("巴士轉乘資料");
            ShowMsgDialog("970X 往香港仔 次程收費 $5.60");
        } else {
            actionBar.setTitle("InterChange Bus Routes Details");
            ShowMsgDialog("970X To Aberdeen second ride $5.60");
        }
        InterChangeTV = (TextView) findViewById(R.id.interChangeTV);


        if (Bus.isLanguare() == true) {
            InterChangeTV.setText("可轉乘的路線:");
        } else {
            InterChangeTV.setText("InterChange Bus Routes ");
        }
        lv = (ListView) findViewById(R.id.list_view);
        openDatabase();
        QueryInterChangeInfo();
        closeDatabase();
        adapter = new InterchangeAdapter(busList, this);

        lv.setAdapter(adapter);
        onClickListviewEvent();
        lv.setTextFilterEnabled(true);
    }

    private void openDatabase() {
        SearchHelper = new DBHelper(this);
    }

    private void closeDatabase() {
        SearchHelper.close();
    }

    public void QueryInterChangeInfo() {

        Cursor cursor = getInterChangeInfoCursor();

        if (cursor != null) {

            if (cursor.moveToFirst()) {

                do {

                    String busNUM = cursor.getString(1);
                    String OrignInfo = cursor.getString(2);
                    String DestINFO = cursor.getString(3);
                    Log.d("test", busNUM);
                    if (busNUM.indexOf("九巴") != -1 || busNUM.indexOf("KMB") != -1) {
                        if(Bus.isLanguare()==true) {
                            busList.add(new Bus(busNUM, OrignInfo + " 開", "往 " + DestINFO, busICON[0]));
                        }else{
                            busList.add(new Bus(busNUM, "From "+OrignInfo, "To " + DestINFO, busICON[0]));
                        }
                    } else if (busNUM.indexOf("城巴") != -1 || busNUM.indexOf("CITYBUS") != -1) {
                        if(Bus.isLanguare()==true) {
                            busList.add(new Bus(busNUM, OrignInfo + " 開", "往 " + DestINFO, busICON[1]));
                        }else{
                            busList.add(new Bus(busNUM, "From "+OrignInfo, "To " + DestINFO, busICON[1]));
                        }
                    } else if (busNUM.indexOf("新巴") != -1 || busNUM.indexOf("FIRST BUS") != -1) {
                        if(Bus.isLanguare()==true) {
                            busList.add(new Bus(busNUM, OrignInfo + " 開", "往 " + DestINFO, busICON[2]));
                        }else{
                            busList.add(new Bus(busNUM, "From "+OrignInfo, "To " + DestINFO, busICON[2]));
                        }
                    }

                } while (cursor.moveToNext());

            }
            cursor.close();

        }

    }

    public void onClickListviewEvent() {
        OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container,
                                    int position, long id) {
                LinearLayout LL0 = (LinearLayout) container;

                // Getting the inner Linear Layout
                LinearLayout LL1 = (LinearLayout) LL0.getChildAt(0);
                RelativeLayout RL0 = (RelativeLayout) LL1.getChildAt(0);
                LinearLayout LL2 = (LinearLayout) RL0.getChildAt(0);
                RelativeLayout RL1 = (RelativeLayout) LL2.getChildAt(0);
                // Getting the Country TextView
                TextView busNum = (TextView) RL1.getChildAt(2);
                TextView Direction = (TextView) RL1.getChildAt(3);
                final String busCode = busNum.getText().toString();
                final String busDirection = Direction.getText().toString();
                // Log.d("LoginStatus", Boolean.toString(Logined));
                Log.d("BusNumber", busCode);
                Log.d("Direction", busDirection.substring(busDirection.indexOf(" ") + 1));
                Intent intent1 = new Intent(getApplication(), BusDetail.class);
                intent1.putExtra("busNum", busCode);
                intent1.putExtra("Direction", busDirection);
                startActivity(intent1);

            }

        };
        lv.setOnItemClickListener(itemClickListener);
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
        AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);
        if (Bus.isLanguare() == true) {
            MyAlertDialog.setTitle("提示");
        } else {
            MyAlertDialog.setTitle("Alert");
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
}
