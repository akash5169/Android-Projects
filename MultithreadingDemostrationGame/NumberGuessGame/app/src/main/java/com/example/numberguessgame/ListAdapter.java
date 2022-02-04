package com.example.numberguessgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListItem> {
        public ListAdapter(Context context, ArrayList<ListItem> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            ListItem listItem = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }
            // Lookup view for data population
            TextView oppGuess = (TextView) convertView.findViewById(R.id.oppGuess);
            TextView myResponse = (TextView) convertView.findViewById(R.id.myResponse);
            // Populate the data into the template view using the data object
            oppGuess.setText(listItem.OppGuess);
            myResponse.setText(listItem.myresponse);
            // Return the completed view to render on screen
            return convertView;
        }
}
