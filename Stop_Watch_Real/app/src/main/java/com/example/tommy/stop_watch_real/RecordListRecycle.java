package com.example.tommy.stop_watch_real;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RecordListRecycle extends Activity {

        private RecyclerView mRecyclerView;
        private RecordListAdapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        int totalHours = 0;
        int totalMinutes = 0;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_record_list_recycle);

            try {
                SQLiteOpenHelper dataBaseHelper = new DataBaseHelper(this);
                SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
                Cursor totalHoursCursor = db.query("RECORD", new String[]{"SUM(MINUTES) AS SUM"}, null, null, null, null, null);
                totalHoursCursor.moveToFirst();
                totalHours = totalHoursCursor.getInt(0)/60;
                totalMinutes = totalHoursCursor.getInt(0) %60;
                totalHoursCursor.close();
                db.close();}

            catch (SQLiteException e) {
                e.printStackTrace();
                Toast.makeText(RecordListRecycle.this, "Database not available", Toast.LENGTH_LONG).show();}

            TextView tempTextView = (TextView) findViewById(R.id.TextViewForList);
            tempTextView.setText("Total Time: " + Integer.toString(totalHours) + " Hours "+
                    Integer.toString(totalMinutes) + " Minutes");





            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mRecyclerView.setHasFixedSize(false);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new RecordListAdapter(RecordListRecycle.this);
            mAdapter.setterForAdapter(mAdapter);
            mRecyclerView.setAdapter(mAdapter);



        }

}