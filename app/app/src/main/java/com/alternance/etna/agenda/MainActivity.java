package com.alternance.etna.agenda;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.alternance.etna.agenda.Fragment.EventFragment;
import com.alternance.etna.agenda.Fragment.HomeFragment;
import com.alternance.etna.agenda.Fragment.ContactFragment;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mBottomNav;

    private ViewPager mViewPager;


    //Fragments
    HomeFragment mfHome;
    EventFragment mfEvents;
    ContactFragment mfContacts;
    MenuItem prevMenuItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing viewPager
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        mBottomNav = (BottomNavigationView)findViewById(R.id.bottom_navigation);

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
                }
                else
                {
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

    private void setupViewPager(ViewPager viewPager)
    {

        ViewPagerBase adapter = new ViewPagerBase(getSupportFragmentManager());
        mfHome = new HomeFragment();
        mfEvents = new EventFragment();
        mfContacts = new ContactFragment();

        adapter.addFragment(mfHome);
        adapter.addFragment(mfEvents);
        adapter.addFragment(mfContacts);
        viewPager.setAdapter(adapter);
    }
}
