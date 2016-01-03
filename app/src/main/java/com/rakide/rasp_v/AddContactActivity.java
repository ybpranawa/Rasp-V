package com.rakide.rasp_v;

import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.net.sip.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.rakide.rasp_v.object.IncomingCallReceiver;
import java.text.ParseException;

public class AddContactActivity extends AppCompatActivity {
    private Button btnAdd;

    public String sipAddress = null;
    public SipManager manager = null;
    public SipProfile me = null;
    public SipAudioCall call = null;
    public IncomingCallReceiver callReceiver;

    public String username;
    public String password;
    public String domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        SharedPreferences prefs = getSharedPreferences("SIP_PREF", MODE_PRIVATE);
        username = prefs.getString("username","");
        password = prefs.getString("password","");
        domain = prefs.getString("domain","");

        initializeManager();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                startActivity(intent);
                finish();*/
                EditText textEdit=(EditText)findViewById(R.id.etAddNumber);
                sipAddress = textEdit.getText().toString()+ "@" +domain;
                initiateCall();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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

        initializeLocalProfile();
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

//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//        String username = prefs.getString("namePref", "");
//        String domain = prefs.getString("domainPref", "");
//        String password = prefs.getString("passPref", "");

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
                    updateStatus("Registering with SIP Server...");
                }

                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    updateStatus("Ready");
                }

                public void onRegistrationFailed(String localProfileUri, int errorCode,
                                                 String errorMessage) {
                    updateStatus("Registration failed.  Please check settings.");
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
                manager.close(me.getUriString());
            }
        } catch (Exception ee) {
            Log.d("WalkActivity/onDestroy", "Failed to close local profile.", ee);
        }
    }

    /**
     * Make an outgoing call.
     */
    public void initiateCall() {

        //updateStatus(sipAddress);

        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                // Much of the client's interaction with the SIP Stack will
                // happen via listeners.  Even making an outgoing call, don't
                // forget to set up a listener to set things up once the call is established.
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    //call.startAudio();
                    //call.setSpeakerMode(true);
                    //call.toggleMute();
                    updateStatus("user menjawab");
//                    try {
//                        call.endCall();
//                    } catch (SipException e) {
//                        e.printStackTrace();
//                    }
//                    call.close();
                }

                @Override
                public void onCallEnded(SipAudioCall call) {
                    updateStatus("Ready.");
                }
/*
                @Override
                public void onError(SipAudioCall call, int errorCode, String errorMessage){
                    updateStatus("user tidak ditemukan");
                    call.close();
                }

                @Override
                public void onCalling(SipAudioCall call){
                    updateStatus("user ditemukan");
                    try {
                        call.endCall();
                    } catch (SipException e) {
                        e.printStackTrace();
                    }
                    call.close();

                    Intent intent=new Intent(AddContactActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }*/

            };

            //updateStatus("user ditemukan");
            call = manager.makeAudioCall(me.getUriString(), sipAddress, listener, 30);
//            //call.close();
            Log.v("MyError",call.getPeerProfile().getUriString());
            if (call.getPeerProfile()!=null){
                updateStatus("user ditemukan");
                call.endCall();
                call.close();
            }
            else{
                updateStatus("user tidak ditemukan");
                call.close();
            }
        }
        catch (Exception e) {
            Log.i("WalkieTalkieActivity/InitiateCall", "Error when trying to close manager.", e);
            if (me != null) {
                try {
                    manager.close(me.getUriString());
                } catch (Exception ee) {
                    Log.i("WalkieTalkieActivity/InitiateCall",
                            "Error when trying to close manager.", ee);
                    ee.printStackTrace();
                }
            }
            if (call != null) {
                call.close();
            }
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
                TextView labelView = (TextView) findViewById(R.id.tvStatus);
                labelView.setText(status);
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
