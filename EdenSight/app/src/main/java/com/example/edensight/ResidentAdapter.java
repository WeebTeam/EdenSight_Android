package com.example.edensight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ResidentAdapter extends RecyclerView.Adapter<ResidentAdapter.ResidentViewHolder> {
    private List<Resident> residentList;
    private Context context;
    private String username, password;

    static class ResidentViewHolder extends RecyclerView.ViewHolder{
        Button testButton;
        TextView name, roomNumber, status, caretaker;

        ResidentViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.resident_name);
            roomNumber = itemView.findViewById(R.id.resident_roomNumber);
            status = itemView.findViewById(R.id.resident_status);
            caretaker = itemView.findViewById(R.id.resident_caretaker);
            testButton = itemView.findViewById(R.id.details_button);
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
        final String name = context.getString(R.string.name) + " " +  resident.getName();
        String roomNum = context.getString(R.string.room_number) + " " + resident.getRoomNumber();
        String status = context.getString(R.string.status) + " " + resident.getStatus();
        String caretaker = context.getString(R.string.caretaker) + " " + resident.getCaretaker();

        holder.name.setText(name);
        holder.roomNumber.setText(roomNum);
        holder.status.setText(status);
        holder.caretaker.setText(caretaker);

        holder.testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ResidentDetailsActivity.class);
                intent.putExtra("resident", resident);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
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
