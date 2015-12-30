package com.rakide.rasp_v.fragment;


import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.rakide.rasp_v.DiallingActivity;
import com.rakide.rasp_v.MainActivity;
import com.rakide.rasp_v.R;
import com.rakide.rasp_v.adapter.IncomingCallReceiver;

public class DialFragment extends Fragment {
    private String allNumber;
    private String last = "";
    private Button  btn1, btn2, btn3,
                    btn4, btn5, btn6,
                    btn7, btn8, btn9,
                    btn0;
    private ImageView btnCall, btnC;
    private EditText etCallerID;

    public DialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dial, container, false);
        btn0 = (Button)view.findViewById(R.id.btn0);
        btn1 = (Button)view.findViewById(R.id.btn1);
        btn2 = (Button)view.findViewById(R.id.btn2);
        btn3 = (Button)view.findViewById(R.id.btn3);
        btn4 = (Button)view.findViewById(R.id.btn4);
        btn5 = (Button)view.findViewById(R.id.btn5);
        btn6 = (Button)view.findViewById(R.id.btn6);
        btn7 = (Button)view.findViewById(R.id.btn7);
        btn8 = (Button)view.findViewById(R.id.btn8);
        btn9 = (Button)view.findViewById(R.id.btn9);
        btnC = (ImageView)view.findViewById(R.id.ivC);
        btnCall = (ImageView)view.findViewById(R.id.ivCall);

        etCallerID = (EditText)view.findViewById(R.id.etCallerID);
        etCallerID.setInputType(InputType.TYPE_NULL);
        etCallerID.setTextIsSelectable(true);

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last = "0";
                allNumber = etCallerID.getText().toString();
                allNumber+=last;
                etCallerID.setText(allNumber);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last = "1";
                allNumber = etCallerID.getText().toString();
                allNumber+=last;
                etCallerID.setText(allNumber);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last = "2";
                allNumber = etCallerID.getText().toString();
                allNumber+=last;
                etCallerID.setText(allNumber);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last = "3";
                allNumber = etCallerID.getText().toString();
                allNumber+=last;
                etCallerID.setText(allNumber);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last = "4";
                allNumber = etCallerID.getText().toString();
                allNumber+=last;
                etCallerID.setText(allNumber);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last = "5";
                allNumber = etCallerID.getText().toString();
                allNumber+=last;
                etCallerID.setText(allNumber);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last = "6";
                allNumber = etCallerID.getText().toString();
                allNumber+=last;
                etCallerID.setText(allNumber);
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last = "7";
                allNumber = etCallerID.getText().toString();
                allNumber+=last;
                etCallerID.setText(allNumber);
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last = "8";
                allNumber = etCallerID.getText().toString();
                allNumber+=last;
                etCallerID.setText(allNumber);
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last = "9";
                allNumber = etCallerID.getText().toString();
                allNumber+=last;
                etCallerID.setText(allNumber);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allNumber = etCallerID.getText().toString();
                allNumber = deleteLast(allNumber);
                etCallerID.setText(allNumber);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), DiallingActivity.class);
//                intent.putExtra(DiallingActivity.recvUsername,allNumber);
//                startActivityForResult(intent, 0);
//                getActivity().finish();
            }
        });

        // ============================ SIP ============================

        // =========================End of SIP =========================
        return view;
    }
    public String deleteLast(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }
        return s.substring(0, s.length()-1);
    }
}
