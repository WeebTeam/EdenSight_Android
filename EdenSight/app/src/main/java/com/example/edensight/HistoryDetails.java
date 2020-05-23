package com.example.edensight;

import java.util.ArrayList;

public class HistoryDetails {
    private String date, avgTemp, avgEcg, avgBp, avgBloodSugar;

    public HistoryDetails(String date, String avgTemp, String avgEcg, String avgBp, String avgBloodSugar) {
        this.date = date;
        this.avgTemp = avgTemp;
        this.avgEcg = avgEcg;
        this.avgBp = avgBp;
        this.avgBloodSugar = avgBloodSugar;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getAvgTemp() { return avgTemp; }

    public void setAvgTemp(String avgTemp) { this.avgTemp = avgTemp; }

    public String getAvgEcg() { return avgEcg; }

    public void setAvgEcg(String avgEcg) { this.avgEcg = avgEcg; }

    public String getAvgBp() { return avgBp; }

    public void setAvgBp(String avgBp) { this.avgBp = avgBp; }

    public String getAvgBloodSugar() { return avgBloodSugar; }

    public void setAvgBloodSugar(String avgBloodSugar) { this.avgBloodSugar = avgBloodSugar; }

    public static ArrayList<HistoryDetails> dummyDetailsList(){
        ArrayList<HistoryDetails> detailsArrayList = new ArrayList<HistoryDetails>();
        detailsArrayList.add(new HistoryDetails("25/04/2019", "25 \u2109", "105 bpm", "115/75 mmHg", "80 mg/dL"));
        detailsArrayList.add(new HistoryDetails("26/04/2019", "26 \u2109", "107 bpm", "117/78 mmHg", "82 mg/dL"));
        detailsArrayList.add(new HistoryDetails("27/04/2019", "25 \u2109", "104 bpm", "119/76 mmHg", "81 mg/dL"));
        detailsArrayList.add(new HistoryDetails("28/04/2019", "24 \u2109", "103 bpm", "120/80 mmHg", "78 mg/dL"));
        detailsArrayList.add(new HistoryDetails("29/04/2019", "27 \u2109", "108 bpm", "119/77 mmHg", "79 mg/dL"));

        return detailsArrayList;
    }
}
