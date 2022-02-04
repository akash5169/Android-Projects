package com.example.akashproject2;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Hashtable;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Hashtable<Integer,String[]> movieList; //data: the names displayed
    private RVClickListener RVlistener; //listener defined in main activity
    private Context context;

    /*
    passing in the data and the listener defined in the main activity
     */
    public MyAdapter(Hashtable<Integer,String[]> theList, RVClickListener listener, Context con){
        movieList = theList;
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


        holder.movieName.setText(movieList.get(position) [1]);
        holder.dirName.setText(movieList.get(position) [2]);

        String uri = "@drawable/"+movieList.get(position) [0];  // where myresource (without the extension) is the file

        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());

        Drawable res = context.getResources().getDrawable(imageResource);

        holder.movImage.setImageDrawable(res);
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        context = recyclerView.getContext();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        public TextView movieName;
        public TextView dirName;
        public ImageView movImage;
        private RVClickListener listener;
        private View itemView;


        public ViewHolder(@NonNull View itemView, RVClickListener passedListener) {
            super(itemView);
            movieName = (TextView) itemView.findViewById(R.id.movName);
            dirName = (TextView) itemView.findViewById(R.id.dirName);
            movImage = (ImageView) itemView.findViewById(R.id.movImage);
            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this); //set context menu for each list item (long click)
            this.listener = passedListener;

            itemView.setOnClickListener(this); //set short click listener
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());

            linkOpener(movieList.get(getAdapterPosition()) [3],v.getContext());

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            //inflate menu from xml

            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.context_menu, menu );
            menu.getItem(0).setOnMenuItemClickListener(onMenu);
            menu.getItem(1).setOnMenuItemClickListener(onMenu);
            menu.getItem(2).setOnMenuItemClickListener(onMenu);

        }

        /*
            listener for menu items clicked
         */
        private final MenuItem.OnMenuItemClickListener onMenu = new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){

                Log.i("ON_CLICK=======", movieName.getText() + " adapter pos: " + getAdapterPosition());

                if(R.id.movieClip==item.getItemId())
                {
                    linkOpener(movieList.get(getAdapterPosition()) [3],context);
                }
                else if(R.id.movieWiki==item.getItemId())
                {
                    linkOpener(movieList.get(getAdapterPosition()) [5],context);
                }
                else if(R.id.dirWiki==item.getItemId())
                {
                    linkOpener(movieList.get(getAdapterPosition()) [4],context);
                }
                return true;
            }
        };

        public void linkOpener(String link, Context con)
        {
            Uri aUri = Uri.parse(link) ;
            Intent aIntent = new Intent(Intent.ACTION_VIEW);
            aIntent.setData(aUri) ;
            aIntent.addCategory(Intent.CATEGORY_BROWSABLE) ;
            if(aIntent.resolveActivity(context.getPackageManager()) != null) {
                con.startActivity(aIntent);
            }
        }
    }
}
