package com.example.williem.hciss;



import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    Button find;
    ListView list;
    String daftar[];

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        total = (TextView)view.findViewById(R.id.totaltransaksi);
        list = (ListView) view.findViewById(R.id.lists);
        calendar = (EditText) view.findViewById(R.id.calendarfragment_date);
        find = (Button) view.findViewById(R.id.findtanggal);

        find.setBackgroundColor(Color.BLUE);
        find.setTextColor(Color.WHITE);
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


    }

    Calendar myCalendar = Calendar.getInstance();

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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String date = sdf.format(Calendar.getInstance().getTime());
        calendar.setText(sdf.format(myCalendar.getTime()));
    }


}
