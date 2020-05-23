package com.example.edensight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.DetailsViewHolder> {
    private List<HistoryDetails> historyDetails;
    private Context context;

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        TextView date, avgTemp, avgEcg, avgBp, avgBloodSugar;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.history_date);
            avgTemp = itemView.findViewById(R.id.history_temp);
            avgEcg = itemView.findViewById(R.id.history_ecg);
            avgBp = itemView.findViewById(R.id.history_bp);
            avgBloodSugar = itemView.findViewById(R.id.history_blood_sugar);
        }
    }

    HistoryAdapter(List<HistoryDetails> detailsList, Context context){
        this.historyDetails = detailsList;
        this.context = context;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
