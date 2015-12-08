package com.informatika.rasp_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private FloatingActionButton btnToContact;
    private FloatingActionButton btnToLogout;
    private String username;
    public static String recvUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvWelcome = (TextView)findViewById(R.id.tvWelcome);
        btnToContact = (FloatingActionButton)findViewById(R.id.btnToContact);
        btnToLogout = (FloatingActionButton)findViewById(R.id.btnToLogout);

        username = getIntent().getStringExtra(recvUsername);
        tvWelcome.setText("Selamat Datang, " + username);

        btnToContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(intent);
            }
        });
        btnToLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
