package com.example.weather247.ui.cards.locationcard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather247.data.DataController;
import com.example.weather247.R;
import com.example.weather247.data.WeatherApiController;


import java.util.ArrayList;

public class LocationCardAdapter extends RecyclerView.Adapter<LocationCardAdapter.Viewholder> {
    private final Context context;
    private final ArrayList<LocationCardModel> locationCardCollection;

    public LocationCardAdapter(Context context, ArrayList<LocationCardModel> locationCardCollection) {
        this.context = context;
        this.locationCardCollection = locationCardCollection;
    }

    @NonNull
    @Override
    public LocationCardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_card, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationCardAdapter.Viewholder holder, int position) {
        LocationCardModel model = locationCardCollection.get(position);
        String region = model.getRegion();
        String country = model.getCountry();
        String searchedLocation = region + ", " + country;
        String dateAdded = model.getDateAdded() + ", Local Time in " + region + " : " + DataController.getCurrentTimeRegion();
        holder.searchedLocationTV.setText(searchedLocation);
        holder.dateAddedTV.setText(dateAdded);
        holder.locationHolderLayout.setOnClickListener(view -> {
            WeatherApiController weatherApiController = new WeatherApiController(context);
            weatherApiController.getJsonData(region);
        });
    }

    @Override
    public int getItemCount() {
        return locationCardCollection.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        private final ConstraintLayout locationHolderLayout;
        private final TextView searchedLocationTV;
        private final TextView dateAddedTV;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            locationHolderLayout = itemView.findViewById(R.id.locationHolderLayout);
            searchedLocationTV = itemView.findViewById(R.id.predictionDay);
            dateAddedTV = itemView.findViewById(R.id.predictionDate);
        }
    }

}
