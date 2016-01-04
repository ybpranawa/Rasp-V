package com.rakide.rasp_v;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipProfile;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CallingActivity extends AppCompatActivity {
    private ImageButton btnEndCallDialling;
    private TextView tvDiallingUsername;
    private TextView tvDiallingTime;
    private String sipAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);

        final MainActivity mainActivity = new MainActivity();

        tvDiallingUsername = (TextView) findViewById(R.id.tvDiallingUsername);
        tvDiallingTime = (TextView) findViewById(R.id.tvDiallingTime);

        SharedPreferences prefs = getSharedPreferences("SIP_PREF", MODE_PRIVATE);
        sipAddress = prefs.getString("sipAddress","");

        tvDiallingUsername.setText(sipAddress);

        SipAudioCall.Listener listener = new SipAudioCall.Listener() {
            @Override
            public void onCallEstablished(SipAudioCall call) {
                updateStatus("on going call..");
            }

            @Override
            public void onCallEnded(SipAudioCall call) {
                updateStatus("Call ended.");
            }
        };

        btnEndCallDialling = (ImageButton)findViewById(R.id.btnEndCallDialling);

        btnEndCallDialling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mainActivity.call.endCall();
                } catch (SipException e) {
                    e.printStackTrace();
                }
                mainActivity.call.close();
                Intent intent = new Intent(CallingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    /**
     * Updates the status box at the top of the UI with a messege of your choice.
     * @param status The String to display in the status box.
     */
    public void updateStatus(final String status) {
        // Be a good citizen.  Make sure UI changes fire on the UI thread.
        this.runOnUiThread(new Runnable() {
            public void run() {
                tvDiallingTime.setText(status);
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
