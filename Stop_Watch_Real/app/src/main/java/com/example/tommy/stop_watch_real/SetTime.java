package com.example.tommy.stop_watch_real;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetTime extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

    }

    public void onButtonClick(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String content = editText.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            Intent confirm_intent = new Intent(SetTime.this, Timer.class);
            confirm_intent.putExtra("Minutes", content);
            startActivity(confirm_intent);
        }
        else {
            Toast message = Toast.makeText(SetTime.this, "Please Enter A Valid Number", Toast.LENGTH_SHORT);
            message.show();
            return;
        }

    }
}

