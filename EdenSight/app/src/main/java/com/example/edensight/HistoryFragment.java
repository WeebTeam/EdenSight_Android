package com.example.edensight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    private static final String RESIDENT_PARAMETER = "resident";

    private Resident resident;
    private ArrayList<HistoryDetails> details;
    private HistoryAdapter historyAdapter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance(Resident selectedResident) {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        details = HistoryDetails.dummyDetailsList();
        RecyclerView recyclerView = view.findViewById(R.id.history_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        historyAdapter = new HistoryAdapter(details, getActivity());
        recyclerView.setAdapter(historyAdapter);

        return view;
    }
}
