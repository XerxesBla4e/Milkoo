package com.example.covidmonitor;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covidmonitor.api.ApiUtilities;
import com.example.covidmonitor.api.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private TextView totalConfirm, totalActive, totalDeaths, totalRecovered, totalTests;
    private TextView todayConfirm, todayRecovered, todayDeaths, dateTv;
    private PieChart pieChart;

    private List<CountryData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hooks();

        list = new ArrayList<>();

        ApiUtilities.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                        list.addAll(response.body());

                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCountry().equals("Uganda")) {
                                int confirm = Integer.parseInt(list.get(i).getCases());
                                int active = Integer.parseInt(list.get(i).getActive());
                                int recovered = Integer.parseInt(list.get(i).getRecovered());
                                int death = Integer.parseInt(list.get(i).getDeaths());

                                totalActive.setText(NumberFormat.getInstance().format(active));
                                totalConfirm.setText(NumberFormat.getInstance().format(confirm));
                                totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                                totalDeaths.setText(NumberFormat.getInstance().format(death));

                                setText(list.get(i).getUpdated());

                                todayDeaths.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                                todayConfirm.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                                todayRecovered.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));
                                totalTests.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));

                                pieChart.addPieSlice(new PieModel("Confirm", confirm, getResources().getColor(R.color.yellow)));
                                pieChart.addPieSlice(new PieModel("Active", confirm, getResources().getColor(R.color.blue)));
                                pieChart.addPieSlice(new PieModel("Recovered", confirm, getResources().getColor(R.color.green)));
                                pieChart.addPieSlice(new PieModel("Deaths", confirm, getResources().getColor(R.color.red_pie)));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setText(String updated) {
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        long milliseconds = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        dateTv.setText("Updated at " + format.format(calendar.getTime()));
    }

    private void hooks() {
        totalConfirm = findViewById(R.id.totalconfirmed);
        totalActive = findViewById(R.id.totalactive);
        totalDeaths = findViewById(R.id.totaldeaths);
        totalRecovered = findViewById(R.id.totalrecovered);
        todayConfirm = findViewById(R.id.todayconfirmed);
        todayRecovered = findViewById(R.id.todayrecovered);
        todayDeaths = findViewById(R.id.todaydeaths);
        totalTests = findViewById(R.id.totaltests);
        pieChart = findViewById(R.id.piechart);
        dateTv = findViewById(R.id.updatedate);
    }
}