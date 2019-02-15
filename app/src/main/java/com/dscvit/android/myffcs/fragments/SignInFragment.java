package com.dscvit.android.myffcs.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dscvit.android.myffcs.MainActivity;
import com.dscvit.android.myffcs.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SignInFragment extends Fragment {
    private static final String TAG = "SignInFragment";
    private static final int RC_SIGN_IN = 69;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private CallbackManager callbackManager = CallbackManager.Factory.create();


    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        updateUI(firebaseAuth.getCurrentUser());

        // Init google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.gso_key))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso);

        // get refs to views
        TextInputEditText emailEditText = view.findViewById(R.id.login_email_edittext);
        TextInputEditText passwordEditText = view.findViewById(R.id.login_password_edittext);
        Button loginButton = view.findViewById(R.id.login_button);
        Button googleButton = view.findViewById(R.id.google_signin_button);
        Button facebookProxyButton = view.findViewById(R.id.facebook_signin_button_proxy);
        Button skipLoginButton = view.findViewById(R.id.skip_login_button);
        LoginButton facebookButton = view.findViewById(R.id.facebook_signin_button);

        // init facebook stuff
        facebookButton.setReadPermissions("email", "public_profile");
        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: Facebook login success");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w(TAG, "onError: ", error);
            }
        });
        facebookProxyButton.setOnClickListener(l -> {
            facebookButton.performClick();
        });

        // set listeners
        loginButton.setOnClickListener(l -> {
            if (Objects.requireNonNull(emailEditText.getText()).toString().isEmpty()) {
                emailEditText.setError("Email must not be empty!");
            } else if (Objects.requireNonNull(passwordEditText.getText()).toString().isEmpty()) {
                passwordEditText.setError("Password must not be empty!");
            } else if (passwordEditText.getText().toString().length() < 8) {
                passwordEditText.setError("Password length must be greater than 8 characters!");
            } else {
                firebaseAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                        .addOnCompleteListener(requireActivity(), task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onViewCreated: Sign in with email success");
                                updateUI(firebaseAuth.getCurrentUser());
                            } else {
                                Log.w(TAG, "onViewCreated: Sign in with email fail", task.getException());
                                Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        googleButton.setOnClickListener(l -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
        skipLoginButton.setOnClickListener(l -> {
            startActivity(new Intent(requireContext(), MainActivity.class));
        });

    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Context context = requireContext();
            context.startActivity(new Intent(context, MainActivity.class));
            Log.d(TAG, "updateUI: Starting MainActivity");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(Objects.requireNonNull(account));
            } catch (ApiException e) {
                Log.w(TAG, "onActivityResult: Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "firebaseAuthWithGoogle: success");
                        updateUI(firebaseAuth.getCurrentUser());
                    } else {
                        Log.w(TAG, "firebaseAuthWithGoogle: failed", task.getException());
                        Snackbar.make(Objects.requireNonNull(getView()).findViewById(R.id.sign_in_container), "Authentication failed", Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithCredential:success");
                        updateUI(firebaseAuth.getCurrentUser());
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
