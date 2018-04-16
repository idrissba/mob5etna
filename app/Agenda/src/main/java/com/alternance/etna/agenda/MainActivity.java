package com.alternance.etna.agenda;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.alternance.etna.agenda.Class.MyCalendar;
import com.alternance.etna.agenda.Fragment.CalendarFragment;
import com.alternance.etna.agenda.Fragment.HomeFragment;
import com.alternance.etna.agenda.Fragment.ContactFragment;
import com.alternance.etna.agenda.Manager.UserData;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mBottomNav;

    private ViewPager mViewPager;


    //Fragments
    HomeFragment mfHome;
//    EventFragment mfEvents;
    ContactFragment mfContacts;
    CalendarFragment mfCalendar;
    MenuItem prevMenuItem;

    private boolean IsLogOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            if (savedInstanceState != null) {
                IsLogOn = savedInstanceState.getBoolean("IsLogOn", false);
            }

            if (IsLogOn == false) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
            }

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //Initializing viewPager
            mViewPager = (ViewPager) findViewById(R.id.viewpager);

            //Initializing the bottomNavigationView
            mBottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);

            mBottomNav.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.action_home:
                                    mViewPager.setCurrentItem(0);
                                    break;
                                case R.id.action_events:
                                    mViewPager.setCurrentItem(1);
                                    break;
                                case R.id.action_contacts:
                                    mViewPager.setCurrentItem(2);
                                    break;
                            }
                            return false;
                        }
                    });

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (prevMenuItem != null) {
                        prevMenuItem.setChecked(false);
                    } else {
                        mBottomNav.getMenu().getItem(0).setChecked(false);
                    }
//                Log.d("page", "onPageSelected: "+position);
                    mBottomNav.getMenu().getItem(position).setChecked(true);
                    prevMenuItem = mBottomNav.getMenu().getItem(position);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

       /*  //Disable ViewPager Swipe
       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
        */

            setupViewPager(mViewPager);
        }
        catch (Exception exp){
            Log.e("Exception", "MainActivity:OnCreate => " + exp.getMessage());
            //Toast.makeText(getContext(), "EventFragment:Update", Toast.LENGTH_LONG);
        }
    }

    /*
     * Cette fonction permet de sauvegarder des elements dans un tableau clé-valeur
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("IsLogOn", IsLogOn);
        super.onSaveInstanceState(outState);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        try {
            ViewPagerBase adapter = new ViewPagerBase(getSupportFragmentManager());
            mfHome = new HomeFragment();
//            mfEvents = new EventFragment();
            mfContacts = new ContactFragment();


            MyCalendar c = new MyCalendar(getString(R.string.MainActivity_my_agenda_text), null);//UserData.getInstance().getEventList());
            mfCalendar = CalendarFragment.MyCalendarFragment(c, true);

            adapter.addFragment(mfHome);
    //        adapter.addFragment(mfEvents);
            adapter.addFragment(mfCalendar);
            adapter.addFragment(mfContacts);
            viewPager.setAdapter(adapter);
        }catch(Exception exp){
            Log.e("Exception", "MainActivity:setupViewPager => " + exp.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    /*
     * Cette fonction permet de traiter les actions de l'actionbar
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            Intent intent;
            String packageName = "com.google.android.apps.maps";
            switch (item.getItemId()) {
                case R.id.action_logout://Deconnexion
                    IsLogOn = false;
                    Intent i = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(i);
                    return true;

                case R.id.action_share: //Partage de l'application
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.MainActivity_share_text) + ":\nhttp://play.google.com/store/apps/details?id=" + packageName);
                    startActivity(Intent.createChooser(intent, getString(R.string.MainActivity_share_title)));
                    return true;

                case R.id.action_rate: //Noter l'application
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("market://details?id=" + packageName));
                    startActivity(intent);
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }
        catch (Exception exp){
            return false;
        }
    }

}
