package com.rakide.rasp_v;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.sip.*;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;

public class LoginActivity extends AppCompatActivity {
    private EditText etLoginUsername;
    private EditText etLoginPassword;
    private Button btnLogin;
    private TextView tvLoginRegister;

    public String username;
    public String password;
    public String domain;
    public static String recvUsername = null;
    public SipProfile mSipProfile=null;
    public SipManager mSipManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginUsername = (EditText)findViewById(R.id.etLoginUsername);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        tvLoginRegister = (TextView)findViewById(R.id.tvLoginRegister);
        etLoginPassword = (EditText)findViewById(R.id.etLoginPassword);

        username = getIntent().getStringExtra(recvUsername);
        etLoginUsername.setText(username);
        password = etLoginPassword.getText().toString();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etLoginUsername.getText().toString().trim();
                domain = "10.151.12.205";

                //ini metode buat login nya pake SipProfile.Builder
                try {
                    SipProfile.Builder builder = new SipProfile.Builder(username, domain);
                    builder.setPassword(password);
                    mSipProfile = builder.build();
                    Log.v("pesan",mSipProfile.getUriString());

                    Intent intent = new Intent();
                    intent.setAction("android.SipDemo.INCOMING_CALL");
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginActivity.this, 0, intent, Intent.FILL_IN_DATA);
                    mSipManager.open(mSipProfile, pendingIntent, null);

                    mSipManager.setRegistrationListener(mSipProfile.getUriString(), new SipRegistrationListener() {
                        @Override
                        public void onRegistering(String localProfileUri) {
                            Toast.makeText(LoginActivity.this, "Logging in...", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onRegistrationDone(String localProfileUri, long expiryTime) {
                            Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onRegistrationFailed(String localProfileUri, int errorCode, String errorMessage) {
                            Toast.makeText(LoginActivity.this, "Gagal", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (SipException | ParseException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(LoginActivity.this, "Logged in as " + username, Toast.LENGTH_LONG).show();

                /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.recvUsername, username);
                startActivityForResult(intent, 0);
                finish();*/
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
