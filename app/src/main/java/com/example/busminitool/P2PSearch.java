package com.example.busminitool;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class P2PSearch extends Activity {
    final Bus bus = new Bus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_p2_psearch);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        if (Bus.isLanguare() == true) {
            //Chinese UI Languare Config
            actionBar.setTitle("ÂI¹ïÂI¸ô½u·j´M");
            spanner_onLoad(Bus.isLanguare());
            intentSearch(Bus.isLanguare());
        } else {
            //English UI Languare Config
            actionBar.setTitle("Point To Point Search");
            spanner_onLoad(Bus.isLanguare());
            intentSearch(Bus.isLanguare());
        }


    }

    //ActionBar back button method
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish(); //finish() is the build in kill current activity API
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Load point to point search spanner method
    public void spanner_onLoad(boolean languare) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        TextView OrignTV = (TextView) findViewById(R.id.OrignTV);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        TextView DistTV = (TextView) findViewById(R.id.DistTV);
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        final Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        TextView buttonTV = (TextView) findViewById(R.id.buttonTV);
        ArrayAdapter<CharSequence> DistrictAdapter = null;
        ArrayAdapter<CharSequence> KLNCity_Area_Adapter = null;
        ArrayAdapter<CharSequence> CENTRAL_Area_Adapter = null;
        ArrayAdapter<CharSequence> SSP_Area_Adapter = null;
        ArrayAdapter<CharSequence> WTS_Area_Adapter = null;
        ArrayAdapter<CharSequence> YTM_Area_Adapter = null;
        ArrayAdapter<CharSequence> HKS_Area_Adapter = null;
        if (languare == true) {
            //Chinese UI SpannerConfig
            OrignTV.setText("¨BÆJ¤@:½Ð¿ï¾Ü©Ò¦b¦a");
            OrignTV.setTypeface(null, Typeface.BOLD);
            OrignTV.setTextColor(Color.parseColor("#FFFFFF"));
            DistTV.setText("¨BÆJ¤G:½Ð¿ï¾Ü¥Øªº¦a");
            DistTV.setTypeface(null, Typeface.BOLD);
            DistTV.setTextColor(Color.parseColor("#FFFFFF"));
            buttonTV.setText("¨BÆJ¤T:½Ð«öSearchÁäÄ~Äò");
            buttonTV.setTypeface(null, Typeface.BOLD);
            buttonTV.setTextColor(Color.parseColor("#FFFFFF"));
            DistrictAdapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_District_TC, android.R.layout.simple_spinner_item);
            KLNCity_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_KLNCity_Area_TC, android.R.layout.simple_spinner_item);
            CENTRAL_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_CENTRAL_Area_TC, android.R.layout.simple_spinner_item);
            SSP_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_SSP_Area_TC, android.R.layout.simple_spinner_item);
            WTS_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_WTS_Area_TC, android.R.layout.simple_spinner_item);
            YTM_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_YTM_Area_TC, android.R.layout.simple_spinner_item);
            HKS_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_HKS_Area_TC, android.R.layout.simple_spinner_item);
        } else {
            //English UI SpannerConfig
            OrignTV.setText("Step1: Please choose origin");
            OrignTV.setTypeface(null, Typeface.BOLD);
            OrignTV.setTextColor(Color.parseColor("#FFFFFF"));
            DistTV.setText("Step2: Please choose destination");
            DistTV.setTypeface(null, Typeface.BOLD);
            DistTV.setTextColor(Color.parseColor("#FFFFFF"));
            buttonTV.setText("Step3:  Press search to process");
            buttonTV.setTypeface(null, Typeface.BOLD);
            buttonTV.setTextColor(Color.parseColor("#FFFFFF"));
            DistrictAdapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_District_EN, android.R.layout.simple_spinner_item);
            KLNCity_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_KLNCity_Area_EN, android.R.layout.simple_spinner_item);
            CENTRAL_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_CENTRAL_Area_EN, android.R.layout.simple_spinner_item);
            SSP_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_SSP_Area_EN, android.R.layout.simple_spinner_item);
            WTS_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_WTS_Area_EN, android.R.layout.simple_spinner_item);
            YTM_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_YTM_Area_EN, android.R.layout.simple_spinner_item);
            HKS_Area_Adapter = ArrayAdapter.createFromResource(this,
                    R.array.list_of_HKS_Area_EN, android.R.layout.simple_spinner_item);
        }


        DistrictAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        KLNCity_Area_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        HKS_Area_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CENTRAL_Area_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SSP_Area_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        WTS_Area_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        YTM_Area_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(DistrictAdapter);
        final ArrayAdapter<CharSequence> finalWTS_Area_Adapter = WTS_Area_Adapter;
        final ArrayAdapter<CharSequence> finalSSP_Area_Adapter = SSP_Area_Adapter;
        final ArrayAdapter<CharSequence> finalCENTRAL_Area_Adapter = CENTRAL_Area_Adapter;
        final ArrayAdapter<CharSequence> finalKLNCity_Area_Adapter = KLNCity_Area_Adapter;
        final ArrayAdapter<CharSequence> finalHKS_Area_Adapter = HKS_Area_Adapter;
        final ArrayAdapter<CharSequence> finalYTM_Area_Adapter = YTM_Area_Adapter;
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> OrignDistadapterView, View view, int position, long id) {
                Log.d("P2PSearch", "You have selected DestDistrict: " + OrignDistadapterView.getSelectedItem().toString());

                if (OrignDistadapterView.getSelectedItem().toString().equalsIgnoreCase("KOWLOON CITY") || OrignDistadapterView.getSelectedItem().toString().equals("¤EÀs«°")) {

                    bus.setOrignDist(OrignDistadapterView.getSelectedItem().toString());
                    spinner2.setAdapter(finalKLNCity_Area_Adapter);
                    spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> OrignAreaadapterView, View view, int position, long id) {
                            bus.setOrignArea(OrignAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected OrignArea: " + OrignAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }

                if (OrignDistadapterView.getSelectedItem().toString().equalsIgnoreCase("CENTRAL WESTERN") || OrignDistadapterView.getSelectedItem().toString().equals("¤¤¦è°Ï")) {
                    bus.setOrignDist(OrignDistadapterView.getSelectedItem().toString());
                    spinner2.setAdapter(finalCENTRAL_Area_Adapter);
                    spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> OrignAreaadapterView, View view, int position, long id) {
                            bus.setOrignArea(OrignAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected OrignArea: " + OrignAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }

                Log.d("P2PSearch", Integer.toString(OrignDistadapterView.getSelectedItem().toString().indexOf("²`¤ô–õ")));

                if (OrignDistadapterView.getSelectedItem().toString().equalsIgnoreCase("SHAM SHUI PO") || OrignDistadapterView.getSelectedItem().toString().indexOf("²`¤ô¨B") != -1) {
                    bus.setOrignDist(OrignDistadapterView.getSelectedItem().toString());

                    spinner2.setAdapter(finalSSP_Area_Adapter);
                    spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> OrignAreaadapterView, View view, int position, long id) {
                            bus.setOrignArea(OrignAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected OrignArea: " + OrignAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
                if (OrignDistadapterView.getSelectedItem().toString().equalsIgnoreCase("Wong Tai Sin") || OrignDistadapterView.getSelectedItem().toString().equals("¶À¤j¥P")) {
                    bus.setOrignDist(OrignDistadapterView.getSelectedItem().toString());
                    spinner2.setAdapter(finalWTS_Area_Adapter);
                    spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> OrignAreaadapterView, View view, int position, long id) {
                            bus.setOrignArea(OrignAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected OrignArea: " + OrignAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
                if (OrignDistadapterView.getSelectedItem().toString().equalsIgnoreCase("SOUTHERN H.K. ISLAND") || OrignDistadapterView.getSelectedItem().toString().indexOf("´ä®q«n°Ï") != -1) {
                    bus.setOrignDist(OrignDistadapterView.getSelectedItem().toString());

                    spinner2.setAdapter(finalHKS_Area_Adapter);
                    spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> OrignAreaadapterView, View view, int position, long id) {
                            bus.setOrignArea(OrignAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected OrignArea: " + OrignAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
                if (OrignDistadapterView.getSelectedItem().toString().equalsIgnoreCase("YAU TSIM MONG") || OrignDistadapterView.getSelectedItem().toString().equals("ªo¦y©ô")) {
                    bus.setOrignDist(OrignDistadapterView.getSelectedItem().toString());
                    spinner2.setAdapter(finalYTM_Area_Adapter);
                    spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> OrignAreaadapterView, View view, int position, long id) {
                            bus.setOrignArea(OrignAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected OrignArea: " + OrignAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }


            }

            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });
        spinner3.setAdapter(DistrictAdapter);
        final ArrayAdapter<CharSequence> finalYTM_Area_Adapter1 = YTM_Area_Adapter;
        final ArrayAdapter<CharSequence> finalCENTRAL_Area_Adapter1 = CENTRAL_Area_Adapter;
        final ArrayAdapter<CharSequence> finalSSP_Area_Adapter1 = SSP_Area_Adapter;
        final ArrayAdapter<CharSequence> finalWTS_Area_Adapter1 = WTS_Area_Adapter;
        final ArrayAdapter<CharSequence> finalHKS_Area_Adapter1 = HKS_Area_Adapter;
        spinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> DestnDistadapterView, View view, int position, long id) {
                Log.d("P2PSearch", "You have selected DestDistrict: " + DestnDistadapterView.getSelectedItem().toString());
                if (DestnDistadapterView.getSelectedItem().toString().equalsIgnoreCase("KOWLOON CITY") || DestnDistadapterView.getSelectedItem().toString().equals("¤EÀs«°")) {
                    bus.setDestDist(DestnDistadapterView.getSelectedItem().toString());
                    spinner4.setAdapter(finalYTM_Area_Adapter1);
                    spinner4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> DestAreaadapterView, View view, int position, long id) {
                            bus.setDestArea(DestAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected DestArea: " + DestAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });

                }
                if (DestnDistadapterView.getSelectedItem().toString().equalsIgnoreCase("CENTRAL WESTERN") || DestnDistadapterView.getSelectedItem().toString().equals("¤¤¦è°Ï")) {
                    bus.setDestDist(DestnDistadapterView.getSelectedItem().toString());
                    spinner4.setAdapter(finalCENTRAL_Area_Adapter1);
                    spinner4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> DestAreaadapterView, View view, int position, long id) {
                            bus.setDestArea(DestAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected DestArea: " + DestAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
                if (DestnDistadapterView.getSelectedItem().toString().equalsIgnoreCase("SHAM SHUI PO") || DestnDistadapterView.getSelectedItem().toString().equals("²`¤ô¨B")) {
                    bus.setDestDist(DestnDistadapterView.getSelectedItem().toString());
                    spinner4.setAdapter(finalSSP_Area_Adapter1);
                    spinner4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> DestAreaadapterView, View view, int position, long id) {
                            bus.setDestArea(DestAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected DestArea: " + DestAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
                if (DestnDistadapterView.getSelectedItem().toString().equalsIgnoreCase("Wong Tai Sin") || DestnDistadapterView.getSelectedItem().toString().equals("¶À¤j¥P")) {
                    bus.setDestDist(DestnDistadapterView.getSelectedItem().toString());
                    spinner4.setAdapter(finalWTS_Area_Adapter1);
                    spinner4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> DestAreaadapterView, View view, int position, long id) {
                            bus.setDestArea(DestAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected DestArea: " + DestAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }

                if (DestnDistadapterView.getSelectedItem().toString().equalsIgnoreCase("SOUTHERN H.K. ISLAND") || DestnDistadapterView.getSelectedItem().toString().equals("´ä®q«n°Ï")) {
                    bus.setDestDist(DestnDistadapterView.getSelectedItem().toString());
                    spinner4.setAdapter(finalHKS_Area_Adapter1);
                    spinner4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> DestAreaadapterView, View view, int position, long id) {
                            bus.setDestArea(DestAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected DestArea: " + DestAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
                if (DestnDistadapterView.getSelectedItem().toString().equalsIgnoreCase("YAU TSIM MONG") || DestnDistadapterView.getSelectedItem().toString().equals("ªo¦y©ô")) {
                    bus.setDestDist(DestnDistadapterView.getSelectedItem().toString());
                    spinner4.setAdapter(finalYTM_Area_Adapter1);
                    spinner4.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> DestAreaadapterView, View view, int position, long id) {
                            bus.setDestArea(DestAreaadapterView.getSelectedItem().toString());
                            Log.d("P2PSearch", "You have selected DestArea: " + DestAreaadapterView.getSelectedItem().toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });
    }

    //Intent Search function method
    public void intentSearch(boolean Languare) {
        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new Button.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("OrignArea", bus.getOrignArea());
                intent.putExtra("DestArea", bus.getDestArea());
                intent.setClass(getApplication(), P2PSearchResult.class);
                if (bus.getOrignArea() != null && bus.getDestArea() != null) {
                    startActivity(intent);
                    finish();

                } else if (bus.getOrignArea() == null && bus.getDestArea() != null) {
                    if (Bus.isLanguare() == true) {
                        ShowMsgDialog("½Ð¿ï¾Ü©Ò¦b¦a");
                    } else {
                        ShowMsgDialog("Please choose origin");
                    }
                } else if (bus.getDestArea() == null && bus.getOrignArea() != null) {
                    if (Bus.isLanguare() == true) {
                        ShowMsgDialog("½Ð¿ï¾Ü¥Øªº¦a");
                    } else {
                        ShowMsgDialog("Please choose destination");
                    }
                } else if (bus.getOrignArea() == null && bus.getDestArea() == null) {
                    if (Bus.isLanguare() == true) {
                        ShowMsgDialog("½Ð¿ï¾Ü©Ò¦b¦a¤Î¥Øªº¦a");
                    } else {
                        ShowMsgDialog("Please choose origin and destination");
                    }
                }

            }

        });
    }

    private void ShowMsgDialog(String Msg) {
        AlertDialog.Builder MyAlertDialog = new AlertDialog.Builder(this);
        if (Bus.isLanguare() == true) {
            MyAlertDialog.setTitle("´£¥Ü");
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
            MyAlertDialog.setNeutralButton("½T»{", OkClick);
        } else {
            MyAlertDialog.setNeutralButton("OK", OkClick);
        }
        MyAlertDialog.show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_p2_psearch,
                    container, false);
            return rootView;
        }
    }

}
