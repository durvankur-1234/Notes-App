package com.example.notes.adapters;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.entities.Note;
import com.makeramen.roundedimageview.RoundedImageView;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Note> notes ;

    //constructor
    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_notes,parent,false);

        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        holder.setNote(notes.get(position));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    //inner class name:ViewHolder
    static class NotesViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle , txtSubTitle, txtDateTime ;
        LinearLayout layoutNote;
        ImageView imageNote ;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtNATitle);
            txtSubTitle=itemView.findViewById(R.id.txtNASubtitle);
            txtDateTime = itemView.findViewById(R.id.txtNADataTime);
            layoutNote = itemView.findViewById(R.id.layoutNote);
            imageNote = itemView.findViewById(R.id.imageNote);

        }

        void setNote(Note note){
            //for text
            txtTitle.setText(note.getTitle());
            if (note.getSubtitle().trim().isEmpty()){
                txtSubTitle.setVisibility(View.GONE);
            }else {
                txtSubTitle.setText(note.getSubtitle());
            }
            txtDateTime.setText(note.getDateTime());


            // if note color empty or note
            GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground();
            if (note.getColor() != null ){
                gradientDrawable.setColor(Color.parseColor(note.getColor().trim()));
            }else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }


            // for image
            if (note.getImagePath() != null){
               // imageNote.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imageNote.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imageNote.setVisibility(View.VISIBLE);
            }else {
                imageNote.setVisibility(View.GONE);
            }

        }

    }
}
