package com.ragingdevs.morsechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

import com.ragingdevs.utils.*;

public class LoginActivity extends AppCompatActivity {

    private EditText mUser;
    private EditText mPassword;
    private ServerCom serverCom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        serverCom = new ServerCom();

        mUser = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("MorseChat - Login");

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
                mPassword.getText().clear();
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
                serverCom.post("user/login", params, new JsonHttpResponseHandler(
                ) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            // UserSingleton.getInstance().clearMe();

                            String username = response.getString("username");
                            String token = response.getString("token");
                            Long id = response.getLong("id");
                            ChatUser owner = new ChatUser(id, username);
                            owner.setToken(token);

                            FileHandler fh = new FileHandler();
                            try {
                                fh.writeToFile(token, "token.txt", LoginActivity.this);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            UserSingleton.getInstance().setUser(owner);
                            serverCom.setAuthHead(token);
                            Intent mainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(mainActivityIntent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //Toast toast = Toast.makeText(LoginActivity.this, "Password or username is wrong, please try again", Toast.LENGTH_LONG);
                            //toast.show();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String benis, Throwable error) {
                        Toast toast = Toast.makeText(LoginActivity.this, "Password or username is wrong, please try again", Toast.LENGTH_LONG);
                        toast.show();
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
