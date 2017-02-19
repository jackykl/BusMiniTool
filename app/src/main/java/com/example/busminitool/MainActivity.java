package com.example.busminitool;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private SearchView mSearchView;
    private TextView mStatusView;

    private CharSequence mTitle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        if (Bus.isLanguare() == true) {
            //Chinese UI Language Config
            actionBar.setTitle("¥D­¶");
            ImageView menu = (ImageView) findViewById(R.id.MenuImageView);
            menu.setImageResource(R.drawable.bus_menu);
        } else {
            //English UI Language Config
            actionBar.setTitle("Main");
            ImageView menu = (ImageView) findViewById(R.id.MenuImageView);
            menu.setImageResource(R.drawable.bus_menu_en);
        }

        //Four function imageButton (P2P Search, NearBy, JourneyTracker, InputBusNo)
        ImageButton BTN_INPUT_BUS_NO = (ImageButton) findViewById(R.id.BTNInputBusNo);
        ImageButton BTN_Journey_Tracker = (ImageButton) findViewById(R.id.BTNJourneyTracker);
        ImageButton BTN_BtnP2P = (ImageButton) findViewById(R.id.BtnP2P);
        ImageButton BTN_BTNNearBy = (ImageButton) findViewById(R.id.BTNNearBy);

        //Input Bus No search onClick intent Event
        BTN_INPUT_BUS_NO.setOnClickListener(new ImageButton.OnClickListener() {

            public void onClick(View v) {
                Log.d("test", "test");
                Intent intent = new Intent();
                intent.setClass(getApplication(), Search.class);
                startActivity(intent);

            }
        });

        //Journey Tracker  onClick intent Event
        BTN_Journey_Tracker.setOnClickListener(new ImageButton.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplication(), Search.class);
                startActivity(intent);

            }
        });

        //Journey Tracker  onClick intent Event
        BTN_BtnP2P.setOnClickListener(new ImageButton.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplication(), P2PSearch.class);
                startActivity(intent);

            }
        });

        //Near by onClick intentEvent
        BTN_BTNNearBy.setOnClickListener(new ImageButton.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplication(), nearBy.class);
                startActivity(intent);

            }
        });

    }

    //Actionbar Create Language Menu method
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Actionbar Language Menu config method
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_languareEN:
                Bus.setLanguare(false);
                Intent changeLang = new Intent();
                changeLang.setClass(getApplication(), MainActivity.class);
                changeLang.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(changeLang);
                return true;
            case R.id.action_languareTC:
                Bus.setLanguare(true);
                Intent changeLang2 = new Intent();
                changeLang2.setClass(getApplication(), MainActivity.class);
                changeLang2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(changeLang2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
