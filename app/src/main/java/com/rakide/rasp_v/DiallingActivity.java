package com.rakide.rasp_v;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;

public class DiallingActivity extends AppCompatActivity {
    private TextView tvDiallingUsername;
    private ImageButton btnEndCallDialling;

    private String domain;
    private String username;
    private String password;
    private String sipAddress;
    private SipManager manager = null;
    private SipProfile me = null;
    private SipAudioCall call = null;

    public static String recvUsername = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialling);

        SharedPreferences prefs = getSharedPreferences("SIP_PREF", MODE_PRIVATE);
        username = prefs.getString("username","");
        password = prefs.getString("password","");
        domain = prefs.getString("domain","");
        sipAddress = prefs.getString("sipAddress", "");

        tvDiallingUsername = (TextView)findViewById(R.id.tvDiallingUsername);
        btnEndCallDialling = (ImageButton)findViewById(R.id.btnEndCallDialling);

        tvDiallingUsername.setText(sipAddress);
        initializeManager();
        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                // Much of the client's interaction with the SIP Stack will
                // happen via listeners.  Even making an outgoing call, don't
                // forget to set up a listener to set things up once the call is established.
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    call.startAudio();
                    call.setSpeakerMode(true);
                    updateStatus(call);
                }

                @Override
                public void onCallEnded(SipAudioCall call) {
                    updateStatus("Call ended.");
                }

                @Override
                public void onCallBusy (SipAudioCall call) {
                    updateStatus("The receiver seems busy");
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent mainIntent = new Intent(DiallingActivity.this, MainActivity.class);
//                            DiallingActivity.this.startActivity(mainIntent);
//                            DiallingActivity.this.finish();
//                        }
//                    }, 3000);
                }

                @Override
                public void onCalling(SipAudioCall call) {
                    updateStatus(call);
                }
            };

            call = manager.makeAudioCall(me.getUriString(), sipAddress, listener, 30);

        }
        catch (Exception e) {
//            Log.i("WalkieTalkieActivity/InitiateCall", "Error when trying to close manager.", e);
            if (me != null) {
                try {
                    manager.close(me.getUriString());
                } catch (Exception ee) {
//                    Log.i("WalkieTalkieActivity/InitiateCall",
//                            "Error when trying to close manager.", ee);
                    ee.printStackTrace();
                }
            }
            if (call != null) {
                call.close();
            }
        }
        btnEndCallDialling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    call.endCall();
                } catch (SipException se) {
//                        Log.d("WalkieTalkieActivity/onOptionsItemSelected",
//                                "Error ending call.", se);
                }
                //call.close();
                call.close();
                Intent intent = new Intent(DiallingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

//            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
//                // Much of the client's interaction with the SIP Stack will
//                // happen via listeners.  Even making an outgoing call, don't
//                // forget to set up a listener to set things up once the call is established.
//                @Override
//                public void onCallEstablished(SipAudioCall call) {
//                    call.startAudio();
//                    call.setSpeakerMode(true);
//                    updateStatus(call);
//                }
//
//                @Override
//                public void onCallEnded(SipAudioCall call) {
//                    updateStatus("Ready.");
//                }
//
//                @Override
//                public void onCallBusy (SipAudioCall call) {
//                    updateStatus("Busy");
//                }
//            };


            // This listener must be added AFTER manager.open is called,
            // Otherwise the methods aren't guaranteed to fire.

            manager.setRegistrationListener(me.getUriString(), new SipRegistrationListener() {
                public void onRegistering(String localProfileUri) {
                    updateStatus("Logging in with SIP Server...");
                }

                public void onRegistrationDone(String localProfileUri, long expiryTime) {
                    updateStatus("Dialing...");
                    initiateCall();
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
//        initiateCall();
    }
    /**
     * Make an outgoing call.
     */
    public void initiateCall() {

        updateStatus(sipAddress);

//        try {
//            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
//                // Much of the client's interaction with the SIP Stack will
//                // happen via listeners.  Even making an outgoing call, don't
//                // forget to set up a listener to set things up once the call is established.
//                @Override
//                public void onCallEstablished(SipAudioCall call) {
//                    call.startAudio();
//                    call.setSpeakerMode(true);
//                    updateStatus(call);
//                }
//
//                @Override
//                public void onCallEnded(SipAudioCall call) {
//                    updateStatus("Call ended.");
//                }
//
//                @Override
//                public void onCallBusy (SipAudioCall call) {
//                    updateStatus("The receiver seems busy");
////                    new Handler().postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            Intent mainIntent = new Intent(DiallingActivity.this, MainActivity.class);
////                            DiallingActivity.this.startActivity(mainIntent);
////                            DiallingActivity.this.finish();
////                        }
////                    }, 3000);
//                }
//
//                @Override
//                public void onCalling(SipAudioCall call) {
//                    updateStatus(call);
//                }
//            };
//
//            call = manager.makeAudioCall(me.getUriString(), sipAddress, listener, 30);
//
//        }
//        catch (Exception e) {
////            Log.i("WalkieTalkieActivity/InitiateCall", "Error when trying to close manager.", e);
//            if (me != null) {
//                try {
//                    manager.close(me.getUriString());
//                } catch (Exception ee) {
////                    Log.i("WalkieTalkieActivity/InitiateCall",
////                            "Error when trying to close manager.", ee);
//                    ee.printStackTrace();
//                }
//            }
//            if (call != null) {
//                call.close();
//            }
//        }
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
     * Updates the status box at the top of the UI with a messege of your choice.
     * @param status The String to display in the status box.
     */
    public void updateStatus(final String status) {
        // Be a good citizen.  Make sure UI changes fire on the UI thread.
        this.runOnUiThread(new Runnable() {
            public void run() {
                tvDiallingUsername.setText(status);
                if (tvDiallingUsername.getText() == "Call ended." || tvDiallingUsername.getText() == "The receiver seems busy"){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent mainIntent = new Intent(DiallingActivity.this, MainActivity.class);
                            DiallingActivity.this.startActivity(mainIntent);
                            DiallingActivity.this.finish();
                        }
                    }, 2000);
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
        useName = call.getPeerProfile().getProfileName();
        if(useName == null) {
            useName = call.getPeerProfile().getUserName();
        }
        updateStatus("Ongoing call " + useName + "@" + call.getPeerProfile().getSipDomain());
    }
}
