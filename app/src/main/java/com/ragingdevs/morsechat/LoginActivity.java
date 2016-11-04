package com.ragingdevs.morsechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail = (EditText) findViewById(R.id.email);

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

        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();

        View focusView = null;
        boolean error = false;

        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_incorrect_password));
            focusView = mPassword;
            error = true;
        }



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

        if (error) {
            focusView.requestFocus();
        } else {
            // Login attempt
            // TODO: add connection to server
            // TODO: check to see if it's in the db
            //Intent mainActivityIntent = new Intent(this, MainActivity.class);
            //startActivity(mainActivityIntent);o
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
