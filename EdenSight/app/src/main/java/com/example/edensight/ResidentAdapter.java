package com.example.edensight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ResidentAdapter extends RecyclerView.Adapter<ResidentAdapter.ResidentViewHolder> {
    private List<Resident> residentList;
    private AdapterCallBack adapterCallBack;

    public class ResidentViewHolder extends RecyclerView.ViewHolder{
        public ImageView picture;
        public TextView name, age;

        public ResidentViewHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.resident_picture);
            name = itemView.findViewById(R.id.resident_name);
            age = itemView.findViewById(R.id.resident_age);
        }
    }

    public ResidentAdapter (List<Resident> residentList, Context context){
        this.residentList = residentList;
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
        holder.picture.setImageResource(R.drawable.placeholder);
        holder.name.setText(resident.getName());
        holder.age.setText(resident.getAge());

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

    public void filterList(ArrayList<Resident> filteredList){
        residentList = filteredList;
        notifyDataSetChanged();
    }
}
