package com.oyisoftware.onurr.expensetracking;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBhelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME   = "ExpenseDB";
    // Contacts table name
    private static final String TABLE_EXPENSE = "expenses";

    /*private static final String id = "id";
    private static final String expense_type = "type";
    private static final String expense_wage = "wage";
    private static final String expense_date = "date";
    private static final String expense_note = "note";*/


    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = "CREATE TABLE " + TABLE_EXPENSE + "("+id+" INTEGER PRIMARY KEY,"+expense_type+" TEXT,"+expense_wage+" INTEGER,"+expense_date+" DATETIME DEFAULT CURRENT_TIMESTAMP,"+expense_note+" TEXT" + ")";
        String sql = "CREATE TABLE " + TABLE_EXPENSE + "(id INTEGER PRIMARY KEY,expense_type TEXT,expense_wage INTEGER,expense_date DATETIME DEFAULT CURRENT_TIMESTAMP,expense_note TEXT" + ")";
        Log.d("DBHelper", "SQL : " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        onCreate(db);
    }

    public void insertExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("expense_type", expense.getExpenseType());
        values.put("expense_wage", expense.getExpenseWage());
        values.put("expense_date", expense.getExpenseDate());
        values.put("expense_note", expense.getExpenseNote());

        db.insert(TABLE_EXPENSE, null, values);
        db.close();
    }

    public void updateExpense(int id ,String type,int wage,String date,String note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("expense_type", type);
        values.put("expense_wage", wage);
        values.put("expense_date", date);
        values.put("expense_note", note );

        db.update(TABLE_EXPENSE, values, "id" + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_EXPENSE, "id=" + id, null);
        db.close();
    }

    public void resetExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_EXPENSE,null, null);
        db.close();
    }

    public List<Expense> getAllCountries() {
        List<Expense> expenses = new ArrayList<Expense>();

         String sqlQuery = "SELECT  * FROM " + TABLE_EXPENSE ;
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.query(TABLE_EXPENSE, new String[]{"id", "expense_type", "expense_wage","expense_date","expense_note"},null , null, null, null, null);

        while (cursor.moveToNext()) {
            Expense expense = new Expense();
            expense.setId(cursor.getInt(0));
            expense.setExpenseType(cursor.getString(1));
            expense.setExpenseWage(cursor.getInt(2));
            expense.setExpenseDate(cursor.getString(3));
            expense.setExpenseNote(cursor.getString(4));
            expenses.add(expense);
        }
        return expenses;
    }

    //Tek fonksiyona inebilir.

    public List<Expense> getDateExpenses(String date) {

        List<Expense> expenses = new ArrayList<Expense>();
        String sqlQuery = "SELECT  * FROM " + TABLE_EXPENSE+ " WHERE strftime('%m',expense_date)='"+date+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        while (cursor.moveToNext()) {
            Expense expense = new Expense();
            expense.setId(cursor.getInt(0));
            expense.setExpenseType(cursor.getString(1));
            expense.setExpenseWage(cursor.getInt(2));
            expense.setExpenseDate(cursor.getString(3));
            expense.setExpenseNote(cursor.getString(4));
            expenses.add(expense);
        }
        return expenses;
    }

    public List<Expense> getYearExpenses(String date) {

        List<Expense> expenses = new ArrayList<Expense>();
        String sqlQuery = "SELECT  * FROM " + TABLE_EXPENSE+ " WHERE strftime('%Y',expense_date)='"+date+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        while (cursor.moveToNext()) {
            Expense expense = new Expense();
            expense.setId(cursor.getInt(0));
            expense.setExpenseType(cursor.getString(1));
            expense.setExpenseWage(cursor.getInt(2));
            expense.setExpenseDate(cursor.getString(3));
            expense.setExpenseNote(cursor.getString(4));
            expenses.add(expense);
        }
        return expenses;
    }

    public List<Expense> getLastDatesExpenses() {

        List<Expense> expenses = new ArrayList<Expense>();
        String sqlQuery = "SELECT  * FROM " + TABLE_EXPENSE+ " WHERE expense_date > date('now','-3 month' )" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);

        while (cursor.moveToNext()) {
            Expense expense = new Expense();
            expense.setId(cursor.getInt(0));
            expense.setExpenseType(cursor.getString(1));
            expense.setExpenseWage(cursor.getInt(2));
            expense.setExpenseDate(cursor.getString(3));
            expense.setExpenseNote(cursor.getString(4));
            expenses.add(expense);
        }
        return expenses;
    }


    public Expense getExpense(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String sqlQuery = "SELECT  * FROM " + TABLE_EXPENSE+ " WHERE id="+id ;

        Cursor cursor = db.rawQuery(sqlQuery, null);

        if (cursor != null)
            cursor.moveToFirst();

            Expense expense=new Expense();
            expense.setId(cursor.getInt(0));
            expense.setExpenseType(cursor.getString(1));
            expense.setExpenseWage(cursor.getInt(2));
            expense.setExpenseDate(cursor.getString(3));
            expense.setExpenseNote(cursor.getString(4));


        return expense;
    }

    //ay ve yıl için ayrı sorgu ile toplam ucret alınacak......

    public int GetAllWage(String date){
        String sqlQuery = "SELECT  sum(expense_wage) FROM " + TABLE_EXPENSE+ " WHERE strftime('%m',expense_date)='"+date+"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlQuery, null);
        int sum=2;
        if(cursor.moveToFirst())
        {

            sum= cursor.getInt(0);
        }
        return sum;
    }
}
