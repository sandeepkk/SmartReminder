package com.skk.smartreminder.activities;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.skk.smartreminder.utils.MyTabListener;
import com.skk.smartreminder.fragments.ShowAllTab;
import com.skk.smartreminder.fragments.ShowMapTab;
import com.skk.smartreminder.R;

public class HomeActivity extends Activity  {
    // Declaring our tabs and the corresponding fragments.

    ActionBar.Tab allTab, mapTab;
    Fragment bmwFragmentTab = new ShowAllTab();
    Fragment toyotaFragmentTab = new ShowMapTab();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("");
        setContentView(R.layout.activity_home);
        // Asking for the default ActionBar element that our platform supports.
        ActionBar actionBar = getActionBar();
        // Screen handling while hiding ActionBar icon.
        actionBar.setDisplayShowHomeEnabled(true);
        // Screen handling while hiding Actionbar title.
       // actionBar.setDisplayShowTitleEnabled(true);
        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Setting custom tab icons.
        allTab = actionBar.newTab().setText("All Reminders");
        mapTab = actionBar.newTab().setText("Map View");

        // Setting tab listeners.
        allTab.setTabListener(new MyTabListener(getApplicationContext(),ShowAllTab.class,R.id.activity_home));
        mapTab.setTabListener(new MyTabListener(getApplicationContext(),ShowMapTab.class,R.id.activity_home));
        // Adding tabs to the ActionBar.
        actionBar.addTab(allTab);
        actionBar.addTab(mapTab);
      //  actionBar.addTab(fordTab);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
        case R.id.add_reminder:
                Intent intent = new Intent(this, NewReminder.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
