package com.example.williem.hciss;



import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {

    protected Cursor cursor;
    DatabaseHelper dbcenter;
    TextView total;
    EditText calendar;
  //  Button find;
    ListView list;
    TodoCursorAdapter todoAdapter;
    String daftar[];
    String dates;
    String tanggal;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
   final    TextView v= (TextView)view.findViewById(R.id.tvcalendar);
        total = (TextView)view.findViewById(R.id.totaltransaksi);
        list = (ListView) view.findViewById(R.id.lists);

      //  find = (Button) view.findViewById(R.id.findtanggal);
        CalendarView calendarview=(CalendarView)view.findViewById(R.id.calendar);
//        find.setBackgroundColor(Color.BLUE);
//        find.setTextColor(Color.WHITE);


        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month=month+1;
                String dayofmonth;
                String date = null;

                if(dayOfMonth<10)
                {

                    String numberAsString = Integer.toString(+dayOfMonth);
                    dayofmonth = "0"+numberAsString;
                    date = (year+"-"+month+"-"+dayofmonth);
                   dates = (year+"-"+month+"-"+dayofmonth);
                    v.setText(dates);
                }

                else if (dayOfMonth>=10) {
                    date = (year + "-" + month + "-" + dayOfMonth);
                    String tahun = Integer.toString(year);
                    String bulan = Integer.toString(month);
                    String hari = Integer.toString(dayOfMonth);
                    tanggal = tahun+"-"+bulan+"-"+hari;
                    v.setText(date);
                }

                dbcenter = new DatabaseHelper(getActivity());

                // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
                DatabaseHelper handler = new DatabaseHelper(getActivity());
// Get access to the underlying writeable database
                SQLiteDatabase db = handler.getWritableDatabase();
// Query for items from the database and get a cursor back

                int jum=dbcenter.sumByDate(date);
                Integer intInstance = new Integer(jum);
                String numberAsString = intInstance.toString();

                Cursor todoCursor = handler.getListContents(date);

                // Find ListView to populate
                total.setText("Total Transaksi : Rp."+numberAsString+",-");
// Setup cursor adapter using cursor from last step
                TodoCursorAdapter todoAdapter = new TodoCursorAdapter(getActivity(), todoCursor);
// Attach cursor adapter to the ListView
                list.setAdapter(todoAdapter);

            }
        });


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View views, int position, long id) {
                SQLiteCursor cursor = (SQLiteCursor) list.getItemAtPosition(position);
                String selectedItem = cursor.getString(0);
                DatabaseHelper handler = new DatabaseHelper(getActivity());
                handler.deleteExpense(selectedItem);

                refresh(view);
                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();

                return false;
            }
        });



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
//                String selectedFromList =(String) (listview.getItemAtPosition(myItemInt));


                                            String s = list.getItemAtPosition(myItemInt).toString();
                                            SQLiteCursor cursor = (SQLiteCursor) list.getItemAtPosition(myItemInt);
                                            String selectedItem = cursor.getString(0);

                                            System.out.println(selectedItem);

                                            String strName = null;
                                            Intent i = new Intent(getActivity(), EditActivity.class);
                                            i.putExtra("id", selectedItem);
                                            startActivity(i);
                                        }
                                    });
/*
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbcenter = new DatabaseHelper(getActivity());

                // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
                DatabaseHelper handler = new DatabaseHelper(getActivity());
// Get access to the underlying writeable database
                SQLiteDatabase db = handler.getWritableDatabase();
// Query for items from the database and get a cursor back

                int jum=dbcenter.sumByDate(calendar.getText().toString());
                Integer intInstance = new Integer(jum);
                String numberAsString = intInstance.toString();

                Cursor todoCursor = handler.getListContents(calendar.getText().toString());

                // Find ListView to populate
                total.setText("Total Transaksi : Rp."+numberAsString+",-");
// Setup cursor adapter using cursor from last step
                TodoCursorAdapter todoAdapter = new TodoCursorAdapter(getActivity(), todoCursor);
// Attach cursor adapter to the ListView
                list.setAdapter(todoAdapter);

            }

        });

*/
    }



    public void refresh(View view)
    {
        // TodoDatabaseHandler is a SQLiteOpenHelper class connecting to SQLite
        DatabaseHelper handlerz = new DatabaseHelper(getActivity());
// Get access to the underlying writeable database
        SQLiteDatabase db = handlerz.getWritableDatabase();
// Query for items from the database and get a cursor back
        Cursor todoCursor = handlerz.getListContents(tanggal);

        // Find ListView to populate
        list = (ListView) view.findViewById(R.id.lists);
// Setup cursor adapter using cursor from last step
        todoAdapter = new TodoCursorAdapter(getActivity(),todoCursor);
// Attach cursor adapter to the ListView
        list.setAdapter(todoAdapter);

    }


}
