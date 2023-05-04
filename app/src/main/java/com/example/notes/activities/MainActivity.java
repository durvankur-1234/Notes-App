package com.example.notes.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.notes.Dao.NoteDao;
import com.example.notes.R;
import com.example.notes.adapters.NotesAdapter;
import com.example.notes.database.NotesDatabase;
import com.example.notes.entities.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_NOTE = 1;

    // Recycler View
    private RecyclerView notesRecyclerView ;
    private List<Note> noteList;
    private NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener(view -> {

            Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
            startActivityForResult(intent,REQUEST_CODE_ADD_NOTE);
            });


        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        notesRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        // for Adapter
        noteList = new ArrayList<>();
        notesAdapter = new NotesAdapter(noteList);
        notesRecyclerView.setAdapter(notesAdapter);

       getNotes();


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

                if (noteList.size() == 0){
                    noteList.addAll(notes); // we saying if for know list is empty and there is nothing to show then inside roomDB
                     //take all the notes data which user stored so far and put inside this list
                    notesAdapter.notifyDataSetChanged();
                }else {
                /* if note list is not empty then it means we have the data so we are
                * just adding only the latest note to the noteList and notify adapter about new inserted , and last we scrolled
                * our recycelerview to the top*/
                    noteList.add(0,notes.get(0));
                    notesAdapter.notifyItemInserted(0);
                }

                notesRecyclerView.smoothScrollToPosition(0); // this we doo a latest note at the top



            }
        }
        new GetNotesTask().execute();

    }


    /*when method helps when use click on save data in CreateNoteActivity then at there we finish that intent
    * and passing data from there(RESULT_OK) like this now  this  method take that data and do changes in MainActivity*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK){
            getNotes();
        }
    }
}