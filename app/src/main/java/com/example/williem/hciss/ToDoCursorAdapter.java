package com.example.williem.hciss;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Williem on 12/2/2017.
 */

class TodoCursorAdapter extends CursorAdapter {

    int priority;
    public TodoCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_todo, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
        TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        TextView tvNote = (TextView) view.findViewById(R.id.tvNotes);
        TextView tvType = (TextView) view.findViewById(R.id.tvType);
        // Extract properties from cursor
        String body = cursor.getString(cursor.getColumnIndexOrThrow("D_TITLE"));
        priority = cursor.getInt(cursor.getColumnIndexOrThrow("D_VALUE"));
        String date = cursor.getString(cursor.getColumnIndexOrThrow("D_DATE"));
        String note = cursor.getString(cursor.getColumnIndexOrThrow("D_NOTE"));

        Integer type = cursor.getInt(cursor.getColumnIndexOrThrow("D_TYPE"));

if (type==1)
{
    // Populate fields with extracted properties
    tvBody.setText(body);
    tvBody.setTextColor(Color.RED);
    tvType.setText("Pengeluaran");
    tvType.setTextColor(Color.RED);
    tvPriority.setText("Rp."+String.valueOf(priority)+",-");
    tvDate.setText(date);
    tvNote.setText(note);
}

else {
    // Populate fields with extracted properties
    tvBody.setText(body);
    tvBody.setTextColor(Color.GREEN);
    tvType.setText("Pemasukan");
    tvType.setTextColor(Color.GREEN);
    tvPriority.setText("Rp."+String.valueOf(priority)+",-");
    tvDate.setText(date);
    tvNote.setText(note);
}


    }
}