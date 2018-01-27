package com.oyisoftware.onurr.expensetracking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtDate;
    EditText edtWage;
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
        setContentView(R.layout.activity_add);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


        final Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        dbHelper = new DBhelper(getApplicationContext());

        edtDate=(EditText)findViewById(R.id.editDate);
        edtWage=(EditText)findViewById(R.id.editWage);
        edtNote=(EditText)findViewById(R.id.editNote);


        Button btnClear=(Button)findViewById(R.id.btn_Clear);
        Button btnSave=(Button)findViewById(R.id.btn_Save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                valType=spinner.getSelectedItem().toString();
                valWage=Integer.parseInt( edtWage.getText().toString() );
                valDate=edtDate.getText().toString();
                valNote=edtNote.getText().toString();

                dbHelper.insertExpense(new Expense(valType.toString(),valWage,valDate.toString(),valNote.toString()));

                Intent intent = new Intent(AddActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                edtDate.getText().clear();
                edtNote.getText().clear();
                edtWage.getText().clear();


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


