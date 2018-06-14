package com.tosha.tsha.tosha.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tosha.tsha.tosha.R;
import com.tosha.tsha.tosha.model.Report;

public class ReportActivity extends AppCompatActivity {

    private EditText txtLocation;
    private EditText txtTime;
    private EditText txtDate;
    private EditText txtDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtLocation = (EditText) findViewById(R.id.txtLocation);
        txtTime = (EditText) findViewById(R.id.txtTime);
        txtDate = (EditText) findViewById(R.id.txtDate);
        txtDescription = (EditText) findViewById(R.id.txtDescription);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //TODO handle for empty reports
                //TODO save report
                Report report = new Report(txtLocation.getText().toString(),txtDescription.getText().toString());
                //add more report data
                report.setReportingUser(user.getUid());
                //TODO
                //add date if present
                //add time if present
                db.collection(user.getUid()+": posts")
                        .add(report)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                dismissKeyboard();
                                emptyFields();
                                Snackbar.make(view, "Report Saved", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(view, "Error adding document", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                            }
                        });

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ReportActivity.this,MainActivity.class));
        finish();
    }

    private void emptyFields(){
        txtDescription.setText("");
        txtLocation.setText("");
        txtDate.setText("");
        txtTime.setText("");
    }

    private void dismissKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }


}
