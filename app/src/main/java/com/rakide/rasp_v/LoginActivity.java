package com.rakide.rasp_v;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.net.sip.*;

import com.rakide.rasp_v.object.IncomingCallReceiver;
import com.rakide.rasp_v.object.SIPAdapter;

import java.text.ParseException;

public class LoginActivity extends AppCompatActivity {
    private EditText etLoginUsername;
    private EditText etLoginPassword;
    private Button btnLogin;
    private TextView tvLoginRegister;
    private TextView tvStatus;

    private static final String SIP_PREF = "SIP_PREF";
    private static String domain;
    private String username;
    private String password;
    public static String recvUsername = null;

    private SipManager manager = null;
    private SipProfile me = null;
    private SipAudioCall call = null;
    private IncomingCallReceiver callReceiver;

    private static final int CALL_ADDRESS = 1;
    private static final int SET_AUTH_INFO = 2;
    private static final int UPDATE_SETTINGS_DIALOG = 3;
    private static final int HANG_UP = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLoginUsername = (EditText)findViewById(R.id.etLoginUsername);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        tvLoginRegister = (TextView)findViewById(R.id.tvLoginRegister);
        etLoginPassword = (EditText)findViewById(R.id.etLoginPassword);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        username = getIntent().getStringExtra(recvUsername);

        domain = "10.151.12.205";
        etLoginUsername.setText(username);

        // remove this
        etLoginUsername.setText("tities");
        etLoginPassword.setText("pwd_tities");
        // end of remove this

        initializeManager();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SipManager.isVoipSupported(LoginActivity.this)) {
                    //Toast.makeText(LoginActivity.this, "support", Toast.LENGTH_SHORT).show();
                    username = etLoginUsername.getText().toString();
                    password = etLoginPassword.getText().toString();
                    SharedPreferences.Editor editor = getSharedPreferences(SIP_PREF, MODE_PRIVATE).edit();
                    editor.putString("domain", domain);
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();
                    //initiateCall();
                    initializeLocalProfile();
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

    @Override
    public void onStart() {
        super.onStart();
        // When we get back from the preference setting Activity, assume
        // settings have changed, and re-login with new auth info.
        initializeManager();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.close();
        }

        closeLocalProfile();

        if (callReceiver != null) {
            this.unregisterReceiver(callReceiver);
        }
    }

    public void initializeManager() {
        if(manager == null) {
            manager = SipManager.newInstance(this);
        }

        //initializeLocalProfile();
    }

    /**
     * Logs you into your SIP provider, registering this device as the location to
     * send SIP calls to for your SIP address.
     */
    public void initializeLocalProfile() {
        if (manager == null) {
            return;
        }

        if (me != null) {
            closeLocalProfile();
        }

        try {
            SipProfile.Builder builder = new SipProfile.Builder(username, domain);
            builder.setPassword(password);
            me = builder.build();

            Intent i = new Intent();
            i.setAction("android.SipDemo.INCOMING_CALL");
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, Intent.FILL_IN_DATA);
            manager.open(me, pi, null);


            // This listener must be added AFTER manager.open is called,
            // Otherwise the methods aren't guaranteed to fire.

            manager.setRegistrationListener(me.getUriString(), new SipRegistrationListener() {
                public void onRegistering(String localProfileUri) {
                    updateStatus("Logging in with SIP Server...");
                }

                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    updateStatus("Ready");
                }

                public void onRegistrationFailed(String localProfileUri, int errorCode,
                                                 String errorMessage) {
                    updateStatus("Login failed. Please check username and password.");
                }
            });
        } catch (ParseException pe) {
            updateStatus("Connection Error.");
        } catch (SipException se) {
            updateStatus("Connection error.");
        }
    }

    /**
     * Closes out your local profile, freeing associated objects into memory
     * and unregistering your device from the server.
     */
    public void closeLocalProfile() {
        if (manager == null) {
            return;
        }
        try {
            if (me != null) {
                Log.v("MyError","manager.close()");
                manager.close(me.getUriString());
            }
        } catch (Exception ee) {
            Log.d("WalkActivity/onDestroy", "Failed to close local profile.", ee);
        }
    }

    /**
     * Updates the status box at the top of the UI with a messege of your choice.
     * @param status The String to display in the status box.
     */
    public void updateStatus(final String status) {
        // Be a good citizen.  Make sure UI changes fire on the UI thread.
        this.runOnUiThread(new Runnable() {
            public void run() {
                tvStatus.setText(status);
                if (tvStatus.getText().toString().equals("Ready") ){
//                    closeLocalProfile();
                    Intent intent = new Intent(LoginActivity.this,LoginToMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    /**
     * Updates the status box with the SIP address of the current call.
     * @param call The current, active call.
     */
    public void updateStatus(SipAudioCall call) {
        String useName = call.getPeerProfile().getDisplayName();
        if(useName == null) {
            useName = call.getPeerProfile().getUserName();
        }
        updateStatus(useName + "@" + call.getPeerProfile().getSipDomain());
    }
}
