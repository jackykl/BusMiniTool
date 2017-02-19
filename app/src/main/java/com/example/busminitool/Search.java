package com.example.busminitool;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Search extends Activity {

    private static final int[] busICON = new int[]{R.drawable.kmb_logo_icon, R.drawable.citybus_logo_icon, R.drawable.firstbus_logo_icon};
    static DBHelper SearchHelper;
    static ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    EditText inputSearch;
    List<Bus> busList = new ArrayList<Bus>();
    private ListView lv;
    private BusAdapter adapter;

    //Fetch  data from database method
    public static Cursor getBusINFOCursor() {
        SQLiteDatabase db = SearchHelper.getReadableDatabase();
        if(Bus.isLanguare()==true) {
            Cursor cursor = db.rawQuery(Prefix_Data_SQL.QUERY_BUS_BASIC_INFO_TC_SQL, null);
            return cursor;
        }else{
            Cursor cursor = db.rawQuery(Prefix_Data_SQL.QUERY_BUS_BASIC_INFO_EN_SQL, null);
            return cursor;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
        if (Bus.isLanguare() == true) {
            //Chinese UI Language Config
            actionBar.setTitle("輸入路線搜尋及旅程時間");
            inputSearch.setHint("請輸入巴士路線...");
        } else {
            //English UI Language Config
            actionBar.setTitle("Input Bus No & Journey Tracker");
            inputSearch.setHint("Please enter the bus number...");
        }
        openDatabase();  // Call Database Connection method call
        QueryBUSINFO(); // RUN fetch bus  data SQL method call
        closeDatabase(); //Close Database Connection method call

        //Customise Listview UI Design Adapter
        adapter = new BusAdapter(busList, this);
        lv.setAdapter(adapter);

        //Clicking the TextViev From ListView Event method call
        onClickListviewEvent();

        //Bus Search EditText for search
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                // When user changed the Text
                if (arg1 < arg2) {
                    // We're deleting char so we need to reset the adapter data
                    adapter.resetData();
                }
                adapter.getFilter().filter(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        lv.setTextFilterEnabled(true);
    }

    // Call Database Connection method
    private void openDatabase() {
        SearchHelper = new DBHelper(this);
    }

    // RUN Related SQL method
    private void closeDatabase() {
        SearchHelper.close();
    }

    // RUN fetch bus  data SQL method
    public void QueryBUSINFO() {

        Cursor cursor = getBusINFOCursor();

        if (cursor != null) {

            if (cursor.moveToFirst()) {
                do {


                    String busNUM = cursor.getString(1);
                    String busINFO = cursor.getString(2);


                    Log.d("test", busNUM);
                    if (busNUM.indexOf("九巴") != -1 || busNUM.indexOf("KMB") != -1) {
                        if (Bus.isLanguare() == true) {
                            busList.add(new Bus(busNUM, "往 " + busINFO, busICON[0]));
                        } else {
                            busList.add(new Bus(busNUM, "To " + busINFO, busICON[0]));
                        }
                    } else if (busNUM.indexOf("城巴") != -1 || busNUM.indexOf("CITYBUS") != -1) {
                        if (Bus.isLanguare() == true) {
                            busList.add(new Bus(busNUM, "往 " + busINFO, busICON[1]));
                        } else {
                            busList.add(new Bus(busNUM, "To " + busINFO, busICON[1]));
                        }
                    } else if (busNUM.indexOf("新巴") != -1 || busNUM.indexOf("FIRST BUS") != -1) {
                        if (Bus.isLanguare() == true) {
                            busList.add(new Bus(busNUM, "往 " + busINFO, busICON[2]));
                        } else {
                            busList.add(new Bus(busNUM, "To " + busINFO, busICON[2]));
                        }
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();

        }

    }

    //Clicking the TextViev From ListView Event method
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
                TextView Direction = (TextView) RL1.getChildAt(0);
                final String busCode = busNum.getText().toString();
                final String busDirection = Direction.getText().toString();
                // Log.d("LoginStatus", Boolean.toString(Logined));
                Log.d("BusNumber", busCode);
                Log.d("Direction", busDirection);
                Intent intent1 = new Intent(getApplication(), BusDetail.class);
                intent1.putExtra("busNum", busCode);
                intent1.putExtra("Direction", busDirection);
                startActivity(intent1);

				/*
                 * Intent intent1 = new
				 * Intent(getApplication(),ChatActivity.class);
				 * //intent1.putExtra("", value); startActivity(intent1);
				 */

            }

        };
        lv.setOnItemClickListener(itemClickListener);
    }

    //ActionBar back button method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
              /*Intent i= new Intent(getApplication(), MainActivity.class);
              i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(i);*/
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
