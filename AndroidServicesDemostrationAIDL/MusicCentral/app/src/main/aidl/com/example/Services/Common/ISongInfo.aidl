// ISongInfo.aidl
package com.example.Services.Common;

// Declare any non-default types here with import statements

interface ISongInfo {

    Bundle getAllSongs();

    Bundle getSong(int songNo);

    String getSongUrl(int songNo);
}