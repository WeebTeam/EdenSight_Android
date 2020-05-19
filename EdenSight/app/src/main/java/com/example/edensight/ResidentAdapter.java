package com.example.edensight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ResidentAdapter extends RecyclerView.Adapter<ResidentAdapter.ResidentViewHolder> {
    private List<Resident> residentList;
    private AdapterCallBack adapterCallBack;
    private Context context;

    static class ResidentViewHolder extends RecyclerView.ViewHolder{
        ImageView picture;
        TextView name, roomNumber, status, caretaker;

        ResidentViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.resident_picture);
            name = itemView.findViewById(R.id.resident_name);
            roomNumber = itemView.findViewById(R.id.resident_roomNumber);
            status = itemView.findViewById(R.id.resident_status);
            caretaker = itemView.findViewById(R.id.resident_caretaker);
        }
    }

    ResidentAdapter(List<Resident> residentList, Context context){
        this.residentList = residentList;
        this.context = context;
        try {
            this.adapterCallBack = ((AdapterCallBack) context);
        } catch (ClassCastException e){
            throw new ClassCastException("Activity must implement AdapterCallBack!!");
        }
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
        String name = context.getString(R.string.name) + " " +  resident.getName();
        String roomNum = context.getString(R.string.room_number) + " " + resident.getRoomNumber();
        String status = context.getString(R.string.status) + " " + resident.getStatus();
        String caretaker = context.getString(R.string.caretaker) + " " + resident.getCaretaker();

        holder.name.setText(name);
        holder.roomNumber.setText(roomNum);
        holder.status.setText(status);
        holder.caretaker.setText(caretaker);

        // Loading Image from URL via Custom Picasso Class
        PicassoClient.downloadImage(context, resident.getImageURL(), holder.picture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterCallBack.onMethodCallBack(resident);
            }
        });
    }

    @Override
    public int getItemCount() {
        return residentList.size();
    }

    public interface AdapterCallBack{
        void onMethodCallBack(Resident resident);
    }

    void filterList(ArrayList<Resident> filteredList){
        residentList = filteredList;
        notifyDataSetChanged();
    }
}
