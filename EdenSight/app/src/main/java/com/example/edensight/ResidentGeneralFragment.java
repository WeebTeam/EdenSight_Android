package com.example.edensight;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ResidentGeneralFragment extends Fragment {

    private static final String GEN_RESIDENT = "Gen_Resident";
    private Resident resident;

    private LineChart volumeReportChart;
    private TextView bpmText, spo2Text, updateDate, graphTitle;

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
        volumeReportChart = rootView.findViewById(R.id.general_graph);
        bpmText = rootView.findViewById(R.id.bpm_text);
        spo2Text = rootView.findViewById(R.id.spo2_text);
        updateDate = rootView.findViewById(R.id.updateDate);
        graphTitle = rootView.findViewById(R.id.general_graph_title);

        List<String> dates = resident.getUpdateDateList();
        List<String> residentSpo2List = resident.getSpo2List();
        List<Double> amounts = new ArrayList<>();
        List<String> residentBpmList = resident.getBpmList();

        if (dates.get(0).equals("Nil")){
            String bpm = "BPM: " + residentBpmList.get(0);
            bpmText.setText(bpm);
            String spo2 = "SpO2: " + residentSpo2List.get(0);
            spo2Text.setText(spo2);
            updateDate.setVisibility(View.INVISIBLE);
        } else {
            for (int i = 0; i < residentBpmList.size(); i++){
                amounts.add(Double.parseDouble(residentBpmList.get(residentBpmList.size()-1-i)));
            }

            String bpm = "BPM: " + residentBpmList.get(0) + "bpm";
            bpmText.setText(bpm);
            String spo2 = "SpO2: " + residentSpo2List.get(0) + "%";
            spo2Text.setText(spo2);
            String[] dataArray = dates.get(0).replace("T", ",").replace(".000Z", "").split(",");
            String updateText = "Last updated: " + dataArray[0] + " at " + dataArray[1];
            updateDate.setText(updateText);

            renderData(dates, amounts);
        }

        return rootView;
    }

    public class ClaimsXAxisValueFormatter extends ValueFormatter {

        List<String> datesList;

        public ClaimsXAxisValueFormatter(List<String> arrayOfDates) {
            this.datesList = arrayOfDates;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return "";
        }
    }

    public class ClaimsYAxisValueFormatter extends ValueFormatter {

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return value + "bpm";
        }
    }

    public void renderData(List<String> dates, List<Double> allAmounts) {

        XAxis xAxis = volumeReportChart.getXAxis();
        XAxis.XAxisPosition position = XAxis.XAxisPosition.BOTTOM;
        xAxis.setPosition(position);
        xAxis.enableGridDashedLine(2f, 7f, 0f);
        xAxis.setAxisMaximum(5f);
        xAxis.setAxisMinimum(0f);
        xAxis.setLabelCount(6, true);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(7f);
        xAxis.setLabelRotationAngle(315f);

        xAxis.setValueFormatter(new ClaimsXAxisValueFormatter(dates));

        xAxis.setCenterAxisLabels(true);

        xAxis.setDrawLimitLinesBehindData(true);

        LimitLine ll1 = new LimitLine(30f,"Graph of Beats per Minute over 6 previous points in time");
        ll1.setLineColor(getResources().getColor(R.color.greenBackground));
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(35f, "");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);
        ll2.setLineColor(Color.parseColor("#FFFFFF"));

        xAxis.removeAllLimitLines();
        xAxis.addLimitLine(ll1);
        xAxis.addLimitLine(ll2);

        YAxis leftAxis = volumeReportChart.getAxisLeft();
        leftAxis.removeAllLimitLines();

        leftAxis.setAxisMaximum(160f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);
        leftAxis.setValueFormatter(new ClaimsYAxisValueFormatter());

        volumeReportChart.getDescription().setEnabled(true);
        Description description = new Description();

        description.setText("Points in Time");
        description.setTextSize(15f);
        volumeReportChart.getDescription().setPosition(0f, 0f);
        volumeReportChart.setDescription(description);
        volumeReportChart.getAxisRight().setEnabled(false);

        //setData()-- allAmounts is data to display;
        setDataForWeeksWise(allAmounts);

    }

    private void setDataForWeeksWise(List<Double> amounts) {

        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < amounts.size(); i++){
            values.add(new Entry(i, amounts.get(i).floatValue()));
        }

        LineDataSet set1;
        if (volumeReportChart.getData() != null && volumeReportChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) volumeReportChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            volumeReportChart.getData().notifyDataChanged();
            volumeReportChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Beats per minute");
            set1.setDrawCircles(true);
            set1.enableDashedLine(10f, 0f, 0f);
            set1.enableDashedHighlightLine(10f, 0f, 0f);
            set1.setColor(getResources().getColor(R.color.blue));
            set1.setCircleColor(getResources().getColor(R.color.blue));
            set1.setLineWidth(2f);//line size
            set1.setCircleRadius(5f);
            set1.setDrawCircleHole(true);
            set1.setValueTextSize(10f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(5f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(5.f);

            set1.setFillColor(Color.WHITE);

            set1.setDrawValues(true);
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);

            volumeReportChart.setData(data);
        }
    }
}