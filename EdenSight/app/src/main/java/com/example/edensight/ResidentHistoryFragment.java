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
import java.util.List;

public class ResidentHistoryFragment extends Fragment {

    private static final String HIS_RESIDENT = "His_Resident";
    private Resident resident;
    RecyclerView historyRecyclerView;
    HistoryAdapter historyAdapter;

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
        List<String> dateList = new ArrayList<>(), timeList = new ArrayList<>(), bpmList = resident.getBpmList(), spo2List = resident.getSpo2List(), rawDateList = resident.getUpdateDateList();

        if (rawDateList.get(0).equals("Nil")){
            dateList.add("Nil");
            timeList.add("Nil");
        } else {
            for (int i = 0; i < rawDateList.size(); i++){
                String[] dataArray = rawDateList.get(i).replace("T", ",").replace(".000Z", "").split(",");
                dateList.add(dataArray[0]);
                timeList.add(dataArray[1]);
            }
        }

        historyRecyclerView = rootView.findViewById(R.id.history_recyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        historyRecyclerView.addItemDecoration(dividerItemDecoration);
        historyAdapter = new HistoryAdapter(getActivity(), dateList, timeList, bpmList, spo2List);
        historyRecyclerView.setAdapter(historyAdapter);

        return rootView;
    }
}