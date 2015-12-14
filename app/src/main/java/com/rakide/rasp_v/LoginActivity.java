package com.rakide.rasp_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText etLoginUsername;
    private Button btnLogin;
    private TextView tvLoginRegister;

    public String username;
    public static String recvUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginUsername = (EditText)findViewById(R.id.etLoginUsername);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        tvLoginRegister = (TextView)findViewById(R.id.tvLoginRegister);

        username = getIntent().getStringExtra(recvUsername);
        etLoginUsername.setText(username);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etLoginUsername.getText().toString().trim();
                Toast.makeText(LoginActivity.this, "Logged in as " + username, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.recvUsername, username);
                startActivityForResult(intent, 0);
                finish();
            }
        });
        tvLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}
