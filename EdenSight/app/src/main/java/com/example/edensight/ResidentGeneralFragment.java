package com.example.edensight;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ResidentGeneralFragment extends Fragment {

    private static final String GEN_RESIDENT = "Gen_Resident";
    private Resident resident;

    LineChart lineChart;

    public ResidentGeneralFragment() { }

    public static ResidentGeneralFragment newInstance(Resident resident) {
        ResidentGeneralFragment fragment = new ResidentGeneralFragment();
        Bundle args = new Bundle();
        args.putParcelable(GEN_RESIDENT, resident);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            resident = getArguments().getParcelable(GEN_RESIDENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_resident_general, container, false);

        lineChart = rootView.findViewById(R.id.general_graph);
        ArrayList<String> xAxis = new ArrayList<>();
        ArrayList<Entry> yAxis = new ArrayList<>();

        float x = 0, y = 0;
        int numDataPoints = 5;

        for (int i=0; i < numDataPoints; i++){
            y = y + 1;
            x = x + 1;
            yAxis.add(new Entry(y, i));
            xAxis.add(i, String.valueOf(x));
        }
        String[] xAxes = new String[xAxis.size()];
        for (int i = 0; i < xAxis.size(); i++){
            xAxes[i] = xAxis.get(i);
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        LineDataSet lineDataSet1 = new LineDataSet(yAxis, "y");
        lineDataSet1.setDrawCircles(false);

        lineDataSets.add(lineDataSet1);
        lineChart.setData(new LineData(lineDataSets));
        lineChart.setVisibleXRangeMaximum(65f);

        return rootView;
    }
}