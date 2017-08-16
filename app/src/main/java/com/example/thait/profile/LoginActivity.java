//package com.example.thait.profile;
package com.example.thait.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {


    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener aListener;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childName;
    DatabaseReference childEmail;
    DatabaseReference childGender;
    DatabaseReference childAge;
    EditText inputEmail,inputPassword;
    Button btnLogin,btnReg;

    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

        inputEmail = (EditText)findViewById(R.id.email);
        inputPassword = (EditText)findViewById(R.id.password);
        btnLogin = (Button)findViewById(R.id.email_sign_in_button);
        btnReg = (Button)findViewById(R.id.email_sign_up_button);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();


        aListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(auth.getCurrentUser() != null){
                    startActivity(new Intent(LoginActivity.this,LoginTransition.class));
                    finish();
                }

            }
        };


//        progressBar = (ProgressBar)findViewById(R.id.login_progress);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                signIn(email,password);

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


}

@Override
protected void onStart(){
    super.onStart();



    auth.addAuthStateListener(aListener);
}

private void signIn(String email,String password){

    if (TextUtils.isEmpty(email)) {
        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
        return;
    }

    if (TextUtils.isEmpty(password)) {
        Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
        return;
    }

//                progressBar.setVisibility(View.VISIBLE);



    //authenticate user
    auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
//                                progressBar.setVisibility(View.GONE);
                    if (!task.isSuccessful()) {

                        // there was an error
                        Toast.makeText(LoginActivity.this, "auth_failed", Toast.LENGTH_LONG).show();



                    } else {
/*
                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_LONG).show();
                        finish();
                        */


                    }
                }
            });
}

}
