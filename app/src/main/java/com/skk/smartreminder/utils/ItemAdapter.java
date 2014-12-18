package com.skk.smartreminder.utils;

/**
 * Created by sandeepkannan on 12/16/14.
 */
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skk.smartreminder.R;

public class ItemAdapter extends ArrayAdapter<ReminderBean> implements  View.OnCreateContextMenuListener
 {

    // declaring our ArrayList of items
    private ArrayList<ReminderBean> objects;

    /* here we must override the constructor for ArrayAdapter
    * the only variable we care about now is ArrayList<Item> objects,
    * because it is the list of objects we want to display.
    */
    public ItemAdapter(Context context, int textViewResourceId, ArrayList<ReminderBean> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
    }

    /*
     * we are overriding the getView method here - this is what defines how each
     * list item will look.
     */
    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
        final ReminderBean i = objects.get(position);

        if (i != null) {
            TextView hiddenId = (TextView) v.findViewById(R.id.LV_id);
            TextView todo = (TextView) v.findViewById(R.id.LV_todo);
            TextView location = (TextView) v.findViewById(R.id.LV_LocationName);
            ImageView image = (ImageView) v.findViewById(R.id.LV_imageView);


            // check to see if each individual textview is null.
            // if not, assign some text!
            hiddenId.setText(i.getDbId());
            if (i.getTodo() != null){
                todo.setText(i.getTodo());
            }
           if (i.getLocation() != null) {
               location.setText(i.getLocation());
               if ("Y".equals(i.getNotified())) {
                   image.setImageResource(R.drawable.images_location_red);

               } else {
                   image.setImageResource(R.drawable.images_location_green);
               }
                //image.setImageResource(R.drawable.map_images);
           } else {

               if ("Y".equals(i.getNotified())) {
                   image.setImageResource(R.drawable.images_clock_red);

               } else {
                   image.setImageResource(R.drawable.images_clock_green);
               }
           }

           // v.setOnCreateContextMenuListener(this);

        }

        // the view must be returned to our activity
        return v;

    }


  public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

    }
}
