package com.example.weather247;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather247.locationcard.LocationCardAdapter;
import com.example.weather247.locationcard.LocationCardModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//Todo 1 add lighting pic
//Todo 2 temperature er text ta ektu upore home er
//Todo 3 home background darker

public class MainActivity extends AppCompatActivity implements  VolleyListener {

    private WeatherApiController weatherApiController;
    private String userLocation, currentTemperature, currentWeatherStatus, currentWeatherIcon;
    private TextView userLocationTv;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView currentTemperatureTv, currentWeatherStatusTv, currentTemperatureUnit;
    private ImageView currentWeatherIconIv;
    private EditText searchLocationBar;
    private ImageButton settingsButton;
    private RecyclerView locationRecyclerView;
    private final ArrayList<LocationCardModel> locationCardCollection = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    private final LocationCardAdapter locationCardAdapter = new LocationCardAdapter(this, locationCardCollection);
    private final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


    //for swipe left/right
    private static final int MIN_DISTANCE = 140;
    private GestureDetector gestureDetector;

    private String urlResponse;
    private String lastSavedData;

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //weather API controller controls the API and fetches data from it
        weatherApiController = new WeatherApiController(this);

        //detect swipes when user swipes layouts
        gestureDetector = new GestureDetector(this, new GestureListener());

        //Initialize all the UI components
        setUiComponents();


        //if there is no internet connection, get data from cache file
        if(!(isConnectedToInternet(this))){

            lastSavedData = readSavedData();
            weatherApiController.getSavedJsonData(this, lastSavedData);

        }

        //else there is internet connection, so detect user location and get data
        else{

            //LocationController() takes the Location from the user device
            locationController();
            weatherApiController.getJsonData();

        }
    }

    public boolean isConnectedToInternet(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            // connected to the mobile provider's data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            }

            else return activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        }

        else {
            return false;
        }
    }

    private String readSavedData(){

        File file = new File(this.getFilesDir(), "cache");

        try {

            File gpxfile = new File(file, "lastSavedData");
            FileReader fileReader = new FileReader(gpxfile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            urlResponse = bufferedReader.readLine();
            bufferedReader.close();
            //fileReader.close();

            Log.i("activity", urlResponse);

        }
        catch (IOException e) {
            Log.e("Exception", "File read failed: " + e);
        }
        return urlResponse;
    }

    @Override
    public void requestFinished() {
        //sets and displays all the home basic weather info
        setHomeInformation();
    }

    @Override
    public void searchRequestFinished() {
        String region = DataController.getRegion();
        String country = DataController.getCountry();
        String searchedLocation = region + ", " + country;
        userLocationTv.setText(searchedLocation);
        Cache.saveUserLocation(MainActivity.this, searchedLocation);

        LocalDateTime currentTime = LocalDateTime.now();
        String dateAdded = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(currentTime);
        locationCardCollection.add(0, new LocationCardModel(region, country, dateAdded));
        locationCardAdapter.notifyItemInserted(0);
        while (locationCardCollection.size() > 5) {
            locationCardCollection.remove(locationCardCollection.size() - 1);
            locationCardAdapter.notifyItemRemoved(locationCardCollection.size());
        }
        if (locationCardCollection.size() > 0) {
            TextView recentlySearchTV = findViewById(R.id.recentlySearched);
            recentlySearchTV.setText(R.string.recently_searched);
        }

        //sets and displays all the home basic weather info of searched location
        setHomeInformation();

    }

    @Override
    public void invalidSearchRequestFinished() {
        Toast.makeText(this, "No matching location found.", Toast.LENGTH_LONG).show();
    }

    private int getTimeInInteger(String time){

        String[] timeParts = time.split(":");

        return (Integer.parseInt(timeParts[0]) * 60) + Integer.parseInt(timeParts[1]);

    }

    private void setUiComponents() {
        userLocationTv = findViewById(R.id.location);

        currentTemperatureTv = findViewById(R.id.temperatureValue);
        currentTemperatureUnit = findViewById(R.id.temperatureUnit);
        currentWeatherStatusTv = findViewById(R.id.weatherStatus);
        currentWeatherIconIv = findViewById(R.id.weatherIcon);

        locationRecyclerView = findViewById(R.id.recycler_view);
        locationRecyclerView.setAdapter(locationCardAdapter);
        locationRecyclerView.setLayoutManager(linearLayoutManager);

        searchLocationBar = findViewById(R.id.searchLocationBar);
        searchLocationBar.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String searchedLocation = searchLocationBar.getText().toString();
                searchLocationBar.getText().clear();
                weatherApiController.getJsonData(searchedLocation);
            }
            return false;
        });

        settingsButton = findViewById(R.id.settingsIcon);
        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        });
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            weatherApiController.getJsonData();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    //sets and displays all the home basic weather info
    public void setHomeInformation(){
        userLocation = DataController.getRegion() + ", " + DataController.getCountry();
        currentTemperature = DataController.getCurrentTemperatureHome();
        currentWeatherStatus = DataController.getCurrentCondition();
        currentWeatherIcon = DataController.getCurrentIcon();

        Date date = new Date();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("kk:mm", Locale.getDefault());
        String nowTime = dateFormat.format(date);

        String currentSunrise = DataController.getCurrentSunrise().substring(0, 5);
        String currentSunset = DataController.getCurrentSunset().substring(0, 5);

        int nowTimeInt = getTimeInInteger(nowTime);
        int currentSunriseInt = getTimeInInteger(currentSunrise);
        int currentSunsetInt = getTimeInInteger(currentSunset);
        currentSunsetInt = currentSunsetInt + 720;

        if(nowTimeInt>= currentSunriseInt && nowTimeInt < currentSunsetInt){

            swipeRefreshLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.day_background));

        }

        else {
            swipeRefreshLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.night_background));
        }

        userLocationTv.setText(userLocation);
        currentTemperatureTv.setText(currentTemperature);
        currentWeatherStatusTv.setText(currentWeatherStatus);

        if(DataController.getTemperatureUnit() == "Celsius" ){
            currentTemperatureUnit.setText("°C");
        }
        else {
            currentTemperatureUnit.setText("°F");
        }

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
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            if (motionEvent.getX() - motionEvent1.getX() > MIN_DISTANCE) {
                startActivity(new Intent(MainActivity.this, CurrentWeather.class));
                MainActivity.this.overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
            else if (motionEvent1.getX() - motionEvent.getX() > MIN_DISTANCE) {
                startActivity(new Intent(MainActivity.this, WeatherPrediction.class));
                MainActivity.this.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anime_slide_out_right);
            }
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