package com.oyisoftware.onurr.expensetracking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Expense>  expenseList;

    public ListAdapter(Activity activity, List<Expense> expenses) {
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        expenseList = expenses;
    }

    @Override
    public int getCount() {
        return expenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return expenseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.row_layout, null); // create layout from

        TextView textView = (TextView) vi.findViewById(R.id.textType); // user name
        TextView textView2 = (TextView) vi.findViewById(R.id.textDate);
        TextView textView3 = (TextView) vi.findViewById(R.id.id_value);
        TextView textView4 = (TextView) vi.findViewById(R.id.textWage);
        Expense expense = expenseList.get(position);

        String id=Integer.toString(expense.getId());

        String dayName=" ";
        int month=1;
        try
        {
            //String date = "15-06-2016";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(expense.getExpenseDate());
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            month = cal.get(Calendar.DAY_OF_MONTH); //YOUR MONTH IN INTEGER
            dayName=cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        textView.setText(expense.getExpenseType());
        textView2.setText(String.valueOf(month)+" "+dayName.toString());
        textView3.setText(id);
        textView4.setText(expense.getExpenseWage()+" $");
        return vi;
    }
}