package com.alternance.etna.agenda;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alternance.etna.agenda.Manager.UserData;

public class LoginActivity extends AppCompatActivity {


    public class LoginTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute()
        {
            LoginActivity.this.progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            String username = params[0];
            String password = params[1];
            return UserData.Login(username, password);
        }

        @Override
        protected void onPostExecute(Boolean backgroundRet) {

            LoginActivity.this._loginButton.setEnabled(true);
            if (backgroundRet) {
                LoginActivity.this.progressDialog.dismiss();

                //Toast.makeText(LoginActivity.this.getBaseContext(), "Success", Toast.LENGTH_LONG).show();
                LoginActivity.this.finish();
            }
            else {
                LoginActivity.this.progressDialog.dismiss();
                Toast.makeText(LoginActivity.this.getBaseContext(), LoginActivity.this.getString(R.string.LoginActivity_login_failed), Toast.LENGTH_LONG).show();
            }
        }
    }


    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;

    public ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserData.getInstance()._currentActivity = LoginActivity.this;
        this.progressDialog = new ProgressDialog(LoginActivity.this);
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setMessage(this.getString(R.string.LoginActivity_connecting));

//        if (android.os.Build.VERSION.SDK_INT > 9)
//        {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }

        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);


        LoginTask task = new LoginTask();
        task.execute(_emailText.getText().toString(), _passwordText.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implementer la logique du sign up success ici
                // Par defaut on finit l'activite et on login directement
                //this.finish();

                //On prefill avec les donnees du sign up?
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.LoginActivity_login_failed), Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError(getString(R.string.LoginActivity_invalid_email_message));
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 5 || password.length() > 10) {
            _passwordText.setError(getString(R.string.LoginActivity_invalid_password_message));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}