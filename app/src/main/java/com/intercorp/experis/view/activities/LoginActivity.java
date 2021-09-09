package com.intercorp.experis.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.intercorp.experis.R;
import com.intercorp.experis.model.entities.User;

import java.util.Arrays;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    Button btFacebook;
    Button btNext;
    ConstraintLayout content;
    private CallbackManager callbackManager;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btNext = findViewById(R.id.btNext);
        btFacebook = findViewById(R.id.btFacebook);
        content = findViewById(R.id.content);

        auth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();


        btFacebook.setOnClickListener(v -> {
            loginFacebook();
        });
        btNext.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        if (loginResult != null && loginResult.getAccessToken().getToken() != null) {
                            firebaseAuthWithFacebook(loginResult.getAccessToken().getToken());
                        }
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Snackbar.make(content, getString(R.string.error_fb_account), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void firebaseAuthWithFacebook(String token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token);
        auth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser firebaseUser = auth.getCurrentUser();
                User user = new User();
                user.setId(firebaseUser.getUid() == null ? Calendar.getInstance().getTimeInMillis() + "" : firebaseUser.getUid());
                user.setName(firebaseUser.getDisplayName() == null ? "" : firebaseUser.getDisplayName());
                user.setEmail(firebaseUser.getEmail() == null ? Calendar.getInstance().getTimeInMillis() + "" : firebaseUser.getEmail());
                startActivity(new Intent(this, RegisterActivity.class).putExtra("userApp", user));
                finish();
            } else {
                dialogMessage(getString(R.string.empty_field_rrss));
            }
        });

    }


    private void dialogMessage(String message) {
        new MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
                .setTitle(R.string.app_name)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.accept, (dialog, which) -> {
                    dialog.dismiss();
                }).show();
    }
}
