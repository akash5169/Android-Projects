package com.example.musiccentral;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;

import com.example.Services.Common.ISongInfo;

public class SongsService extends Service {

//    HashMap <String,HashMap> allSongsInfo=new HashMap<>();
//    HashMap <Integer,String> songsTitles=new HashMap<>();
//    HashMap <Integer,Bitmap> songsImages=new HashMap<>();
//    HashMap <Integer,String> songsArtist=new HashMap<>();
//    HashMap <Integer,String> songsUrls=new HashMap<>();

    ArrayList<Bitmap> songsImages=new ArrayList<>();
    ArrayList<String> songsArtist=new ArrayList<>();
    ArrayList<String> songsTitles =new ArrayList<>();
    ArrayList<String> songsUrls =new ArrayList<>();
    Bundle allSongsInfo= new Bundle();
    Bundle songBundle= new Bundle();
    private final ISongInfo.Stub mBinder = new ISongInfo.Stub() {

        // Implement the remote method
        public Bundle getAllSongs()
        {

            synchronized (allSongsInfo) {
                allSongsInfo.putParcelableArrayList("songsImages", songsImages);
                allSongsInfo.putStringArrayList("songsArtist", songsArtist);
                allSongsInfo.putStringArrayList("songsTitles", songsTitles);
                allSongsInfo.putStringArrayList("songsUrls", songsUrls);
            }
            return allSongsInfo;
        }

        public Bundle getSong(int songNo){

            synchronized (songBundle) {
                songBundle.putInt("songNo", songNo);
                songBundle.putString("songTitle", songsTitles.get(songNo));
                songBundle.putString("songArtist", songsArtist.get(songNo));
                songBundle.putString("songUrls", songsUrls.get(songNo));
                songBundle.putParcelable("songImage", songsImages.get(songNo));
            }
            return songBundle;

        }

        public String getSongUrl(int songNo){
            return songsUrls.get(songNo);
        }

    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        System.out.println("=====OnBind was called====");
        initializeSongs();

        //System.out.println("===contents of mBinder");

        return mBinder;
    }

    void initializeSongs()
    {
        Bitmap songImage0 = BitmapFactory.decodeResource(getResources(),R.drawable.song0);
        Bitmap songImage1 = BitmapFactory.decodeResource(getResources(),R.drawable.song1);
        Bitmap songImage2 = BitmapFactory.decodeResource(getResources(),R.drawable.song2);
        Bitmap songImage3 = BitmapFactory.decodeResource(getResources(),R.drawable.song3);
        Bitmap songImage4 = BitmapFactory.decodeResource(getResources(),R.drawable.song4);
        Bitmap songImage5 = BitmapFactory.decodeResource(getResources(),R.drawable.song5);

        songsImages.add(songImage0);
        songsImages.add(songImage1);
        songsImages.add(songImage2);
        songsImages.add(songImage3);
        songsImages.add(songImage4);
        songsImages.add(songImage5);

        songsArtist.add("Drake");
        songsTitles.add("Ukulele");
        songsUrls.add("https://www.bensound.com/bensound-music/bensound-ukulele.mp3");

        songsArtist.add("Bruno Mars");
        songsTitles.add("Creative Minds");
        songsUrls.add("https://www.bensound.com/bensound-music/bensound-creativeminds.mp3");

        songsArtist.add("The Weekend");
        songsTitles.add("A New Beginning");
        songsUrls.add("https://www.bensound.com/bensound-music/bensound-anewbeginning.mp3");

        songsArtist.add("Justin Bieber");
        songsTitles.add("Little Idea");
        songsUrls.add("https://www.bensound.com/bensound-music/bensound-littleidea.mp3");

        songsArtist.add("Adele");
        songsTitles.add("Jazzy Frenchy");
        songsUrls.add("https://www.bensound.com/bensound-music/bensound-jazzyfrenchy.mp3");

        songsArtist.add("Linkin Park");
        songsTitles.add("Happy Rock");
        songsUrls.add("https://www.bensound.com/bensound-music/bensound-happyrock.mp3");


    }
}