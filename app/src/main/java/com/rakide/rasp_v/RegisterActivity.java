package com.rakide.rasp_v;

import android.content.Intent;
import android.net.sip.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText etRegisterUsername;
    private EditText etRegisterNama;
    private EditText etRegisterHp;
    private EditText etRegisterPassword;
    private EditText etRegisterPassword2;
    private String username;
    private String nama;
    private String hp;
    private String password;
    private String password2;
    private String domain;
    public SipProfile mSipProfile=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = (Button)findViewById(R.id.btnRegister);
        etRegisterUsername = (EditText)findViewById(R.id.etRegisterUsername);
        etRegisterNama = (EditText)findViewById(R.id.etRegisterNama);
        etRegisterHp = (EditText)findViewById(R.id.etRegisterHp);
        etRegisterPassword = (EditText)findViewById(R.id.etRegisterPassword);
        etRegisterPassword2 = (EditText)findViewById(R.id.etRegisterPassword2);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etRegisterUsername.getText().toString().trim();
                nama = etRegisterNama.getText().toString();
                hp = etRegisterHp.getText().toString();
                password = etRegisterPassword.getText().toString();
                password2 = etRegisterPassword2.getText().toString();
                domain = "10.151.12.205";

                SipProfile.Builder builder = null;
                try {
                    builder = new SipProfile.Builder(username, domain);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                builder.setPassword(password);
                mSipProfile = builder.build();

                //dikasih if dulu baru redirect ke halaman lainnya
                //Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                //intent.putExtra(LoginActivity.recvUsername,username);
                //startActivityForResult(intent,0);

                finish();
            }
        });
    }
}
