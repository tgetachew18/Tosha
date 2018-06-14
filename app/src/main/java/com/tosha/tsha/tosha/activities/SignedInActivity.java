package com.tosha.tsha.tosha.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tosha.tsha.tosha.R;

import java.util.Arrays;
import java.util.List;

public class SignedInActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private FirebaseUser currentUser = null;
    private AuthUI currentInstance = null;
    private FirebaseAuth mAuth;
    private TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed_in);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentInstance = AuthUI.getInstance();
        mAuth = FirebaseAuth.getInstance();
        email = (TextView) findViewById(R.id.tvUserEmail);

        //Todo signout
        final Button signOut = (Button) findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(SignedInActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                                if(task.isSuccessful()){
                                    startActivity(new Intent(SignedInActivity.this, SignedInActivity.class));
                                    finish();
                                }else {
                                    Toast.makeText(SignedInActivity.this, "SignOut failed.", Toast.LENGTH_SHORT).show();
                                }
                                currentUser = null;


                            }
                        });
            }
        });



        //Todo accDelete
        Button delAcc = (Button) findViewById(R.id.deleteAccount);
        delAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(SignedInActivity.this, SignedInActivity.class));
                            finish();
                        }else{
                            Toast.makeText(SignedInActivity.this, "Deletion failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        if(currentUser != null) {

            email.setText(currentUser.getEmail());

        }else{
            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.FacebookBuilder().build());
            // Create and launch sign-in intent
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setIsSmartLockEnabled(false)
                    .build(), RC_SIGN_IN);
        }

    }

    public void setUI(){

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                email.setText(user.getEmail());
                Toast.makeText(SignedInActivity.this, "Authentication success.", Toast.LENGTH_SHORT).show();

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Toast.makeText(SignedInActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                // handling possible errors
                if (response == null) {
                    // User cancelled Sign-in by using the back button
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    // Device has no network connection
                    return;
                }
                if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    // Unknown error occurred
                    return;
                }
            }
        }
    }


}