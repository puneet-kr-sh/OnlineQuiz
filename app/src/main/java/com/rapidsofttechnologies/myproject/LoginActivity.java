package com.rapidsofttechnologies.myproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
    EditText userName, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString().equals("puneet") && password.getText().toString().equals("@123")) {
                    SharedPreferences sharedPreference = getApplicationContext().getSharedPreferences("loginDetails",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreference.edit();
                    editor.putBoolean("isLoggedIn",true);
                    editor.commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    Toast.makeText(LoginActivity.this, "Invalid Credentials !!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
