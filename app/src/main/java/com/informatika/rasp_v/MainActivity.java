package com.informatika.rasp_v;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.informatika.rasp_v.adapter.ContactAdapter;

public class MainActivity extends AppCompatActivity {
    private ListView lvContact;

    private String username;
    public static String recvUsername = null;

    public static String[] listUsername = {
            "fanyagriansyah","ybpranawa","kharismana","novanindaovari","nindyasaridu",
            "yohanaseul","demsyiman","imaginearabella",
            "fanyagriansyah","ybpranawa","kharismana","novanindaovari","nindyasaridu",
            "yohanaseul","demsyiman","imaginearabella"
    };
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra(recvUsername);
        getSupportActionBar().setTitle("Hi, "+username+"!");

        lvContact = (ListView) findViewById(R.id.lvContact);

        context = this;
        lvContact.setAdapter(new ContactAdapter(MainActivity.this, listUsername));
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
