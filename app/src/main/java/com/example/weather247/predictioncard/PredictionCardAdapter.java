package com.example.weather247.predictioncard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.weather247.R;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PredictionCardAdapter extends RecyclerView.Adapter<PredictionCardAdapter.Viewholder> {

    private final Context context;
    private final ArrayList<PredictionCardModel> predictionCardCollection;

    public PredictionCardAdapter(Context context, ArrayList<PredictionCardModel> predictionCardCollection) {
        this.context = context;
        this.predictionCardCollection = predictionCardCollection;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prediction_card, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        PredictionCardModel model = predictionCardCollection.get(position);
        holder.dayHolder.setText(model.getDay());
        holder.dateHolder.setText(model.getDate());
        holder.statusHolder.setText(model.getStatus());
        holder.maxTempHolder.setText(model.getMaxTemp());
        holder.minTempHolder.setText(model.getMinTemp());
        holder.avgTempHolder.setText(model.getAvgTemp());
        holder.humidityHolder.setText(model.getHumidity());
        holder.chanceOfRainHolder.setText(model.getChanceOfRain());
        holder.chanceOfSnowHolder.setText(model.getChanceOfSnow());
        holder.baseCard.setOnClickListener(view -> {
            if (holder.collapsibleLayout.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.baseCard, new AutoTransition());
                holder.collapsibleLayout.setVisibility(View.VISIBLE);
            }
            else {
                TransitionManager.beginDelayedTransition(holder.baseCard, new AutoTransition());
                holder.collapsibleLayout.setVisibility(View.GONE);
            }
        });
        Picasso.get().load(model.getIconPath()).into(holder.iconHolder);
    }

    @Override
    public int getItemCount() {
        return predictionCardCollection.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        private final MaterialCardView baseCard;
        private final LinearLayout collapsibleLayout;
        private final TextView dayHolder;
        private final TextView dateHolder;
        private final ImageView iconHolder;
        private final TextView statusHolder;

        private final TextView maxTempHolder;
        private final TextView minTempHolder;
        private final TextView avgTempHolder;
        private final TextView humidityHolder;
        private final TextView chanceOfRainHolder;
        private final TextView chanceOfSnowHolder;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            baseCard = itemView.findViewById(R.id.base_card);
            collapsibleLayout = itemView.findViewById(R.id.collapsibleLayout);
            dayHolder = itemView.findViewById(R.id.predictionDay);
            dateHolder = itemView.findViewById(R.id.predictionDate);
            iconHolder = itemView.findViewById(R.id.predictionIcon);
            statusHolder = itemView.findViewById(R.id.predictionStatus);
            maxTempHolder = itemView.findViewById(R.id.predictedMaxTemp);
            minTempHolder = itemView.findViewById(R.id.predictedMinTemp);
            avgTempHolder = itemView.findViewById(R.id.predictedAvgTemp);
            humidityHolder = itemView.findViewById(R.id.predictedHumidity);
            chanceOfRainHolder = itemView.findViewById(R.id.predictedChanceOfRain);
            chanceOfSnowHolder = itemView.findViewById(R.id.predictedChanceOfSnow);
        }
    }
}
