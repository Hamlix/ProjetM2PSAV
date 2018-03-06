package com.example.nviller.projetm2psav.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nviller.projetm2psav.R;
import com.example.nviller.projetm2psav.dao.DAOUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mouna on 27/01/2018.
 */

public class LoginActivity extends AppCompatActivity {

    protected EditText editTextEmail;
    protected EditText editTextPassword;
    protected Button buttonLogIn;
    protected TextView textViewSignUp;

    private FirebaseAuth firebaseAuth;
    private DAOUser daoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        daoUser = DAOUser.getInstance();

        editTextEmail = (EditText) findViewById(R.id.activity_login_email);
        editTextPassword = (EditText) findViewById(R.id.activity_login_password);
        buttonLogIn = (Button) findViewById(R.id.activity_login_validate);
        textViewSignUp  = (TextView) findViewById(R.id.activity_login_to_activity_sign_up);

        //pas de compte on retourne se créer un compte
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateUserActivity.class);
                startActivity(intent);
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = editTextEmail.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();

                if ( !daoUser.validateUser(email, password)) {
                    showAlertDialogError(R.string.login_error_title, getResources().getString(R.string.login_error_message));
                } else {
                    signIn(email, password);
                }
            }
        });
    }

    public void signIn(final String email, final String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, GestionEventActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            showAlertDialogError(R.string.login_error_title,
                                    task.getException().getMessage());
                        }
                    }
                });

    }

    public void showAlertDialogError(int title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
