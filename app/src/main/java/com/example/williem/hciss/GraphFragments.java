package com.example.williem.hciss;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GraphFragments extends Fragment {

    int yearint=2017;
    String yearinc="2017";
String year="2017";


    Integer y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,y11,y12;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph_fragments, container, false);


        return rootView;
    }






    TextView v;
    GraphView graph;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        graph = (GraphView) view.findViewById(R.id.graph);
        //Button minus = (Button) view.findViewById(R.id.kurang);
        //Button plus = (Button) view.findViewById(R.id.tambah);
        v = (TextView) view.findViewById(R.id.graphicstext);

        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.getViewport().setDrawBorder(true);
        //

refresh();



    }


    void refresh()
    {
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        y1= dbHelper.sumByMonth("01",yearinc);
        y2= dbHelper.sumByMonth("02",yearinc);
        y3= dbHelper.sumByMonth("03",yearinc);
        y4= dbHelper.sumByMonth("04",yearinc);
        y5= dbHelper.sumByMonth("05",yearinc);
        y6= dbHelper.sumByMonth("06",yearinc);
        y7= dbHelper.sumByMonth("07",yearinc);
        y8= dbHelper.sumByMonth("08",yearinc);
        y9= dbHelper.sumByMonth("09",yearinc);
        y10= dbHelper.sumByMonth("10",yearinc);
        y11= dbHelper.sumByMonth("11",yearinc);
        y12= dbHelper.sumByMonth("12",yearinc);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[]{new DataPoint(1, y1),

        });
        series2.resetData(new DataPoint[]{new DataPoint(1, y1),
                new DataPoint(2, y2),
                new DataPoint(3, y3),
                new DataPoint(4, y4),
                new DataPoint(5, y5),
                new DataPoint(6, y6),
                new DataPoint(7, y7),
                new DataPoint(8, y8),
                new DataPoint(9, y9),
                new DataPoint(10, y10),
                new DataPoint(11, y11),
                new DataPoint(12, y12),});
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(12);

        series2.setDrawAsPath(true);
        series2.setAnimated(true);
        graph.addSeries(series2);
    }


    }


