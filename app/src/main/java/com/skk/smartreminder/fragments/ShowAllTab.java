package com.skk.smartreminder.fragments;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.skk.smartreminder.utils.GeoFenceConsants;
import com.skk.smartreminder.utils.ItemAdapter;
import com.skk.smartreminder.utils.ReminderBean;
import com.skk.smartreminder.activities.ShowReminder;
import com.skk.smartreminder.utils.DatabaseHelper;
import com.skk.smartreminder.geofence.GeoFenceMain;
import com.skk.smartreminder.R;
import com.skk.smartreminder.utils.ReminderSwipeGesture;

import java.util.ArrayList;

/**
 * Created by sandeepkannan on 12/16/14.
 */


public class ShowAllTab extends ListFragment {
    ItemAdapter mAdapter;
    ArrayList<ReminderBean> mBeans;


public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mBeans= DatabaseHelper.getInstance(getActivity()).loadAllReminders();
    mAdapter = new ItemAdapter(getActivity(),R.layout.list_item,mBeans);
    setListAdapter(mAdapter);
    getListView(). setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView pos = (TextView)view.findViewById(R.id.LV_id);
            view.setSelected(true);
            String index = pos.getText().toString();
            Intent showReminder = new Intent(getActivity(), ShowReminder.class);
            showReminder.putExtra(GeoFenceConsants.NOTIFICATION_ID, index);
            startActivity(showReminder);
        }
    });
   registerForContextMenu(getListView());
    /*getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            boolean deleteGeofence = true;
            // do something with the data
            TextView pos = (TextView) view.findViewById(R.id.LV_id);
            if (((TextView) view.findViewById(R.id.LV_LocationName)).getText().equals("")) {
                deleteGeofence = false;
            }
            String index = pos.getText().toString();
            deleteItemFromList(index,deleteGeofence,i);
            return true;
        }
    });*/


}

  /*  @Override
   public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
        TextView pos = (TextView)v.findViewById(R.id.LV_id);
       // v.setSelected(true);
        String index = pos.getText().toString();
        Intent showReminder = new Intent(getActivity(), ShowReminder.class);
        showReminder.putExtra(GeoFenceConsants.NOTIFICATION_ID, index);
        startActivity(showReminder);
    }*/

    public void deleteItemFromList(String id,final boolean deleteGeofence,final int position ) {
        final Long dbId = Long.parseLong(id);
        AlertDialog.Builder alert = new AlertDialog.Builder(
                getActivity());

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this reminder?");

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAdapter.remove(mBeans.get(position));
                mAdapter.notifyDataSetChanged();
                DatabaseHelper.getInstance(getActivity()).deleteData(dbId);
                if (deleteGeofence) {
                    GeoFenceMain geoFenceMain = new GeoFenceMain();
                    geoFenceMain.removeGeoFence(getActivity(), dbId.toString());
                }

            }

        });


        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        if (item.getTitle().equals("Delete")) {
            long id = menuInfo.id;
            TextView tv = (TextView) menuInfo.targetView.findViewById(R.id.LV_id);
            TextView location = (TextView) menuInfo.targetView.findViewById(R.id.LV_LocationName);

            DatabaseHelper.getInstance(getActivity()).deleteData(Long.parseLong(tv.getText().toString()));
            mAdapter.remove(mBeans.get(menuInfo.position));
            mAdapter.notifyDataSetChanged();
            if (location !=null && !location.toString().isEmpty()) {
               GeoFenceMain geoFenceMain = new GeoFenceMain();
               geoFenceMain.removeGeoFence(getActivity(), tv.getText().toString());
            }
        }

        return super.onContextItemSelected(item);

    }

}




