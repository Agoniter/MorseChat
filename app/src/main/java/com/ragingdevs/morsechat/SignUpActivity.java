package com.ragingdevs.morsechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private ServerCom serverCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Sign Me Up");

        serverCom = new ServerCom();
        mEmail = (EditText) findViewById(R.id.reg_email);
        mUsername = (EditText) findViewById(R.id.reg_username);
        mPassword = (EditText) findViewById(R.id.reg_password);
        mConfirmPassword = (EditText) findViewById(R.id.reg_confirm_password);

        Button mSignUpButton = (Button) findViewById(R.id.registration_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrationCheck();
            }
        });

    }

    /**
     * Checks if the fields are filled in and registers a new user
     */
    private void registrationCheck() {

        String email = mEmail.getText().toString();
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();

        View focusView = null;
        boolean error = false;

        if (TextUtils.isEmpty(email)) {
            mEmail.setError(getString(R.string.error_field_required));
            focusView = mEmail;
            error = true;
        }

        if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            error = true;
        }

        if (!isUsernameValid(username)) {
            mUsername.setError(getString(R.string.error_invalid_email));
            focusView = mUsername;
            error = true;
        }

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_incorrect_password));
            focusView = mPassword;
            error = true;
        }

        if (TextUtils.isEmpty(confirmPassword) || !comparePassword(password, confirmPassword)) {
            mConfirmPassword.setError(getString(R.string.error_password_mismatch));
            focusView = mConfirmPassword;
            error = true;
        }

        if (error) {
            focusView.requestFocus();
        } else {
            // TODO: add server connection
            RequestParams params = new RequestParams();
            params.put("email",email);
            params.put("username",username);
            params.put("password",password);
            serverCom.post("user/create",params, new AsyncHttpResponseHandler(){

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody, StandardCharsets.UTF_8);
                    Log.d("success", "signup success:" + response);
                    Toast toast = Toast.makeText(SignUpActivity.this, "Signup successful!", Toast.LENGTH_LONG);
                    toast.show();
                    Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    error.printStackTrace();
                    switch (statusCode){
                        case 406:
                            Toast case406 = Toast.makeText(SignUpActivity.this, "Username is already taken please, chose another one", Toast.LENGTH_LONG);
                            case406.show();
                            break;
                        case 503:
                            Toast case503 = Toast.makeText(SignUpActivity.this, "Server unavailable, please try later", Toast.LENGTH_LONG);
                            case503.show();
                    }
                }
            });

        }
    }


    /**
     * Checks if email input contains @
     * @param email
     * @return true or false
     */
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Check for username to be longer than 3 characters
     * @param username
     * @return true or false
     */
    private boolean isUsernameValid(String username) {
        return username.length() > 3;
    }

    /**
     * Checks if password is 6 characters or longer
     * @param password
     * @return true or false
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    /**
     * Compare passwords to see if it's just right http://i.imgur.com/dpxi4E4.png
     * @param password
     * @return
     */
    private boolean comparePassword(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("Trykk dåå", "");
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
