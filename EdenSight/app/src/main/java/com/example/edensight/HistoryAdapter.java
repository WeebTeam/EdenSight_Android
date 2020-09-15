package com.example.edensight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Random;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private List<String> dataList;

    public HistoryAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_view, parent, false);

        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        // Not actual data, will modify soon
        String date = dataList.get(position);
        Random random = new Random();
        String avgBpm = String.valueOf(random.nextInt(100-60) + 60);
        String avgSpo2 = String.valueOf(random.nextInt(99-97) + 97);

        holder.date.setText(": " + date);
        holder.avgBpm.setText(": " + avgBpm);
        holder.avgSpo2.setText(": " + avgSpo2);
    }

    @Override
    public int getItemCount() { return dataList.size(); }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView date, avgBpm, avgSpo2;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.history_date);
            avgBpm = itemView.findViewById(R.id.history_bpm);
            avgSpo2 = itemView.findViewById(R.id.history_spo2);
        }
    }
}
