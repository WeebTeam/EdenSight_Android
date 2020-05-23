package com.example.edensight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
        TextView timeDisplay = view.findViewById(R.id.test_dashboardText);
        Button refreshBtn = view.findViewById(R.id.test_dashboardButton);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        String currentTime = "Last Updated: " + dateFormat.format(calendar.getTime());
        timeDisplay.setText(currentTime);

        // Lets user refresh fragment to retrieve latest data and time
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(DashboardFragment.this).attach(DashboardFragment.this).commit();
                Toast.makeText(getActivity(), "Update Complete", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
