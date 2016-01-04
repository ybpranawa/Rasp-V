package com.rakide.rasp_v;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.rakide.rasp_v.adapter.TabMainAdapter;
import com.rakide.rasp_v.object.IncomingCallReceiver;

import java.text.ParseException;


public class MainActivity extends AppCompatActivity {
    private ViewPager pager;
    private TabLayout tabs;
    private TextView tvStatusBarMain;

    public SipManager manager = null;
    private SipProfile me = null;
    public SipAudioCall call = null;
    private IncomingCallReceiver callReceiver;

    private String username;
    private String password;
    private String domain;
    private static final String SIP_PREF = "SIP_PREF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(SIP_PREF, MODE_PRIVATE);
        username = prefs.getString("username","");
        password = prefs.getString("password","");
        domain = prefs.getString("domain","");

        getSupportActionBar().setTitle("Hi, " + username + " !");
        pager = (ViewPager)findViewById(R.id.pager);
        tabs = (TabLayout)findViewById(R.id.tabs);
        tvStatusBarMain = (TextView)findViewById(R.id.tvStatusBarMain);

        pager.setAdapter(new TabMainAdapter(getSupportFragmentManager()));
        tabs.setTabTextColors(getResources().getColor(R.color.tabTextColorDeactive), getResources().getColor(R.color.tabTextColor));
//        tabs.setTabTextColors(ContextCompat.getColor(context,R.color.tabTextColorDeactive), getResources().getColor(R.color.tabTextColor));

        tabs.setupWithViewPager(pager);
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);



        // Set up the intent filter.  This will be used to fire an
        // IncomingCallReceiver when someone calls the SIP address used by this
        // application.
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.SipDemo.INCOMING_CALL");
        callReceiver = new IncomingCallReceiver();
        this.registerReceiver(callReceiver, filter);

        // "Push to talk" can be a serious pain when the screen keeps turning off.
        // Let's prevent that.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        if (manager != null) {
//            SipAudioCall.Listener listener = new SipAudioCall.Listener(){
//                // Much of the client's interaction with the SIP Stack will
//                // happen via listeners.  Even making an outgoing call, don't
//                // forget to set up a listener to set things up once the call is established.
//
//                @Override
//                public void onCallEnded(SipAudioCall call) {
//                    updateStatus("Ready.");
//                }
//
//                @Override
//                public void onError(SipAudioCall call, int errorCode, String errorMessage){
//                    updateStatus("Error receiving call (" + errorCode + ")");
//                    call.close();
//                }
//
//                @Override
//                public void onChanged(SipAudioCall call){
//                    updateStatus("event occurs and the corresponding callback is not overridden");
//                }
//            };
//        }

//
//
//
//        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Contact row = (Contact) adapterView.getItemAtPosition(i);
//                Toast.makeText(MainActivity.this, row.displayName, Toast.LENGTH_SHORT).show();
//            }
//        });

        initializeManager();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            closeLocalProfile();
            Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
        else if (id == R.id.refresh) {
            initializeLocalProfile();
        }

        return super.onOptionsItemSelected(item);
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
                    call.startAudio();
                    call.setSpeakerMode(true);
                    updateStatus(call);
                }

                @Override
                public void onCallEnded(SipAudioCall call) {
                    updateStatus("Ready.");
                }

                @Override
                public void onError(SipAudioCall call, int errorCode, String errorMessage){
                    updateStatus("Error receiving call (" + errorCode + ")");
                    call.close();
                }

                @Override
                public void onChanged(SipAudioCall call){
                    updateStatus("event occurs and the corresponding callback is not overridden");
                }

                public void onRinging (SipAudioCall call, SipProfile caller){
                    updateStatus(caller.getUriString());
                }

            };
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
                tvStatusBarMain.setText(status);
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
