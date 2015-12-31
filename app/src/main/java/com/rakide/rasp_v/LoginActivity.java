package com.rakide.rasp_v;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.net.sip.*;

import java.text.ParseException;

public class LoginActivity extends AppCompatActivity {
    private EditText etLoginUsername;
    private EditText etLoginPassword;
    private Button btnLogin;
    private TextView tvLoginRegister;

    public String username;
    public String password;
    public static String recvUsername = null;
    public SipManager mSipManager = null;
    public SipProfile mSipProfile = null;
    public String domain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginUsername = (EditText)findViewById(R.id.etLoginUsername);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        tvLoginRegister = (TextView)findViewById(R.id.tvLoginRegister);
        etLoginPassword = (EditText)findViewById(R.id.etLoginPassword);

        //username = getIntent().getStringExtra(recvUsername);
        username = "fany";
        domain = "10.151.12.205";
        etLoginUsername.setText(username);
        //password = etLoginPassword.getText().toString();
        password="pwd_fany";


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mSipManager == null) {
                    mSipManager = SipManager.newInstance(LoginActivity.this);
                }

                SipProfile.Builder builder = null;
                try {
                    builder = new SipProfile.Builder(username, domain);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                builder.setPassword(password);
                mSipProfile = builder.build();

//                Intent intent = new Intent();
//                intent.setAction("com.rakide.rasp_v.MainActivity");
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginActivity.this, 0, intent, Intent.FILL_IN_DATA);
//                try {
//                    mSipManager.open(mSipProfile, pendingIntent, null);
//                } catch (SipException e) {
//                    e.printStackTrace();
//                }

//                try {
//                    mSipManager.setRegistrationListener(mSipProfile.getUriString(), new SipRegistrationListener() {
//
//                        public void onRegistering(String localProfileUri) {
//                            Toast.makeText(LoginActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
//                        }
//
//                        public void onRegistrationDone(String localProfileUri, long expiryTime) {
//                            Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
//                        }
//
//                        public void onRegistrationFailed(String localProfileUri, int errorCode, String errorMessage) {
//                            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } catch (SipException e) {
//                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                }
                if(SipManager.isApiSupported(LoginActivity.this))
                    Toast.makeText(LoginActivity.this,"support", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(LoginActivity.this, "gak support", Toast.LENGTH_SHORT).show();


                    /*Toast.makeText(LoginActivity.this,"Logged in as "+username,Toast.LENGTH_LONG).

                    show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.recvUsername,username);

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
