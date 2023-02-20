package com.example.appspavi.gym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class ListViewAdapter1 extends ArrayAdapter<Hero1> {

    //the hero list that will be displayed
    private List<Hero1> heroList;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public ListViewAdapter1(List<Hero1> heroList, Context mCtx) {
        super(mCtx, R.layout.update, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.update, null, true);

        //getting text views
        TextView name = listViewItem.findViewById(R.id.namev);
        TextView date = listViewItem.findViewById(R.id.datev);
        TextView amt = listViewItem.findViewById(R.id.amtv);
        Hero1 hero = heroList.get(position);
        name.setText(hero.getName());
        date.setText(hero.getDate());
        amt.setText(hero.getAmt());

        return listViewItem;

    }
}