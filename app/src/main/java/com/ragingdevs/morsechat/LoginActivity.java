package com.ragingdevs.morsechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private EditText mUser;
    private EditText mPassword;
    private ServerCom serverCom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        serverCom = new ServerCom();

        mUser = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        Button mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: add login function onClick
                loginCheck();
            }
        });

        Button mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        final Intent signupIntent = new Intent(this, SignUpActivity.class);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: add sign up action onClock
                startActivity(signupIntent);
            }
        });
    }

    /**
     * Override back button
     */
    @Override
    public void onBackPressed() {
    }

    /**
     * Checks if the fields are filled in and then tries to login
     */
    private void loginCheck() {

        String user = mUser.getText().toString();
        String password = mPassword.getText().toString();

        View focusView = null;
        boolean error = false;

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_incorrect_password));
            focusView = mPassword;
            error = true;
        }



        if (TextUtils.isEmpty(user)) {
            mUser.setError(getString(R.string.error_field_required));
            focusView = mUser;
            error = true;
        }

        if (error) {
            focusView.requestFocus();
        } else {
            RequestParams params = new RequestParams();
            params.put("username", user);
            params.put("password", password);
            serverCom.post("user/login", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String response = new String(responseBody, StandardCharsets.UTF_8);
                    Log.d("success", "login success:" + response);
                    //Intent mainActivityIntent = new Intent(this, MainActivity.class);
                    //startActivity(mainActivityIntent);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

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
     * Checks if password is 6 characters or longer
     * @param password
     * @return true or false
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

}
