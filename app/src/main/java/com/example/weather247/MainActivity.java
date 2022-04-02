package com.example.weather247;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather247.locationcard.LocationCardAdapter;
import com.example.weather247.locationcard.LocationCardModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements  VolleyListener {

    private String userLocation, currentTemperature, currentWeatherStatus, currentWeatherIcon;
    private TextView userLocationTv;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView currentTemperatureTv, currentWeatherStatusTv;
    private ImageView currentWeatherIconIv;
    private EditText searchLocationBar;
    private RecyclerView locationRecyclerView;
    private ArrayList<LocationCardModel> locationCardCollection = new ArrayList<>();
    //TODO swipe to refresh yet to be added
    private SwipeRefreshLayout swipeRefreshLayout;

    private LocationCardAdapter locationCardAdapter = new LocationCardAdapter(this, locationCardCollection);
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


    //for swipe left/right
    private static final String TAG = "MainActivity";
    private static int MIN_DISTANCE = 100;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gestureDetector = new GestureDetector(this, new GestureListener());

        //Initialize all the UI components
        setUiComponents();

        //LocationController() takes the Location from the user device
        locationController();

        //weather API controller controls the API and fetches data from it
        WeatherApiController weatherApiController = new WeatherApiController(this);
        weatherApiController.getJsonData(this);
    }

    @Override
    public void requestFinished() {
        //sets and displays all the home basic weather info
        setHomeInformation();
    }

    private void setUiComponents() {
        userLocationTv = findViewById(R.id.location);

        currentTemperatureTv = findViewById(R.id.temperatureValue);
        currentWeatherStatusTv = findViewById(R.id.weatherStatus);
        currentWeatherIconIv = findViewById(R.id.weatherIcon);

        locationRecyclerView = findViewById(R.id.recycler_view);
        locationCardAdapter = new LocationCardAdapter(this, locationCardCollection);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        locationRecyclerView.setAdapter(locationCardAdapter);
        locationRecyclerView.setLayoutManager(linearLayoutManager);

        searchLocationBar = findViewById(R.id.searchLocationBar);
        searchLocationBar.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String searchedLocation = searchLocationBar.getText().toString();
                searchLocationBar.getText().clear();
                //TODO: make an API call with searched location, check validity of the location and then do the following
                LocalDateTime currentTime = LocalDateTime.now();
                String dateAdded = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(currentTime);
                locationCardCollection.add(0, new LocationCardModel(searchedLocation, dateAdded));
                locationCardAdapter.notifyItemInserted(0);
                while (locationCardCollection.size() > 5) {
                    locationCardCollection.remove(locationCardCollection.size() - 1);
                    locationCardAdapter.notifyItemRemoved(locationCardCollection.size());
                }
                if (locationCardCollection.size() > 0) {
                    TextView recentlySearchTV = findViewById(R.id.recentlySearched);
                    recentlySearchTV.setText("Recently searched");
                }
            }
            return false;
        });
    }

    //sets and displays all the home basic weather info
    public void setHomeInformation(){
        currentTemperature = DataController.getCurrentTemperature();
        currentWeatherStatus = DataController.getCurrentCondition();
        currentWeatherIcon = DataController.getCurrentIcon();

        currentTemperatureTv.setText(currentTemperature);
        currentWeatherStatusTv.setText(currentWeatherStatus);

        Picasso.get().load(currentWeatherIcon).into(currentWeatherIconIv);
    }

    //locationController() takes the Location from the user device
    public void locationController() {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //check permission
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission granted
            getLocation();
        } else {
            //when permission denied
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    private void getLocation() {

        //i did not do this if part
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //started from here
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            //initialize location
            Location location = task.getResult();
            if(location!=null){
                try {
                    //initialize geoCoder
                    Geocoder geocoder = new Geocoder(MainActivity.this,
                            Locale.getDefault());
                    //initialize address list
                    List<Address> addresses = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1);

                    userLocation = addresses.get(0).getLocality();
                    Cache.saveUserLocation(MainActivity.this, userLocation);
                    Log.i("Location", "getLocation: " + userLocation);
                    userLocationTv.setText(userLocation);


                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
            if(location==null){
                //make a toast
                Toast.makeText(MainActivity.this, "Please turn on your location to get updated information!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent motionEvent) {
            Log.i(TAG, "onDown: called");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            if (motionEvent.getX() - motionEvent1.getX() > MIN_DISTANCE)
                startActivity(new Intent(MainActivity.this, CurrentWeather.class));
            else if (motionEvent1.getX() - motionEvent.getX() > MIN_DISTANCE)
                startActivity(new Intent(MainActivity.this, WeatherPrediction.class));
            return super.onFling(motionEvent, motionEvent1, v, v1);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        super.dispatchTouchEvent(ev);
        return gestureDetector.onTouchEvent(ev);
    }
}