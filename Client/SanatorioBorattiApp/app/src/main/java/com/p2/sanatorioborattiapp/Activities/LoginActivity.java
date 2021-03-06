package com.p2.sanatorioborattiapp.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.p2.sanatorioborattiapp.Entities.SessionManager;
import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.Interfaces.LoginUser;
import com.p2.sanatorioborattiapp.R;
import com.p2.sanatorioborattiapp.Service.Service;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    EditText _userNameText;
    EditText _passwordText;
    Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _userNameText = (EditText) findViewById(R.id.input_user_name);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        SessionManager session = new SessionManager(getApplicationContext());
        if(session.isLoggedIn()) {
            goMainActivity();
        }
    }

    public void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        final String userName = _userNameText.getText().toString();
        final String password = _passwordText.getText().toString();

        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);

        final Service service = new Service(this);
        service.loginUserInBackground(user, new LoginUser(){
            @Override
            public void done(boolean success, int userId) {
                if(success){
                    SessionManager session = new SessionManager(getApplicationContext());
                    session.createLoginSession(userName, password, userId);
                    goMainActivity();
                }else{
                    Toast.makeText(getBaseContext(), "El usuario y la contraseña ingresados no son correctos", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void goMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Ha ocurrido un error. Vuelva a intentarlo", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String userName = _userNameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (userName.isEmpty()) {
            _userNameText.setError(getString(R.string.error_field_required));
            valid = false;
        } else {
            _userNameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError(getString(R.string.error_invalid_password));
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}