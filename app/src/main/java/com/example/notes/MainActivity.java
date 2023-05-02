package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNote);
        imageAddNoteMain.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
            startActivityForResult(intent,REQUEST_CODE_ADD_NOTE);
            });


    }
}