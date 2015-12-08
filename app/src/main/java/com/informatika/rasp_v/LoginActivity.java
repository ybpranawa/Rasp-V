package com.informatika.rasp_v;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private EditText etLoginUsername;
    private EditText etLoginPassword;
    private Button btnLogin;
    private TextView tvLoginRegister;

    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginUsername = (EditText)findViewById(R.id.etLoginUsername);
        etLoginPassword = (EditText)findViewById(R.id.etLoginPassword);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        tvLoginRegister = (TextView)findViewById(R.id.tvLoginRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etLoginUsername.getText().toString();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.recvUsername,username);
                startActivityForResult(intent,0);
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
