package com.example.tommy.stop_watch_real;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView menuContent = (ListView) findViewById(R.id.listContent);
        menuContent.setOnItemClickListener(new AdapterView.OnItemClickListener()  {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(MainActivity.this, SetTime.class);
                        startActivity(intent);
                        break;
                    case 1:
                        SQLiteOpenHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
                        Cursor cursor = db.query("RECORD", new String[] {"LIST"}, null, null,null,null,null);
                        if (cursor.moveToFirst()) {
                            Intent intent2 = new Intent(MainActivity.this, RecordList.class);
                            startActivity(intent2);}
                        else {Toast.makeText(MainActivity.this,"There isn't a single record there",Toast.LENGTH_SHORT).show();}
                        cursor.close();
                        break;
                    default: break;
                }



            }

            });
    }



}
