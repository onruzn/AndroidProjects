package com.oyisoftware.onurr.expensetracking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HistoryActivity extends AppCompatActivity {

    private ListView customListView;
    DBhelper dbHelper;
    ListAdapter myListAdapter;
    int year;
    String date;
    public List<Expense> expenses;
    private SimpleDateFormat dateFormatter;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        customListView = (ListView) findViewById(R.id.listview2);
        dbHelper = new DBhelper(getApplicationContext());

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Calendar calendar = Calendar.getInstance();
        date=dateFormatter.format(calendar.getTime());
        year = calendar.get(Calendar.YEAR);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {

                        if (position==0){

                            expenses = dbHelper.getAllCountries();
                            myListAdapter = new ListAdapter(HistoryActivity.this, expenses);
                            customListView.setAdapter(myListAdapter);
                            myListAdapter.notifyDataSetChanged();

                        }


                        if (position==1){

                            expenses = dbHelper.getYearExpenses(String.valueOf(year));
                            myListAdapter = new ListAdapter(HistoryActivity.this, expenses);
                            customListView.setAdapter(myListAdapter);
                            myListAdapter.notifyDataSetChanged();

                        }

                        if (position==2){

                            expenses = dbHelper.getLastDatesExpenses();
                            myListAdapter = new ListAdapter(HistoryActivity.this, expenses);
                            customListView.setAdapter(myListAdapter);
                            myListAdapter.notifyDataSetChanged();

                        }
                        if (position==3){

                            expenses = dbHelper.getLastDatesExpenses();
                            myListAdapter = new ListAdapter(HistoryActivity.this, expenses);
                            customListView.setAdapter(myListAdapter);
                            myListAdapter.notifyDataSetChanged();

                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

    }
}
