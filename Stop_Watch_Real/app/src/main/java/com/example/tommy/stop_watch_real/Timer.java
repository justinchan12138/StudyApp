package com.example.tommy.stop_watch_real;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer extends AppCompatActivity {
    private boolean running;
    private int second; // Get the value from the intent
    private int minutes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        if(savedInstanceState != null){
            second = savedInstanceState.getInt("second");
            minutes = savedInstanceState.getInt("minutes");
            running = savedInstanceState.getBoolean("running");
            startTimer();
        }
        else {
        Intent intent = getIntent();
        String content = intent.getStringExtra("Minutes");
        minutes = Integer.parseInt(content);
        second = minutes * 60;
        startTimer();
    }}
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("second",second);
        savedInstanceState.putInt("minutes",minutes);
        savedInstanceState.putBoolean("running",running);
    }
    public void onStart (View view) {running = true;}
    public void onStop (View view)  {running = false;}
    public void onReset (View view) {
        second = minutes * 60;
        running = false;
    }
    boolean pressedTwice = false;
    public void onBackPressed()
    {
        if (pressedTwice == true) {Timer.super.onBackPressed();
            return;
        }
        pressedTwice = true;
        Toast toast = Toast.makeText(Timer.this, "Are you sure you want to exit?", Toast.LENGTH_SHORT);
        toast.show();
        new Handler().postDelayed(new Runnable() {

            public void run(){
                pressedTwice = false;
            }
        },3000);


    }

    // learn more about this !!!

    public void startTimer () {
        final TextView textview = (TextView) findViewById(R.id.textView3); //Anonymous inner classes may only access variables of the enclosing method that are final.
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minute3 = second/60;
                String minutes2 = String.format("%02d", minute3);
                int seconds = second % 60;
                String secs = String.format("%02d", seconds);

                String time = ("" + minutes2 + ":" + secs);
                textview.setText(time);
                if (running == true){
                    if (second == 0) {
                        running = false;
                        Date date = new Date();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        String tempDate = dateFormat.format(date);
                        SQLiteOpenHelper dataBasehelper = new DataBaseHelper(Timer.this);
                        try{
                            SQLiteDatabase db = dataBasehelper.getWritableDatabase();
                            ContentValues newRecordValue = new ContentValues();
                            newRecordValue.put("DATE", tempDate);
                            newRecordValue.put("MINUTES", minutes);
                            String temp_minutes_string = String.format("%03d", minutes);
                            String temp_string = tempDate + "         " + "| " + temp_minutes_string + " Minutes" + "  |";

                            newRecordValue.put("LIST", temp_string);
                            db.insert("RECORD", null, newRecordValue);
                            db.close();}
                        catch (SQLiteException e) {
                            e.printStackTrace();
                            Toast.makeText(Timer.this, "Congrats", Toast.LENGTH_SHORT).show();
                        }

                        return;

                    // Update the database AND send a message to congratulate the user
                    }
                    --second;
                }
                handler.postDelayed(this,1000);
            }
        });



    }


}

