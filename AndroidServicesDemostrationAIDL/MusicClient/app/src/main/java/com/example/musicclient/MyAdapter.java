package com.example.musicclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Bundle songList; //data: the names displayed
    private RVClickListener RVlistener; //listener defined in main activity
    private Context context;

    /*
    passing in the data and the listener defined in the main activity
     */
    public MyAdapter(Bundle theList, RVClickListener listener, Context con){
        songList = theList;
        this.RVlistener = listener;
        context=con;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.rv_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listView, RVlistener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.songName.setText(songList.getStringArrayList("songsTitles").get(position));
        holder.artistName.setText(songList.getStringArrayList("songsArtist").get(position));

        Bitmap bitmap=null;
        try {
            String imagePath=songList.getStringArrayList("songImagesFiles").get(position);
            bitmap = BitmapFactory.decodeStream(context.openFileInput(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        holder.songImage.setImageBitmap(bitmap);
        holder.songImage.getLayoutParams().height=100;
        holder.songImage.getLayoutParams().width=100;


    }


    @Override
    public int getItemCount() {
        return songList.getStringArrayList("songsArtist").size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView songName;
        public TextView artistName;
        public ImageView songImage;
        private RVClickListener listener;
        private View itemView;


        public ViewHolder(@NonNull View itemView, RVClickListener passedListener) {
            super(itemView);
            songName = (TextView) itemView.findViewById(R.id.songTitle);
            artistName = (TextView) itemView.findViewById(R.id.songArtist);
            songImage = (ImageView) itemView.findViewById(R.id.songImage);
            this.itemView = itemView;
            this.listener = passedListener;

            itemView.setOnClickListener(this); //set short click listener
        }

        @Override
        public void onClick(View v) {
            System.out.println("======RVClickListener in ViewHolder called");
            listener.onClick(v, getAdapterPosition());
        }


    }
}
