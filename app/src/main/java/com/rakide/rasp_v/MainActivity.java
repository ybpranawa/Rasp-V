package com.rakide.rasp_v;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rakide.rasp_v.adapter.TabMainAdapter;


public class MainActivity extends AppCompatActivity {
    private String username;
    private ViewPager pager;
    private TabLayout tabs;

    public static String recvUsername = null;
    public static final String SIP_PREF = "SIP_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(SIP_PREF, MODE_PRIVATE);

        username = prefs.getString("username","");

        getSupportActionBar().setTitle("Hi, " + username + " !");
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);

        pager.setAdapter(new TabMainAdapter(getSupportFragmentManager()));
        tabs.setTabTextColors(getResources().getColor(R.color.tabTextColorDeactive), getResources().getColor(R.color.tabTextColor));
//        tabs.setTabTextColors(ContextCompat.getColor(context,R.color.tabTextColorDeactive), getResources().getColor(R.color.tabTextColor));

        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
//
//
//
//        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Contact row = (Contact) adapterView.getItemAtPosition(i);
//                Toast.makeText(MainActivity.this, row.displayName, Toast.LENGTH_SHORT).show();
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
