package com.example.edensight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResidentGeneralFragment extends Fragment {

    private static final String GEN_RESIDENT = "Gen_Resident";
    private Resident resident;

    public ResidentGeneralFragment() { }

    public static ResidentGeneralFragment newInstance(Resident resident) {
        ResidentGeneralFragment fragment = new ResidentGeneralFragment();
        Bundle args = new Bundle();
        args.putParcelable(GEN_RESIDENT, resident);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resident = getArguments().getParcelable(GEN_RESIDENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_resident_general, container, false);
        TextView textView = rootView.findViewById(R.id.general_text);
        textView.setText("Name: " + resident.getName());
        return rootView;
    }
}