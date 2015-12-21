package com.rakide.rasp_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rakide.rasp_v.fragment.ContactFragment;
import com.rakide.rasp_v.object.Contact;

public class DiallingActivity extends AppCompatActivity {
    private TextView tvDiallingUsername;
    private Button btnEndCallDialling;
    private String username;
    public static String recvUsername = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialling);

        username = getIntent().getStringExtra(recvUsername);
        tvDiallingUsername = (TextView)findViewById(R.id.tvDiallingUsername);
        btnEndCallDialling = (Button)findViewById(R.id.btnEndCallDialling);

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
