package com.example.derrick.p10_ps;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String USER_PREF = "USER_PREF";
    ViewPager viewPager;
    Button btnRead;
    SharedPreferences sp;
    ArrayList<Fragment> al;
    MyFragmentPagerAdapter adapter;
    AlarmManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.viewPager);
        btnRead = findViewById(R.id.btnRead);
        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
        FragmentManager fm = getSupportFragmentManager();
        al = new ArrayList<Fragment>();
        al.add(new Frag1());
        al.add(new Frag2());
        al.add(new Frag3());

        adapter = new MyFragmentPagerAdapter(fm, al);
        viewPager.setAdapter(adapter);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MINUTE, 5);

                //Create a new PendingIntent and add it to the AlarmManager
                Intent intent = new Intent(MainActivity.this,
                        MainActivity.class);
                int reqCode = 12345;
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(MainActivity.this,
                                reqCode, intent,
                                PendingIntent.FLAG_CANCEL_CURRENT);

                // Get AlarmManager instance
                am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);

                // Set the alarm
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("currentFrag", viewPager.getCurrentItem());
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int currFrag = sp.getInt("currentFrag",0);
        viewPager.setCurrentItem(currFrag);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.actionNext:
                // Toast.makeText(this, "Selected Next", Toast.LENGTH_SHORT).show();
                nextFrag();
                return true;
            case R.id.actionPrevious:
                // Toast.makeText(this, "Selected Previous", Toast.LENGTH_SHORT).show();
                previousFrag();
                return true;
            case R.id.actionRandom:
                // Toast.makeText(this, "Selected Random", Toast.LENGTH_SHORT).show();
                randomFrag();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void nextFrag(){
        int max = viewPager.getChildCount();
        if (viewPager.getCurrentItem() < max - 1){
            int nextPage = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(nextPage,true);
        } else {
            Toast.makeText(this, "This is the last page!", Toast.LENGTH_SHORT).show();
        }
    }
    public void previousFrag(){
        if (viewPager.getCurrentItem() > 0){
            int previousPage = viewPager.getCurrentItem() - 1;
            viewPager.setCurrentItem(previousPage, true);
        } else{
            Toast.makeText(this, "This is the first page!", Toast.LENGTH_SHORT).show();
        }
    }
    public void randomFrag(){
        int max = viewPager.getChildCount() -1;
        int min = 0;
        int random = new Random().nextInt((max - min) + 1) + min;
        Log.i("Random", Integer.toString(random));
        viewPager.setCurrentItem(random,true);
    }



}
