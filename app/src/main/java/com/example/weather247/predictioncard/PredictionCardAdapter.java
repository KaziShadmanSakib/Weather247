package com.example.weather247.predictioncard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import com.example.weather247.R;

import java.util.ArrayList;

public class PredictionCardAdapter extends RecyclerView.Adapter<PredictionCardAdapter.Viewholder> {

    private Context context;
    private ArrayList<PredictionCardModel> predictionCardCollection;

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
        holder.maxTempHolder.setText(model.getMaxTemp());
        holder.minTempHolder.setText(model.getMinTemp());
        holder.avgTempHolder.setText(model.getAvgTemp());
        holder.humidityHolder.setText(model.getHumidity());
        holder.chanceOfRainHolder.setText(model.getChanceOfRain());
        holder.chanceOfSnowHolder.setText(model.getChanceOfSnow());
        holder.predictionHolderLayout.setOnClickListener(view -> {
            if (holder.collapsibleLayout.getVisibility() == View.GONE) {
                TransitionManager.beginDelayedTransition(holder.predictionHolderLayout, new AutoTransition());
                holder.collapsibleLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return predictionCardCollection.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private final LinearLayout predictionHolderLayout;
        private final LinearLayout collapsibleLayout;
        private final TextView dayHolder;
        private final TextView dateHolder;
        private final TextView maxTempHolder;
        private final TextView minTempHolder;
        private final TextView avgTempHolder;
        private final TextView humidityHolder;
        private final TextView chanceOfRainHolder;
        private final TextView chanceOfSnowHolder;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            predictionHolderLayout = itemView.findViewById(R.id.predictionHolderLayout);
            collapsibleLayout = itemView.findViewById(R.id.collapsibleLayout);
            dayHolder = itemView.findViewById(R.id.predictionDay);
            dateHolder = itemView.findViewById(R.id.predictionDate);
            maxTempHolder = itemView.findViewById(R.id.predictedMaxTemp);
            minTempHolder = itemView.findViewById(R.id.predictedMinTemp);
            avgTempHolder = itemView.findViewById(R.id.predictedAvgTemp);
            humidityHolder = itemView.findViewById(R.id.predictedHumidity);
            chanceOfRainHolder = itemView.findViewById(R.id.predictedChanceOfRain);
            chanceOfSnowHolder = itemView.findViewById(R.id.predictedChanceOfSnow);
        }
    }
}
