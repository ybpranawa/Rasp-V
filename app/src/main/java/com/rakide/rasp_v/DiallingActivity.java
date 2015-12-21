package com.rakide.rasp_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DiallingActivity extends AppCompatActivity {
    private TextView tvDiallingUsername;
    private ImageButton btnEndCallDialling;
    private String username;
    public static String recvUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialling);

        username = getIntent().getStringExtra(recvUsername);

        tvDiallingUsername = (TextView)findViewById(R.id.tvDiallingUsername);
        btnEndCallDialling = (ImageButton)findViewById(R.id.btnEndCallDialling);

        tvDiallingUsername.setText(username);
        btnEndCallDialling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiallingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
