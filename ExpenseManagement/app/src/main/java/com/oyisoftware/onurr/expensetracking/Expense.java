package com.oyisoftware.onurr.expensetracking;

import java.io.Serializable;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String expenseType;
    private int expenseWage;
    private String expenseDate;
    private String expenseNote;

    public Expense() {
        super();
    }

    public Expense(String expenseType, int expenseWage, String expenseDate,String expenseNote) {
        super();
        this.expenseType = expenseType;
        this.expenseWage = expenseWage;
        this.expenseDate = expenseDate;
        this.expenseNote = expenseNote;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExpenseWage() {
        return expenseWage;
    }

    public void setExpenseWage(int expenseWage) {
        this.expenseWage = expenseWage;
    }

    public String getExpenseNote() {
        return expenseNote;
    }

    public void setExpenseNote(String expenseNote) {
        this.expenseNote = expenseNote;
    }

    public String getExpenseDate(){return expenseDate;}

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
}
