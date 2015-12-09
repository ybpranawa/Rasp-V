package com.informatika.rasp_v;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.informatika.rasp_v.adapter.ContactAdapter;
import com.informatika.rasp_v.adapter.TabMainAdapter;

public class MainActivity extends AppCompatActivity {
    private String username;
    private ViewPager pager;
    private TabLayout tabs;
    public static String recvUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra(recvUsername);
        getSupportActionBar().setTitle("Hi, " + username + "!");

        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);

        pager.setAdapter(new TabMainAdapter(getSupportFragmentManager()));
        tabs.setTabTextColors(getResources().getColor(R.color.tabTextColorDeactive), getResources().getColor(R.color.tabTextColor));

        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
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
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
