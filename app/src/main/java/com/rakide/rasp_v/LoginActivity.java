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
        //etLoginUsername.setText(username);
        //password = etLoginPassword.getText().toString();
        password="pwd_fany";


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SipManager.isVoipSupported(LoginActivity.this)) {
                    //Toast.makeText(LoginActivity.this, "support", Toast.LENGTH_SHORT).show();
                    if(mSipManager == null) {
                        mSipManager = SipManager.newInstance(LoginActivity.this);
                    }

                    try {
                        SipProfile.Builder builder = new SipProfile.Builder(username, domain);
                        builder.setPassword(password);
                        mSipProfile = builder.build();

                        /*Intent intent = new Intent();
                        intent.setAction("com.rakide.rasp_v.MainActivity");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginActivity.this, 0, intent, Intent.FILL_IN_DATA);
                        mSipManager.open(mSipProfile, pendingIntent, null);*/

                        mSipManager.setRegistrationListener(mSipProfile.getUriString(), new SipRegistrationListener() {

                            public void onRegistering(String localProfileUri) {
                                Toast.makeText(LoginActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
                            }

                            public void onRegistrationDone(String localProfileUri, long expiryTime) {
                                Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                            }

                            public void onRegistrationFailed(String localProfileUri, int errorCode, String errorMessage) {
                                Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (SipException e) {
                        Toast.makeText(LoginActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    } catch (ParseException pe){
                        Toast.makeText(LoginActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(LoginActivity.this, "Tidak Support", Toast.LENGTH_SHORT).show();
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

    /*public void initializemanager()
    {
        if(mSipManager == null) {
            mSipManager = SipManager.newInstance(LoginActivity.this);
        }

        try {
            SipProfile.Builder builder = new SipProfile.Builder(username, domain);
            builder.setPassword(password);
            mSipProfile = builder.build();

            Intent intent = new Intent();
            intent.setAction("com.rakide.rasp_v.MainActivity");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginActivity.this, 0, intent, Intent.FILL_IN_DATA);
            mSipManager.open(mSipProfile, pendingIntent, null);


            mSipManager.setRegistrationListener(mSipProfile.getUriString(), new SipRegistrationListener() {

                public void onRegistering(String localProfileUri) {
                    Toast.makeText(LoginActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
                }

                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                }

                public void onRegistrationFailed(String localProfileUri, int errorCode, String errorMessage) {
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (SipException e) {

        } catch (ParseException pe){

        }
    }*/

}
