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
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    EditText addexpense_expense,addexpense_date,addexpense_notes,titleexpense_expense;
    Spinner tipe;
    Calendar myCalendar = Calendar.getInstance();
    FloatingActionButton fab2;
    String spinnertext;

    protected Cursor cursor;
    DatabaseHelper dbHelper;


    String notes,date;
    double value;
    boolean editingNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbHelper = new DatabaseHelper(this);


        setTitle("Add new");
        titleexpense_expense = (EditText) findViewById(R.id.titlexpense_expense);
        addexpense_expense = (EditText) findViewById(R.id.addexpense_expense);
        addexpense_date = (EditText) findViewById(R.id.addexpense_date);
        addexpense_notes = (EditText) findViewById(R.id.addexpense_notes);
        fab2 = (FloatingActionButton) findViewById(R.id.fabs2);







        spinnerinit();


        tipe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Text = parent.getSelectedItem().toString();
                spinnertext=Text;
              /*  if(Text.equals("Select one")) {
                    Toast.makeText(AddActivity.this, "expense", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Text.equals("Income")){
                    Toast.makeText(AddActivity.this, "Income", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(Text.equals("Expense")){
                    Toast.makeText(AddActivity.this, "Income", Toast.LENGTH_SHORT).show();
                    return;
                }
                */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        addexpense_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddActivity.this, dates, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String newTitle= titleexpense_expense.getText().toString();
                String newValue = addexpense_expense.getText().toString();


                Integer intValue = Integer.parseInt(newValue);

                String newDate = addexpense_date.getText().toString();
                String newNotes = addexpense_notes.getText().toString();



            if(spinnertext.equals("Select one"))
            {
                Toast.makeText(AddActivity.this, "Value, Type, dan Date harus diisi", Toast.LENGTH_SHORT).show();
                if(newValue.equals("") || newDate.equals(""))
                {
                    Toast.makeText(AddActivity.this, "Value, Type, dan Date harus diisi", Toast.LENGTH_SHORT).show();
                }

            }

                if(spinnertext.equals("Select one"))
                {
                    Toast.makeText(AddActivity.this, "Value, Type, dan Date harus diisi", Toast.LENGTH_SHORT).show();
                    if(newValue.equals("") || newDate.equals(""))
                    {
                        Toast.makeText(AddActivity.this, "Value, Type, dan Date harus diisi", Toast.LENGTH_SHORT).show();
                    }

                }




                else if(spinnertext.equals("Income"))
                {

                    dbHelper.addData(newTitle ,intValue,0,newDate , newNotes);

                    Toast.makeText(getApplicationContext(), "Berhasil"+newValue+newDate+newNotes, Toast.LENGTH_LONG).show();





                    Intent i = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }

                else if(spinnertext.equals("Expense"))
                {
                    intValue=intValue*-1;
                    dbHelper.addData(newTitle ,intValue,1,newDate , newNotes);

                    Toast.makeText(getApplicationContext(), "Berhasil"+newValue+newDate+newNotes, Toast.LENGTH_LONG).show();





                    Intent i = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }



                else if(spinnertext.equals("Income"))
                {

                    dbHelper.addData(newTitle ,intValue,0,newDate , newNotes);

                    Toast.makeText(getApplicationContext(), "Berhasil"+newValue+newDate+newNotes, Toast.LENGTH_LONG).show();





                    Intent i = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }

            else if(spinnertext.equals("Expense"))
            {
                intValue=intValue*-1;
                dbHelper.addData(newTitle ,intValue,1,newDate , newNotes);

                Toast.makeText(getApplicationContext(), "Berhasil"+newValue+newDate+newNotes, Toast.LENGTH_LONG).show();





                Intent i = new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
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
        String myFormat = "yyyy/dd/mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        addexpense_date.setText(sdf.format(myCalendar.getTime()));
    }


    public void spinnerinit()
    {
        tipe = (Spinner) findViewById(R.id.typespinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipe_expenses, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        tipe.setAdapter(adapter);
    }






}
