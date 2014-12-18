package com.skk.smartreminder.utils;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;

import com.skk.smartreminder.R;

/**
 * Created by sandeepkannan on 12/16/14.
 */
public class MyTabListener<T extends Fragment> implements ActionBar.TabListener {
        private  Class<T> mClass =null;
        private Fragment mFragment;
        private Context myCtx;
        private int id;

                // The contructor.

        public MyTabListener(Context ctx, Class<T> clz, int id) {
            myCtx = ctx;
            this.id =id;
            mClass = clz;
        }


        @Override
       public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (mFragment == null) {
                mFragment = Fragment.instantiate(myCtx, mClass.getName());
                ft.add(this.id,mFragment);
            } else {
                ft.show(mFragment);
            }
        }


        // When a tab is unselected, we have to hide it from the user's view.

        @Override
       public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            ft.hide(mFragment);
         }

     @Override
         public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }




}
