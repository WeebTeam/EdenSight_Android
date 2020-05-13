package com.example.edensight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardFragment extends Fragment {
    private static final String RESIDENT_PARAMETER = "resident";

    private Resident resident;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(Resident selectedResident) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putParcelable(RESIDENT_PARAMETER, selectedResident);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resident = getArguments().getParcelable(RESIDENT_PARAMETER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard,container,false);
        TextView testText = (TextView)view.findViewById(R.id.test_dashboardText);
        Button testButton = (Button)view.findViewById(R.id.test_dashboardButton);

        String text = "Name: " + resident.getName();
        testText.setText(text);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), resident.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
