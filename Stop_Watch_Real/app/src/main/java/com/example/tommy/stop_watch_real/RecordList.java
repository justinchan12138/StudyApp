package com.example.tommy.stop_watch_real;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.AlphabeticIndex;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecordList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        final ListView temp = (ListView) findViewById(R.id.recordList);
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayAdapter<String> adapter;
        int totalHours = 0;
        int totalMinutes = 0;

        try {
            SQLiteOpenHelper dataBaseHelper = new DataBaseHelper(this);
            SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
            Cursor totalHoursCursor = db.query("RECORD", new String[]{"SUM(MINUTES) AS SUM"}, null, null, null, null, null);
            totalHoursCursor.moveToFirst();
            totalHours = totalHoursCursor.getInt(0)/60;
            totalMinutes = totalHoursCursor.getInt(0) %60;
            totalHoursCursor.close();
            Cursor cursor = db.query("RECORD", new String[] {"LIST"}, null, null,null,null,null);
            cursor.moveToFirst();

            do {
                String tempFinalValue = cursor.getString(0);
                arrayList.add(tempFinalValue);}while(cursor.moveToNext());

            adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            temp.setAdapter(adapter);
            cursor.close();
            db.close();}

        catch (SQLiteException e) {
            e.printStackTrace();
            Toast.makeText(RecordList.this, "Database not available", Toast.LENGTH_LONG).show();}

        temp.setLongClickable(true);
        temp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id) {

                AlertDialog.Builder alert = new AlertDialog.Builder(RecordList.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to delete record");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteOpenHelper dataBaseHelper = new DataBaseHelper(RecordList.this);
                        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
                        String temp_string = (String) temp.getItemAtPosition(position);
                        db.delete("RECORD", "LIST=?", new String[] {temp_string});
                        dialog.dismiss();
                        Cursor cursor = db.query("RECORD", new String[] {"LIST"}, null, null,null,null,null);
                        if (cursor.moveToFirst()) {
                            finish();
                            startActivity(getIntent());}
                        else {
                            Intent intent2 = new Intent(RecordList.this, MainActivity.class);
                            startActivity(intent2);
                            Toast.makeText(RecordList.this,"You just deleted all the records",Toast.LENGTH_LONG).show();
                            }
                        cursor.close();
                        db.close();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;
            }
        });

        TextView textView = (TextView) findViewById(R.id.textView4);

        textView.setText("You have studied for " + Integer.toString(totalHours) +" hours and " + Integer.toString(totalMinutes) + " minutes.");

    }








}
