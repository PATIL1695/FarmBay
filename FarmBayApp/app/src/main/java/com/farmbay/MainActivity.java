package com.farmbay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private Button guestLoginButton;
    private SignInButton googleLoginButton;
    private LoginButton fbLoginButton;

    FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 2;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener mAuthListener;
    CallbackManager callbackManager;

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //facebook Login
        setupFacebookSignin();

        //guest Login
        guestLoginButton = (Button) findViewById(R.id.guestLogin);
        guestLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(i);
            }
        });
        //Google Login
        setupGoogleSignin();


    }
    private void setupGoogleSignin(){
        googleLoginButton = (SignInButton) findViewById(R.id.googlebtn);

        googleLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);

            }
        });
    }

    private void setupFacebookSignin(){
        FacebookSdk.sdkInitialize(getApplicationContext());

        fbLoginButton = (LoginButton) findViewById(R.id.facebookLogin);

        fbLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // find out what you are supposed to do here
            }
        });

        callbackManager = CallbackManager.Factory.create();

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(MainActivity.this, loginResult.getAccessToken().getUserId(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(i);
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Login Canceled!", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onError(FacebookException error) {

                Toast.makeText(MainActivity.this, "Login Error!", Toast.LENGTH_LONG).show();
            }

        });

    }
}

