package com.oyisoftware.onurr.expensetracking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ListView customListView;
    DBhelper dbHelper;
    ListAdapter myListAdapter;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        Calendar cal = Calendar.getInstance();
        String monthname=(String)android.text.format.DateFormat.format("MM", new Date());
        String dayName=cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());


        TextView txtSum = (TextView) findViewById(R.id.textView3);


        TextView textView2 = (TextView) findViewById(R.id.textDate);
        textView2.setText(dayName);

        Button btnAdd=(Button)findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


        Button btnHistory=(Button)findViewById(R.id.btn_history);

        btnHistory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });


        customListView = (ListView) findViewById(R.id.listview);
        dbHelper = new DBhelper(getApplicationContext());


        int value=1;
        value=dbHelper.GetAllWage(monthname);
        String val=String.valueOf(value);

        txtSum.setText(val+" $");

        List<Expense> expenses = dbHelper.getDateExpenses(monthname);
        myListAdapter = new ListAdapter(MainActivity.this, expenses);
        customListView.setAdapter(myListAdapter);


        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView text1 = (TextView) view.findViewById(R.id.id_value);
                String select_Id = text1.getText().toString();

                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                i.putExtra("send_string",select_Id);
                startActivity(i);
                //Toast.makeText(getApplicationContext(),select_Id, Toast.LENGTH_LONG).show();
            }
        });

    }


    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
