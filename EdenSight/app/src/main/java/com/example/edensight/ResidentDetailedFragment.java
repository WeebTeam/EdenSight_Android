package com.example.edensight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResidentDetailedFragment extends Fragment {

    private static final String DET_RESIDENT = "Det_Resident";
    private Resident resident;

    public ResidentDetailedFragment() { }

    public static ResidentDetailedFragment newInstance(Resident resident) {
        ResidentDetailedFragment fragment = new ResidentDetailedFragment();
        Bundle args = new Bundle();
        args.putParcelable(DET_RESIDENT, resident);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resident = getArguments().getParcelable(DET_RESIDENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_resident_detailed, container,false);
        TextView textView = rootView.findViewById(R.id.detailed_text);
        textView.setText("Allocated Date: " + resident.getAllocationDate());
        return rootView;
    }
}