package com.example.akashproject2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import com.example.akashproject2.MyAdapter;

public class MainActivity extends AppCompatActivity {

    RecyclerView thisView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView movieView=(RecyclerView) findViewById(R.id.recyclerView);
        thisView=movieView;
        Hashtable<Integer,String[]> ht=new Hashtable<>();

        ht.put(0,new String[]{"forestgump","Forest Gump","Robert Zemeckis","https://www.youtube.com/watch?v=eYSnxZKTZzU","https://en.wikipedia.org/wiki/Robert_Zemeckis","https://en.wikipedia.org/wiki/Forrest_Gump"});
        ht.put(1,new String[]{"flight","Flight","Robert Zemeckis","https://www.youtube.com/watch?v=DzV8R_86LX8","https://en.wikipedia.org/wiki/Robert_Zemeckis","https://en.wikipedia.org/wiki/Flight_(2012_film)"});
        ht.put(2,new String[]{"raanjhanaa","Raanjhana","Aanand L. Rai","https://www.youtube.com/watch?v=ER9vmhxFucg","https://en.wikipedia.org/wiki/Aanand_L._Rai","https://en.wikipedia.org/wiki/Raanjhanaa"});
        ht.put(3,new String[]{"rockstar","Rockstar","Imtiaz Ali","https://www.youtube.com/watch?v=bD5FShPZdpw","https://en.wikipedia.org/wiki/Imtiaz_Ali_(director)","https://en.wikipedia.org/wiki/Rockstar_(2011_film)"});
        ht.put(4,new String[]{"shawshank","Shawshank Redemption","Frank Darabont","https://www.youtube.com/watch?v=6hB3S9bIaco","https://en.wikipedia.org/wiki/Frank_Darabont","https://en.wikipedia.org/wiki/The_Shawshank_Redemption"});
        ht.put(5,new String[]{"pulpfiction","Pulp Fiction","Quentin Tarantino","https://www.youtube.com/watch?v=s7EdQ4FqbhY","https://en.wikipedia.org/wiki/Quentin_Tarantino","https://en.wikipedia.org/wiki/Pulp_Fiction"});
        ht.put(6,new String[]{"thor","Thor","Kenneth Branagh","https://www.youtube.com/watch?v=JOddp-nlNvQ","https://en.wikipedia.org/wiki/Kenneth_Branagh","https://en.wikipedia.org/wiki/Thor_(film)"});
        ht.put(7,new String[]{"shooter","Shooter","Antoine Fuqua","https://www.youtube.com/watch?v=natRMn81kiY","https://en.wikipedia.org/wiki/Antoine_Fuqua","https://en.wikipedia.org/wiki/Shooter_(2007_film)"});


        RVClickListener listener = (view,position)->{

        };

        MyAdapter adapter = new MyAdapter(ht, listener,getApplicationContext());
        movieView.setHasFixedSize(true);
        movieView.setAdapter(adapter);
        movieView.setLayoutManager(new GridLayoutManager(this,2));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.listView)
        {
            thisView.setLayoutManager(new LinearLayoutManager(this));
            return true;
        }
        else{
            thisView.setLayoutManager(new GridLayoutManager(this,2));
            return true;
        }

    }
}