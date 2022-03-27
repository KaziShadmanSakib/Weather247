package com.example.weather247;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    String userLocation, currentTemperature, currentWeatherStatus, currentWeatherIcon;
    TextView userLocationTv;
    FusedLocationProviderClient fusedLocationProviderClient;
    TextView currentTemperatureTv, currentWeatherStatusTv;
    ImageView currentWeatherIconIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //LocationController() takes the Location from the user device
        LocationController();

        //weather API controller controls the API and fetches data from it
        WeatherApiController weatherApiController = new WeatherApiController(this);
        weatherApiController.getJsonData(this);

        //sets and displays all the home basic weather info
        homeWeatherInformation();

    }

    //sets and displays all the home basic weather info
    public void homeWeatherInformation(){

        currentTemperatureTv = findViewById(R.id.temperatureValue);
        currentWeatherStatusTv = findViewById(R.id.weatherStatus);
        currentWeatherIconIv = findViewById(R.id.weatherIcon);

        currentTemperature = Cache.loadCurrentTemperature(this);
        currentWeatherStatus = Cache.loadCurrentCondition(this);
        currentWeatherIcon = Cache.loadCurrentIcon(this);

        currentTemperatureTv.setText(currentTemperature);
        currentWeatherStatusTv.setText(currentWeatherStatus);

        Log.i("activity", currentTemperature);

        Picasso.get().load(currentWeatherIcon).into(currentWeatherIconIv);
    }

    //LocationController() takes the Location from the user device
    public void LocationController() {

        userLocationTv = findViewById(R.id.location);

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
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
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
                        userLocationTv.setText(userLocation);


                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
                if(location==null){
                    //make a toast
                    Toast.makeText(MainActivity.this, "Please turn on your location to get updated information!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}