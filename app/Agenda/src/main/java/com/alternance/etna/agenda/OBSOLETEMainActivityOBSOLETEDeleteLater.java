package com.alternance.etna.agenda;

//import android.support.v7.app.FragmentManager;
import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.Menu;

public class OBSOLETEMainActivityOBSOLETEDeleteLater extends AppCompatActivity {

    private TextView mTextMessage;


    // A modifier de maniere a utiliser des intents (par defaut, c'est seulement le text qui change ;(   )

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item)
//    {
//            Intent intent = new Intent(this);
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//    };


    protected void ProcessIntent(BottomNavigationView.OnNavigationItemSelectedListener listener)
    {
        Intent intent = new Intent(this, OBSOLETEMainActivityOBSOLETEDeleteLater.class);
        return ;
    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mTextMessage = (TextView) findViewById(R.id.message);
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
      //  showEditEventDialog();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    private void showEditEventDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditEventDialogFragment editEventDialogFragment = EditEventDialogFragment.newInstance("Some EventEntity");
        editEventDialogFragment.show(fm, "fragment_edit_event_dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}
