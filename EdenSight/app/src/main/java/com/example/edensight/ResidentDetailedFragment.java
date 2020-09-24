package com.example.edensight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ResidentDetailedFragment extends Fragment {

    private static final String DET_RESIDENT = "Det_Resident";
    private Resident resident;
    TextView name, caretaker, roomNo, gender, dob, ic, nation, phoneNo, guardian, emergency, streetAddr1, streetAddr2, postal, city, state;

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
        streetAddr1 = rootView.findViewById(R.id.resident_detailed_streetAddr1);
        streetAddr2 = rootView.findViewById(R.id.resident_detailed_streetAddr2);
        postal = rootView.findViewById(R.id.resident_detailed_postal);
        city = rootView.findViewById(R.id.resident_detailed_city);
        state = rootView.findViewById(R.id.resident_detailed_state);

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
        streetAddr1.setText(": " + resident.getStreetAddr1());
        streetAddr2.setText(": " + resident.getStreetAddr2());
        postal.setText(": " + resident.getPostal());
        city.setText(": " + resident.getCity());
        state.setText(": " + resident.getState());

        streetAddr1.setMovementMethod(new ScrollingMovementMethod());
        streetAddr2.setMovementMethod(new ScrollingMovementMethod());
        return rootView;
    }
}