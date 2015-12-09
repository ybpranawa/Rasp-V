package com.informatika.rasp_v;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    private Button btnRegister;
    private EditText etRegisterUsername;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = (Button)findViewById(R.id.btnRegister);
        etRegisterUsername = (EditText)findViewById(R.id.etRegisterUsername);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etRegisterUsername.getText().toString().trim();

                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.putExtra(LoginActivity.recvUsername,username);
                startActivityForResult(intent,0);
                finish();
            }
        });
    }

}
