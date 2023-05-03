package com.example.notes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.notes.Dao.NoteDao;
import com.example.notes.R;
import com.example.notes.database.NotesDatabase;
import com.example.notes.entities.Note;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_NOTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
            startActivityForResult(intent,REQUEST_CODE_ADD_NOTE);
            });

       getNotes();
       Log.i("om","ommm");

    }

   //  once we save the note now to get them data in MainActivity
    private void getNotes(){

        // same we getting data ny using AsyncTask will we run the task in background
        class GetNotesTask extends AsyncTask<Void, Void, List<Note>>{


            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase.getDatabase(getApplicationContext())
                        .noteDao().getAllNotes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);

                Log.i("GET_NOTE" , notes.toString());

            }
        }
        new GetNotesTask().execute();

    }


}