package com.example.nviller.projetm2psav.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nviller.projetm2psav.R;
import com.example.nviller.projetm2psav.dao.DAOUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by nviller on 19/01/2018.
 */

public class CreateUserActivity extends AppCompatActivity {

    protected EditText editTextFirstName;
    protected EditText editTextLastName;
    protected EditText editTextPseudo;
    protected EditText editTextPassword;
    protected EditText editTextEmail;
    protected Button buttonSignUp;
    //protected User newUser;

    private FirebaseAuth firebaseAuth;
    private DAOUser daoUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);

        editTextFirstName = (EditText) findViewById(R.id.create_user_first_name);
        editTextLastName = (EditText) findViewById(R.id.create_user_last_name);
        editTextPseudo = (EditText) findViewById(R.id.create_user_pseudo);
        editTextEmail = (EditText) findViewById(R.id.create_user_email);
        editTextPassword = (EditText) findViewById(R.id.create_user_password);
        buttonSignUp = (Button) findViewById(R.id.create_user_sign_up);

        daoUser = DAOUser.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String firstName  = editTextFirstName.getText().toString().trim();
                final String lastName   = editTextLastName.getText().toString().trim();
                final String pseudo   = editTextPseudo.getText().toString().trim();
                final String email = editTextEmail.getText().toString().trim();
                final String password = editTextPassword.getText().toString().trim();

                if ( !daoUser.validateUser(email, password, firstName, lastName)) {
                    showAlertDialogError(R.string.signup_error_title,
                            getResources().getString(R.string.signup_error_message));
                } else {
                    signUp(email, password, firstName, lastName,pseudo);
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

    public void signUp(final String email, final String password,
                       final String firstName, final String lastName, final String pseudo) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(CreateUserActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(CreateUserActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            daoUser.createUser(task.getResult().getUser().getUid(), email, firstName, lastName);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), getString(R.string.signup_toast_connected), Toast.LENGTH_LONG).show();
                        } else {
                            showAlertDialogError(R.string.signup_error_title, task.getException().getMessage());
                        }
                    }
                });
    }

}
