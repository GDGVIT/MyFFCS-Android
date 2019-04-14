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
import com.dscvit.android.myffcs.models.ApiModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private static final String TAG = "SignUpFragment";
    private FirebaseAuth firebaseAuth;
    private ApiModel apiModel;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.api_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiModel = retrofit.create(ApiModel.class);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);

        // get refs to views
        TextInputEditText emailEditText = view.findViewById(R.id.signup_email_edittext);
        TextInputEditText passwordEditText = view.findViewById(R.id.signup_password_edittext);
        TextInputLayout emailTIL = view.findViewById(R.id.signup_email_TIL);
        TextInputLayout passwordTIL = view.findViewById(R.id.signup_password_TIL);
        Button registerButton = view.findViewById(R.id.register_button);
        Button skipButton = view.findViewById(R.id.skip_register_button);

        // set listeners
        registerButton.setOnClickListener(l -> {
            if (Objects.requireNonNull(emailEditText.getText()).toString().isEmpty()) {
                emailTIL.setError("Email must not be empty!");
            } else if (Objects.requireNonNull(passwordEditText.getText()).toString().isEmpty()) {
                passwordTIL.setError("Password must not be empty!");
            } else if (passwordEditText.getText().toString().length() < 8) {
                passwordTIL.setError("Password length must be greater than 8 characters!");
            } else {
                firebaseAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                        .addOnCompleteListener(requireActivity(), task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onViewCreated: Sign up successful");
                                updateUI(firebaseAuth.getCurrentUser());
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(requireContext(), "This user already exists. Try signing in instead", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(requireContext(), "Registration failed. Try again later", Toast.LENGTH_LONG).show();
                                    Log.w(TAG, "onViewCreated: Registration failed", task.getException());
                                }
                            }
                        });
            }
        });
        skipButton.setOnClickListener(l -> {
            Context context = requireContext();
            context.startActivity(new Intent(context, MainActivity.class));
            requireActivity().finish();
        });

    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            Context context = requireContext();
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                Call<String> insertUserCall = apiModel.addUser(currentUser.getUid(), currentUser.getDisplayName());
                try {
                    insertUserCall.execute();
                } catch (IOException e) {
                    Log.e(TAG, "updateUI: ", e);
                }
            });
            context.startActivity(new Intent(context, MainActivity.class));
            requireActivity().finish();

        }
    }
}
