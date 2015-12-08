package com.informatika.rasp_v;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {
    private TextView tvContactUsername;
    private TextView tvContactPassword;
    public static String recvUsername = null;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvContactUsername = (TextView)findViewById(R.id.tvContactUsername);
        tvContactPassword = (TextView)findViewById(R.id.tvContactPassword);

        username = getIntent().getStringExtra(recvUsername);

        tvContactUsername.setText(username);
        tvContactPassword.setText("");
    }

}
