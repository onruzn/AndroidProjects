package com.oyisoftware.onurr.expensetracking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtDate;
    EditText edtWage;
    EditText edtType;
    EditText edtNote;
    private int mYear, mMonth, mDay;
    private SimpleDateFormat dateFormatter;
    DBhelper dbHelper;
    String valType;
    String valNote;
    String valDate;
    int valWage=2;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        Bundle extras = getIntent().getExtras();
        final int value = Integer.parseInt(extras.getString("send_string"));
        dbHelper = new DBhelper(getApplicationContext());

        valType=dbHelper.getExpense(value).getExpenseType();
        valWage=dbHelper.getExpense(value).getExpenseWage();
        valDate=dbHelper.getExpense(value).getExpenseDate();
        valNote=dbHelper.getExpense(value).getExpenseNote();

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);



        edtDate=(EditText)findViewById(R.id.editDate);
        edtWage=(EditText)findViewById(R.id.editWage);
        edtType=(EditText)findViewById(R.id.editType);
        edtNote=(EditText)findViewById(R.id.editNote);

        edtDate.setText(valDate);
        edtWage.setText(Integer.toString(valWage));
        edtType.setText(valType);
        edtNote.setText(valNote);


        Button btnSave=(Button)findViewById(R.id.button);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                valType=edtType.getText().toString();
                valWage=Integer.parseInt( edtWage.getText().toString() );
                valDate=edtDate.getText().toString();
                valNote=edtNote.getText().toString();

                dbHelper.updateExpense(value,valType,valWage,valDate,valNote);
                Log.i("LogDeneme", "Guncelleme yapıldı.");

                Intent intent = new Intent(DetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnDelete=(Button)findViewById(R.id.button3);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                dbHelper.deleteExpense(value);
                Log.i("LogDeneme", "Kayıt silindi.");

                Intent intent = new Intent(DetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        edtDate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        c.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                        String dateString = dateFormat.format(c.getTime());

                        edtDate.setText(dateString);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

}
