package com.example.appspavi.gym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Hero> {

    //the hero list that will be displayed
    private List<Hero> heroList;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public ListViewAdapter(List<Hero> heroList, Context mCtx) {
        super(mCtx, R.layout.view, heroList);
        this.heroList = heroList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.view, null, true);

        //getting text views
        TextView date = listViewItem.findViewById(R.id.vdate);
        TextView in = listViewItem.findViewById(R.id.intime);
        TextView out = listViewItem.findViewById(R.id.outtime);
        Hero hero = heroList.get(position);
        date.setText(hero.getDate());
        in.setText(hero.getIn());
        out.setText(hero.getOut());

        return listViewItem;

    }
}