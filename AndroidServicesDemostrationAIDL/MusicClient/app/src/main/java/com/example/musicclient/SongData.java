package com.example.musicclient;

import android.graphics.Bitmap;

public class SongData {

    int songId;
    String songTitle;
    Bitmap songImage;
    String songUrl;

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public void setSongImage(Bitmap songImage) {
        this.songImage = songImage;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }


    public int getSongId() {
        return songId;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public Bitmap getSongImage() {
        return songImage;
    }

    public String getSongUrl() {
        return songUrl;
    }



}
