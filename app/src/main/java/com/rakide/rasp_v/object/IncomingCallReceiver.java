package com.rakide.rasp_v.object;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.sip.SipAudioCall;
import android.net.sip.SipProfile;

import com.rakide.rasp_v.CallingActivity;
import com.rakide.rasp_v.LoginActivity;
import com.rakide.rasp_v.MainActivity;

public class IncomingCallReceiver extends BroadcastReceiver {
    /**
     * Processes the incoming call, answers it, and hands it over to the
     * WalkieTalkieActivity.
     * @param context The context under which the receiver is running.
     * @param intent The intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        SipAudioCall incomingCall = null;
        final MainActivity wtActivity = (MainActivity) context;
//        final CallingActivity callingActivity = (CallingActivity) context;
        try {

            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    try {
                        call.answerCall(30);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                public void onCallEnded(SipAudioCall call) {
                    wtActivity.updateStatus("Call ended.");
                }
            };

            incomingCall = wtActivity.manager.takeAudioCall(intent, listener);
            incomingCall.answerCall(30);
            incomingCall.startAudio();
            incomingCall.setSpeakerMode(true);
            if(incomingCall.isMuted()) {
                incomingCall.toggleMute();
            }

            wtActivity.call = incomingCall;

            wtActivity.updateStatus(incomingCall);
//            wtActivity.updateStatus("INCOMING CALL");
            SharedPreferences.Editor editor = wtActivity.getSharedPreferences("SIP_PREF", wtActivity.MODE_PRIVATE).edit();
            editor.putString("sipAddress", incomingCall.getPeerProfile().getUriString());
            editor.commit();
        } catch (Exception e) {
            if (incomingCall != null) {
                incomingCall.close();
            }
        }
    }
}
