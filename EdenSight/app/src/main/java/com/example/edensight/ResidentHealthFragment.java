package com.example.edensight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ResidentHealthFragment extends Fragment {

    private static final String HEL_RESIDENT = "Hel_Resident";
    private Resident resident;

    TextView weight, height, blood;
    ListView conditions, allergies, medication;

    String[] conditionsArray = {"Back Problem","Asthma", "Migraine", "Back Problem","Asthma", "Migraine"},
    allergiesArray = {"Penicillin", "Aspirin", "Peanuts"},
    medicationArray = {"Painkiller once a day", "Flu Medicine 3 times a day", "Medicine X after breakfast", "Another one", "And another one"};

    ArrayAdapter conditionsAdapter, allergiesAdapter, medicationAdapter;

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
        weight = rootView.findViewById(R.id.health_weight);
        height = rootView.findViewById(R.id.health_height);
        blood = rootView.findViewById(R.id.health_blood);
        conditions = rootView.findViewById(R.id.health_conditions);
        allergies = rootView.findViewById(R.id.health_allergies);
        medication = rootView.findViewById(R.id.health_medication);

        // Assign values to text here when values are available in database
        conditionsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listview, conditionsArray);
        allergiesAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listview, allergiesArray);
        medicationAdapter = new ArrayAdapter<String>(getActivity(), R.layout.listview, medicationArray);

        conditions.setAdapter(conditionsAdapter);
        allergies.setAdapter(allergiesAdapter);
        medication.setAdapter(medicationAdapter);

        return rootView;
    }
}