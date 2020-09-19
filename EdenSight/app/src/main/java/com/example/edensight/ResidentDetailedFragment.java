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
    TextView name, caretaker, roomNo, gender, dob, ic, nation, phoneNo, guardian, emergency, address;

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
        name = rootView.findViewById(R.id.resident_detailed_name);
        caretaker = rootView.findViewById(R.id.resident_detailed_caretaker);
        roomNo = rootView.findViewById(R.id.resident_detailed_room_number);
        gender = rootView.findViewById(R.id.resident_detailed_gender);
        dob = rootView.findViewById(R.id.resident_detailed_dob);
        ic = rootView.findViewById(R.id.resident_detailed_ic);
        nation = rootView.findViewById(R.id.resident_detailed_nation);
        phoneNo = rootView.findViewById(R.id.resident_detailed_phone_number);
        guardian = rootView.findViewById(R.id.resident_detailed_guardian);
        emergency = rootView.findViewById(R.id.resident_detailed_emergency);
        address = rootView.findViewById(R.id.resident_detailed_address);

        // More will be added once database has all values
        name.setText(": " + resident.getName());
        caretaker.setText(": " + resident.getCaretaker());
        roomNo.setText(": " + resident.getRoomNumber());
        gender.setText(": " + resident.getGender());
        dob.setText(": " + resident.getDob());
        ic.setText(": " + resident.getIc());
        nation.setText(": " + resident.getNationality());
        phoneNo.setText(": " + resident.getTelNum());
        guardian.setText(": " + resident.getGuardian());
        emergency.setText(": " + resident.getEmergencyTel());
        address.setText(": " + resident.getAddress());

        return rootView;
    }
}