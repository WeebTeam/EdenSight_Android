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
        TextView date, avgTemp, avgHr, avgBp, avgBloodSugar;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.history_date);
            avgTemp = itemView.findViewById(R.id.history_temp);
            avgHr = itemView.findViewById(R.id.history_hr);
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
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_view, parent, false);

        return new DetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        final HistoryDetails details = historyDetails.get(position);
        String date = context.getString(R.string.date) + " " + details.getDate();
        String temp = context.getString(R.string.avg_temp) + " " + details.getAvgTemp();
        String hr = context.getString(R.string.avg_ecg) + " " + details.getAvgHr();
        String bp = context.getString(R.string.avg_bp) + " " + details.getAvgBp();
        String bloodSugar = context.getString(R.string.avg_blood_sugar) + " " + details.getAvgBloodSugar();

        holder.date.setText(date);
        holder.avgTemp.setText(temp);
        holder.avgHr.setText(hr);
        holder.avgBp.setText(bp);
        holder.avgBloodSugar.setText(bloodSugar);
    }

    @Override
    public int getItemCount() { return historyDetails.size(); }


}
