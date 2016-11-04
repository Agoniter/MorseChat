package com.ragingdevs.morsechat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEmail;
    private EditText mUsername;
    private EditText mPassword;
    private EditText mComfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mEmail = (EditText) findViewById(R.id.reg_email);
        mUsername = (EditText) findViewById(R.id.reg_username);
        mPassword = (EditText) findViewById(R.id.reg_password);
        mComfirmPassword = (EditText) findViewById(R.id.reg_confirm_password);

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
        String confirmPassword = mComfirmPassword.getText().toString();

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
            mComfirmPassword.setError(getString(R.string.error_password_mismatch));
            focusView = mComfirmPassword;
            error = true;
        }

        if (error) {
            focusView.requestFocus();
        } else {
            // TODO: add server connection


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

}
