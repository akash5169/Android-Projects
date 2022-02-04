package com.example.musicclient;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Services.Common.ISongInfo;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    protected static final String TAG = "SongServiceUser";

    protected static final int PERMISSION_REQUEST = 0;
    private boolean mIsBound = false;
    private ISongInfo mISongInfoService;
    Bundle songList;
    MainActivity con=this;
    Button viewAllSongs;
    public Intent intent;
    public TextView bindStatus;
    Button bindToService;
    Button unBindService;
    Button fetchSingle;
    Spinner spinner;
    int songtofetch;
    Intent musicServiceIntent;
    TextView singleSongArtist;
    TextView singleSongTitle;
    private final ServiceConnection mConnection=new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder iservice) {

            bindStatus.setText("Service Connected");
            mISongInfoService = ISongInfo.Stub.asInterface(iservice);
            mIsBound = true;

        }

        public void onServiceDisconnected(ComponentName className) {

            bindStatus.setText("Service Disconnected");
            mISongInfoService = null;

            mIsBound = false;

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindStatus = (TextView) findViewById(R.id.bindStatus);
        bindToService= (Button) findViewById(R.id.bindToService);
        unBindService=(Button) findViewById(R.id.unbindFromService);
        viewAllSongs=(Button) findViewById(R.id.viewAllSongs);
        spinner=(Spinner)findViewById(R.id.mySpinner);
        fetchSingle=(Button)findViewById(R.id.fetchSingleSong);
        singleSongArtist=(TextView)findViewById(R.id.singlesongArtist);
        singleSongTitle=(TextView)findViewById(R.id.singlesongTitle);

        bindStatus.setText("Service Not Bound to Music Central");

        viewAllSongs.setEnabled(false);
        unBindService.setEnabled(false);

        bindToService.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {

                    // Call KeyGenerator and get a new ID
                    if (mIsBound) {
                        songList=mISongInfoService.getAllSongs();
                        songList=convertParcel(songList);
                        bindStatus.setText("Service is bound");
                        disableOnBind();

                    } else {
                        checkBindingAndBind();
                        songList=mISongInfoService.getAllSongs();
                        songList=convertParcel(songList);
                        bindStatus.setText("Service is bound");
                        disableOnBind();

                    }

                } catch (RemoteException e) {

                    Log.e(TAG, e.toString());

                }
            }
        });

        fetchSingle.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                try {
                    Bundle bn=mISongInfoService.getSong(songtofetch);
                    singleSongTitle.setText(" Playing "+bn.getString("songTitle"));
                    singleSongArtist.setText(" By artist "+bn.getString("songArtist"));
                    linkOpener(bn.getString("songUrls"),getApplicationContext());

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        unBindService.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (mIsBound) {
                    try {
                        unbindService(mConnection);
                    }
                    catch (Exception e)
                    {
                        System.out.println("service is already disconnected");
                        e.printStackTrace();
                    }
                    disableOnUnbind();
                    bindStatus.setText("Service not bound");
                }
                if(musicServiceIntent!=null)
                {
                    stopService(musicServiceIntent);
                }
            }
        });

        viewAllSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    System.out.println("====="+mISongInfoService.getSongUrl(0));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Intent in=new Intent(getApplicationContext(),SongsRecyclerView.class);
                in.putExtra("songList",songList);
                startActivity(in);
            }
        });

        spinner.setOnItemSelectedListener(this);

        List<String> categories = new ArrayList<String>();
        categories.add("Song1");
        categories.add("Song2");
        categories.add("Song3");
        categories.add("Song4");
        categories.add("Song5");
        categories.add("Song6");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        System.out.println("Song Selected===="+position);
        songtofetch=position;
        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void checkBindingAndBind() {
        if (!mIsBound) {

            boolean b = false;
            Intent i = new Intent(ISongInfo.class.getName());
            ResolveInfo info = getPackageManager().resolveService(i, 0);
            i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
            intent=i;

            b = bindService(i,this.mConnection, Context.BIND_AUTO_CREATE);

            if (b) {
               bindStatus.setText("Service is connected");
                //Log.i(TAG, "====Ugo says bindService() succeeded!");
            } else {
                Log.i(TAG, "====Ugo says bindService() failed!");
            }
        }
    }


    // Bind to KeyGenerator Service
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        checkBindingAndBind();
        unBindService.setEnabled(false);
        viewAllSongs.setEnabled(false);
        fetchSingle.setEnabled(false);
        spinner.setEnabled(false);
        bindStatus.setText("Service not bound");
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

    // Unbind from KeyGenerator Service
    @Override
    protected void onStop() {
        super.onStop();
        try {
            unbindService(mConnection);
        }
        catch (Exception e)
        {
            System.out.println("service is already disconnected");
            e.printStackTrace();
        }
    }

    public Bundle convertParcel(Bundle songList)
    {
        ArrayList<String> songsArtist=songList.getStringArrayList("songsArtist");
        ArrayList<String> songsTitles=songList.getStringArrayList("songsTitles");
        ArrayList<String> songsUrls=songList.getStringArrayList("songsUrls");
        ArrayList<Parcelable> songImages=songList.getParcelableArrayList("songsImages");
        ArrayList<String> songImagesFiles=new ArrayList<>();

        int i=0;
        for (Parcelable image:songImages) {
            Bitmap bm=(Bitmap) image;

            String name="img"+i;
            String filename=createImageFromBitmap(bm,name);
            songImagesFiles.add(filename);
            i++;
        }

        Bundle bn=new Bundle();
        bn.putStringArrayList("songsArtist",songsArtist);
        bn.putStringArrayList("songsTitles",songsTitles);
        bn.putStringArrayList("songsUrls",songsUrls);
        bn.putStringArrayList("songImagesFiles",songImagesFiles);

        return bn;
    }

    public String createImageFromBitmap(Bitmap bitmap,String name) {
        String fileName = name;//no .png or .jpg needed
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            // remember close file output
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

    @Override
    public void onResume() {
        bindToService.setEnabled(true);

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void disableOnBind()
    {
        spinner.setEnabled(true);
        viewAllSongs.setEnabled(true);
        unBindService.setEnabled(true);
        fetchSingle.setEnabled(true);
        bindToService.setEnabled(false);
    }

    public void disableOnUnbind()
    {
        spinner.setEnabled(false);
        viewAllSongs.setEnabled(false);
        unBindService.setEnabled(false);
        fetchSingle.setEnabled(false);
        bindToService.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}