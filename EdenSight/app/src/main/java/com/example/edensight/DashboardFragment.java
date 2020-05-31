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
import java.util.Random;

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

        TextView tempText = view.findViewById(R.id.temp_text);
        TextView ecgText = view.findViewById(R.id.ecg_text);
        TextView bpText = view.findViewById(R.id.bp_text);
        TextView bloodsugarText = view.findViewById(R.id.blood_sugar_text);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        String currentTime = "Last Updated: " + dateFormat.format(calendar.getTime());
        timeDisplay.setText(currentTime);

        String temperature = "BT: " + randomNumber(38, 36) + " \u2103";
        tempText.setText(temperature);
        String ecg = "ECG: " + randomNumber(95, 70) + " bpm";
        ecgText.setText(ecg);
        String bp = "BP: " + randomNumber(120, 110) + "/" + randomNumber(80,70) + " mmHg";
        bpText.setText(bp);
        String bloodsugar = "BG: " + randomNumber(140, 72) + " mg/dL";
        bloodsugarText.setText(bloodsugar);

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

    // Function that generates random number between range
    public int randomNumber(int maxNumber, int minNumber){
        Random random = new Random();
        // + 1 to include max number
        int result  = random.nextInt(maxNumber - minNumber + 1) + minNumber;
        return result;
    }
}
