package com.example.busminitool;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import static com.example.busminitool.DbConstants.*;


public class nearBy extends Activity
        implements OnMapClickListener, OnMapLongClickListener, OnMarkerClickListener {
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 300;
    static DBHelper dbhelper;
    final int RQS_GooglePlayServices = 1;
    LocationManager locationManager;
    Location myLocation;
    TextView tvLocInfo;
    boolean markerClicked;
    PolylineOptions rectOptions;
    Polyline polyline;
    private GoogleMap myMap;

    public static Cursor getMapINFO() {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        if(Bus.isLanguare()==true) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + BusDetailInfo_TC, null);
            return cursor;
        }else{
            Cursor cursor = db.rawQuery("SELECT * FROM " + BusDetailInfo_EN, null);
            return cursor;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        if (Bus.isLanguare() == true) {
            //Chinese Ui Config
            actionBar.setTitle("鄰近巴士路線");
        } else {
            //English UI Config
            actionBar.setTitle("Nearby Bus Route");
        }
        LatLng point;
        actionBar.setDisplayShowTitleEnabled(true);
        tvLocInfo = (TextView) findViewById(R.id.locinfo);

        FragmentManager myFragmentManager = getFragmentManager();
        MapFragment myMapFragment
                = (MapFragment) myFragmentManager.findFragmentById(R.id.map);
        myMap = myMapFragment.getMap();
        if (myMap != null)
            myMap.setMyLocationEnabled(true);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );
        myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            point = new LatLng(latitude, longitude);
            CameraPosition cameraPosition;                   // Creates a CameraPosition from the builder
            cameraPosition = new CameraPosition.Builder()
                    .target(point)      // Sets the center of the map to Mountain View
                    .zoom(18)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(45)                   // Sets the tilt of the camera to 30 degrees
                    .build();

            myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            myMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                @Override
                public void onInfoWindowClick(Marker m) {
                    Intent jump2BusDetail = new Intent();
                    jump2BusDetail.setClass(getApplication(), BusDetail.class);
                    if (Bus.isLanguare() == true) {
                        jump2BusDetail.putExtra("busNum", m.getTitle().substring(0, m.getTitle().indexOf("往") - 1));
                        jump2BusDetail.putExtra("Direction", m.getTitle().substring(m.getTitle().indexOf("往")));
                    } else {
                        jump2BusDetail.putExtra("busNum", m.getTitle().substring(0, m.getTitle().indexOf("To") - 1));
                        jump2BusDetail.putExtra("Direction", m.getTitle().substring(m.getTitle().indexOf("To")));
                    }
                    startActivity(jump2BusDetail);
                    Log.d("nearBY", m.getTitle());
                    Log.d("nearBY", m.getSnippet());
                }

            });

        }
        markerClicked = false;

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

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode == ConnectionResult.SUCCESS) {
            openDatabase();
            getMap();
            closeDatabase();
        } else {
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
        }

    }

    @Override
    public void onMapClick(LatLng point) {
        tvLocInfo.setText(point.toString());
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        myMap.animateCamera(CameraUpdateFactory.newLatLng(point));
        markerClicked = false;
    }

    private void openDatabase() {
        dbhelper = new DBHelper(getApplication());
    }

    private void closeDatabase() {
        dbhelper.close();
    }

    public void getMap() {
        Cursor cursor = getMapINFO();
        String stationName;
        String stationNo;
        String busNO;
        String Direction;
        String Straddress;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    busNO = cursor.getString(1);
                    stationNo = cursor.getString(2);
                    stationName = cursor.getString(3);
                    Direction = cursor.getString(6);
                    Straddress = cursor.getString(7);
                    Log.d("address", Straddress);
                    Geocoder coder = new Geocoder(this);
                    List<Address> address;
                    try {
                        address = coder.getFromLocationName(Straddress, 5);
                        if (address == null) {
                            return;
                        }
                        Address location = address.get(0);

                        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());

                        if (busNO.indexOf("九巴") != -1 || busNO.indexOf("KMB") != -1) {
                            if (Bus.isLanguare() == true) {
                                myMap.addMarker(new MarkerOptions().position(point).title(busNO + " 往 " + Direction).snippet(stationNo + " - " + stationName + " 站")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.kmb_logo_icon)));
                            } else {
                                myMap.addMarker(new MarkerOptions().position(point).title(busNO + " To " + Direction).snippet("Station No: "+stationNo + " - " + stationName)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.kmb_logo_icon)));
                            }
                        } else if (busNO.indexOf("城巴") != -1 || busNO.indexOf("CITYBUS") != -1) {
                            if (Bus.isLanguare() == true) {
                                myMap.addMarker(new MarkerOptions().position(point).title(busNO + " 往 " + Direction).snippet(stationNo + " - " + stationName + " 站")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.citybus_logo_icon)));
                            } else {
                                myMap.addMarker(new MarkerOptions().position(point).title(busNO + " To " + Direction).snippet("Station No: "+stationNo + " - " + stationName)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.citybus_logo_icon)));
                            }
                        } else if (busNO.indexOf("新巴") != -1 || busNO.indexOf("FIRST BUS") != -1) {
                            if (Bus.isLanguare() == true) {
                                myMap.addMarker(new MarkerOptions().position(point).title(busNO + " 往 " + Direction).snippet(stationNo + " - " + stationName + " 站")
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.firstbus_logo_icon)));
                            } else {
                                myMap.addMarker(new MarkerOptions().position(point).title(busNO + " To " + Direction).snippet("Station No: "+stationNo + " - " + stationName)
                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.firstbus_logo_icon)));
                            }
                        }


                    } catch (Exception e) {

                    }


                } while (cursor.moveToNext());
            }
        }

    }

    @Override
    public boolean onMarkerClick(Marker m) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onMapLongClick(LatLng arg0) {
        // TODO Auto-generated method stub

    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle b) {

        }

        @Override
        public void onProviderDisabled(String s) {
        }

        @Override
        public void onProviderEnabled(String s) {

        }
    }
}