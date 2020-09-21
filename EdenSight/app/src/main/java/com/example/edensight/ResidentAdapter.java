package com.example.edensight;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResidentAdapter extends RecyclerView.Adapter<ResidentAdapter.ResidentViewHolder> {
    private List<Resident> residentList;
    private Context context;
    private String username, password;

    static class ResidentViewHolder extends RecyclerView.ViewHolder{
        TextView name, roomNo, bpm, spo2;

        ResidentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.person_name);
            roomNo = itemView.findViewById(R.id.person_roomNo);
            bpm = itemView.findViewById(R.id.person_bpm);
            spo2 = itemView.findViewById(R.id.person_spo2);
        }
    }

    ResidentAdapter(List<Resident> residentList, Context context, String username, String password){
        this.residentList = residentList;
        this.context = context;
        this.username = username;
        this.password = password;
    }

    @NonNull
    @Override
    public ResidentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resident_view, parent, false);

        return new ResidentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResidentViewHolder holder, final int position) {
        final Resident resident = residentList.get(position);

        String name = resident.getName();
        String roomNum = resident.getRoomNumber();
        String bpm = resident.getFirstBpmList() + "bpm";
        String spo2 = resident.getFirstSpo2List() + "%";

        holder.name.setText(name);
        holder.roomNo.setText(roomNum);
        holder.bpm.setText(bpm);
        holder.spo2.setText(spo2);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResidentDetailsActivity.class);
                intent.putExtra("resident", resident);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return residentList.size();
    }

    void filterList(ArrayList<Resident> filteredList){
        residentList = filteredList;
        notifyDataSetChanged();
    }
}
