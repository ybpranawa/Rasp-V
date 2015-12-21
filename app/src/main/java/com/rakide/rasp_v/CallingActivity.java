package com.rakide.rasp_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class CallingActivity extends AppCompatActivity {
    private ImageButton btnEndCallDialling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);

        btnEndCallDialling = (ImageButton)findViewById(R.id.btnEndCallDialling);

        btnEndCallDialling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CallingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
