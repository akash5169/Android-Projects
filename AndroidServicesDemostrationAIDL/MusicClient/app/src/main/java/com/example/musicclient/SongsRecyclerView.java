package com.example.musicclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class SongsRecyclerView extends AppCompatActivity {

    RecyclerView thisView;
    Intent musicServiceIntent;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_recycler_view);

        RecyclerView songView=(RecyclerView) findViewById(R.id.recyclerView);
        thisView=songView;

        Bundle songList=getIntent().getBundleExtra("songList");

        RVClickListener listener = (view, position) -> {
            System.out.println("======RVClickListener in SongsRecyclerView called");
            linkOpener(songList.getStringArrayList("songsUrls").get(position),this);
            view.setSelected(true);
        };

        MyAdapter adapter = new MyAdapter(songList, listener,getApplicationContext());
        songView.setHasFixedSize(true);
        songView.setAdapter(adapter);
        songView.setLayoutManager(new LinearLayoutManager(this));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void linkOpener(String link, Context con)
    {
        System.out.println("======link opener called");

        if(musicServiceIntent!=null)
        {
            stopService(musicServiceIntent);

        }
        musicServiceIntent= new Intent(con, MusicService.class);

        musicServiceIntent.putExtra("SongUrl",link);
        startForegroundService(musicServiceIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(musicServiceIntent!=null)
        {
            stopService(musicServiceIntent);

        }
    }
}