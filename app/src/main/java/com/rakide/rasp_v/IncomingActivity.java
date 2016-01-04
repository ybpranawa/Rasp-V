package com.rakide.rasp_v;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.sip.SipAudioCall;
import android.net.sip.SipProfile;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class IncomingActivity extends AppCompatActivity {
    private ImageButton btnUpCallDialling;
    private ImageButton btnEndCallDialling;
    private TextView tvDiallingUsername;

    private String sipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming);

        btnUpCallDialling = (ImageButton) findViewById(R.id.btnUpCallDialling);
        btnEndCallDialling = (ImageButton) findViewById(R.id.btnEndCallDialling);

        btnUpCallDialling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(incomingActivity.this, "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(IncomingActivity.this, CallingActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
