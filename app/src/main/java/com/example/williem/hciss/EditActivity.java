package com.example.williem.hciss;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    EditText addexpense_expense,addexpense_date,addexpense_notes,titleexpense_expense;
    Spinner tipe;
    Calendar myCalendar = Calendar.getInstance();
    FloatingActionButton button;


    protected Cursor cursor;
    DatabaseHelper dbHelper;

    Integer values;
    String notes,date;
    double value;
    boolean editingNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        dbHelper = new DatabaseHelper(this);


        setTitle("Edit");
        titleexpense_expense = (EditText) findViewById(R.id.expense_edit_title);
        addexpense_expense = (EditText) findViewById(R.id.expense_edit_value);
        addexpense_date = (EditText) findViewById(R.id.expense_edit_date);
        addexpense_notes = (EditText) findViewById(R.id.expense_edit_note);
        button = (FloatingActionButton) findViewById(R.id.fabs3);

        List<String> categories = new ArrayList<String>();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newString;

                Bundle extras = getIntent().getExtras();

                newString = extras.getString("id");

                if (addexpense_expense.getText().toString().equals("") || addexpense_notes.getText().toString().equals("")) {
                    Toast.makeText(EditActivity.this, "Value dan Date harus diisi", Toast.LENGTH_SHORT).show();
                } else {

                    values = Integer.parseInt(addexpense_expense.getText().toString());

                    dbHelper.editExpense(titleexpense_expense.getText().toString(), values, addexpense_notes.getText().toString(), newString);
                    finish();
                }
            }
        });




    }

    DatePickerDialog.OnDateSetListener dates = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        addexpense_date.setText(sdf.format(myCalendar.getTime()));
    }






}
