package com.example.edensight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private List<String> dateList, timeList, bpmList, spo2List;

    public HistoryAdapter(Context context, List<String> dateList, List<String> timeList, List<String> bpmList, List<String> spo2List) {
        this.context = context;
        this.dateList = dateList;
        this.timeList = timeList;
        this.bpmList = bpmList;
        this.spo2List = spo2List;
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
        String date = dateList.get(position), time = timeList.get(position), bpm = bpmList.get(position), spo2 = spo2List.get(position);
        if (bpm.equals("Nil")){
            holder.date.setText(": Nil");
            holder.time.setText(": Nil");
            holder.bpm.setText(": Nil");
            holder.spo2.setText(": Nil");
        } else {
            holder.date.setText(": " + date);
            holder.time.setText(": " +time);
            holder.bpm.setText(": " + bpm + "bpm");
            holder.spo2.setText(": " + spo2 + "%");
        }

    }

    @Override
    public int getItemCount() { return dateList.size(); }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView date, time, bpm, spo2;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.history_date);
            time = itemView.findViewById(R.id.history_time);
            bpm = itemView.findViewById(R.id.history_bpm);
            spo2 = itemView.findViewById(R.id.history_spo2);
        }
    }
}
