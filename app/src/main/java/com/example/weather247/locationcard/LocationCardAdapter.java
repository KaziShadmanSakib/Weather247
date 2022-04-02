package com.example.weather247.locationcard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weather247.R;


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
        holder.searchedLocationTV.setText(model.getSearchedLocation());
        holder.dateAddedTV.setText(model.getDateAdded());
        //TODO: send location to API controller
        // waiting for Kabbo vai to implement searched location data request
        holder.locationHolderLayout.setOnClickListener(view -> {
            Toast.makeText(context, holder.searchedLocationTV.getText(), Toast.LENGTH_LONG).show();
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
