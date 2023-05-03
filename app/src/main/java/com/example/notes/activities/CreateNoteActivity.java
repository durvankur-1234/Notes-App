package com.example.notes.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.database.NotesDatabase;
import com.example.notes.entities.Note;

import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle , inputNoteSubtitle , inputNoteText ;
    private TextView txtDateTime;
    private ImageView imageSave;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        ImageView imageBack = findViewById(R.id.imageBack);
        // imgBack onClickListener
        imageBack.setOnClickListener(view -> onBackPressed());

        inputNoteTitle = findViewById(R.id.inputNoteTitle);
        inputNoteSubtitle = findViewById(R.id.inputNoteSubtitle);
        inputNoteText = findViewById(R.id.inputNote);
        txtDateTime = findViewById(R.id.textDataTime);
        imageSave = findViewById(R.id.imageSave);

        //putting date in txt
        txtDateTime.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm a", Locale.getDefault()).format(new Date()));

        imageSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });

    }

    //create a method
     public void saveNote() {

         if (inputNoteTitle.getText().toString().isEmpty()) {
             Toast.makeText(this, "Note Title cannot be Empty", Toast.LENGTH_LONG).show();
             return;
         } else if (inputNoteSubtitle.getText().toString().isEmpty()) {
             Toast.makeText(this, "Note Subtitle cannot be Empty", Toast.LENGTH_LONG).show();
             return;
         }


         // if they both a filled then saving in room
         final Note note = new Note();

         note.setTitle(inputNoteTitle.getText().toString());
         note.setSubtitle(inputNoteSubtitle.getText().toString());
         note.setNoteText(inputNoteText.getText().toString());
         note.setDateTime(txtDateTime.getText().toString());


        /** Room doesnt allow to save process and database operation in main thread its a heavy operation  so we
         * need to do saving process in background so we use Async task method*/


         @SuppressLint("StaticFieldLeak")
         class SaveNoteTask extends  AsyncTask<Void ,Void,Void> {


             @Override
             protected Void doInBackground(Void... voids) {
                   NotesDatabase.getDatabase(getApplicationContext()).noteDao().insertNotes(note);
                 return null;
             }

             @Override
             protected void onPostExecute(Void unused) {
                 super.onPostExecute(unused);

                 Intent intent = new Intent();
                 setResult(RESULT_OK,intent);
                finish();   // means when saving is done then that Activity will be finished
             }
         }
         new SaveNoteTask().execute();


    }

}