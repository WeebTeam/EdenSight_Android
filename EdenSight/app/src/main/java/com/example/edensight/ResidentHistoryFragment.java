package com.example.edensight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResidentHistoryFragment extends Fragment {

    private static final String HIS_RESIDENT = "His_Resident";
    private Resident resident;

    public ResidentHistoryFragment() { }

    public static ResidentHistoryFragment newInstance(Resident resident) {
        ResidentHistoryFragment fragment = new ResidentHistoryFragment();
        Bundle args = new Bundle();
        args.putParcelable(HIS_RESIDENT, resident);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resident = getArguments().getParcelable(HIS_RESIDENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_resident_history, container, false);
        TextView textView = rootView.findViewById(R.id.history_text);
        textView.setText("Room Number: " + resident.getRoomNumber());
        return rootView;
    }
}