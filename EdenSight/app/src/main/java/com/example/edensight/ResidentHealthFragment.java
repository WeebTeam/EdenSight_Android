package com.example.edensight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResidentHealthFragment extends Fragment {

    private static final String HEL_RESIDENT = "Hel_Resident";
    private Resident resident;

    public ResidentHealthFragment() { }

    public static ResidentHealthFragment newInstance(Resident resident) {
        ResidentHealthFragment fragment = new ResidentHealthFragment();
        Bundle args = new Bundle();
        args.putParcelable(HEL_RESIDENT, resident);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resident = getArguments().getParcelable(HEL_RESIDENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_resident_health, container,false);
        TextView textView = rootView.findViewById(R.id.health_text);
        textView.setText("Caretaker: " + resident.getCaretaker());
        return rootView;
    }
}