package com.example.thait.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    Button btnLogOut,update;
    TextView name,email,gender,age;
    TextView editName,editEmail,editGender,editAge;
    EditText eName,eEmail,eGender,eAge;
    ImageView pic;
    String userUID,userEmail;
    boolean changeName,changeEmail,changeGender,changeAge;

    FirebaseUser user;
    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference childName;
    DatabaseReference childEmail;
    DatabaseReference childGender;
    DatabaseReference childAge;
//    DatabaseReference childUID = mRef.child("uid");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnLogOut = (Button)findViewById(R.id.logout);
        update = (Button)findViewById(R.id.update);
        name = (TextView)findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        gender = (TextView) findViewById(R.id.gender);
        age = (TextView) findViewById(R.id.age);
        pic = (ImageView)findViewById(R.id.propic);
        editName = (TextView) findViewById(R.id.edit1);
        editEmail = (TextView) findViewById(R.id.edit2);
        editGender = (TextView) findViewById(R.id.edit3);
        editAge = (TextView) findViewById(R.id.edit4);
        eName = (EditText) findViewById(R.id.name2);
        eEmail = (EditText) findViewById(R.id.email2);
        eGender = (EditText) findViewById(R.id.gender2);
        eAge = (EditText) findViewById(R.id.age2);

        changeName = false;
        changeEmail = false;
        changeGender = false;
        changeAge = false;


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userUID = user.getUid();
        userEmail = user.getEmail();
        email.setText(userEmail);
        Toast.makeText(ProfileActivity.this, userEmail, Toast.LENGTH_SHORT).show();

        //REFERENCES
        DatabaseReference childInfoName = mRef.child(userUID);
        childName = childInfoName.child("name");
        childEmail = childInfoName.child("email");
        childGender = childInfoName.child("gender");
        childAge = childInfoName.child("age");

        childEmail.setValue(user.getEmail());

        eName.setEnabled(false);
        eEmail.setEnabled(false);
        eGender.setEnabled(false);
        eAge.setEnabled(false);

        pic.setImageResource(R.drawable.unknownwoman);






        eName.setText(name.getText().toString());
        name.setText(eName.getText().toString());
        eEmail.setText(email.getText().toString());
        eGender.setText(gender.getText().toString());
        eAge.setText(age.getText().toString());
        age.setText(eAge.getText().toString());


    }


    @Override
    public void onStart(){
        super.onStart();





        childName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String nameText = dataSnapshot.getValue(String.class);
                name.setText(nameText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        childEmail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String emailText = dataSnapshot.getValue(String.class);
                email.setText(emailText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        childGender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String genderText = dataSnapshot.getValue(String.class);
                gender.setText(genderText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        childAge.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ageText = dataSnapshot.getValue(String.class);
                age.setText(ageText);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        String email = auth.getInstance().getCurrentUser().getEmail();
//        name.setText(auth.getInstance().getCurrentUser().getEmail());
        if(auth.getCurrentUser() == null)
            name.setText("Null User");
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                if(auth.getCurrentUser() == null)
                    name.setText("Success Sign Out");
                else
                    name.setText("Failed Sign Out");

                finish();
            }
        });




        //Edit

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldName = name.getText().toString();
                eName.setEnabled(true);
                eName.setText(oldName);
                name.setEnabled(false);
                changeName = true;
            }
        });

        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldEmail = email.getText().toString();
                eEmail.setEnabled(true);
                eEmail.setText(oldEmail);
                email.setEnabled(false);
                changeEmail = true;
            }
        });


        editGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldGender = gender.getText().toString();
                eGender.setEnabled(true);
                eGender.setText(oldGender);
                gender.setEnabled(false);
                changeGender = true;
            }
        });

        editAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldAge = age.getText().toString();
                eAge.setEnabled(true);
                eAge.setText(oldAge);
                age.setEnabled(false);
                changeAge = true;
            }
        });

        //UPDATE
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    String newName = eName.getText().toString();
                    String newEmail = eEmail.getText().toString();
                    String newGender = eGender.getText().toString();
                    String newAge = eAge.getText().toString();

                if(changeName == true) {
                    eName.setEnabled(false);
                    name.setEnabled(true);
                    name.setText(newName);
                    childName.setValue(newName);
                }

                if(changeEmail == true) {

                    eEmail.setEnabled(false);
                    email.setEnabled(true);
                    email.setText(newEmail);
                    childEmail.setValue(newEmail);
                    user.updateEmail(newEmail);
                }
                if(changeGender == true) {

                    eGender.setEnabled(false);
                    gender.setEnabled(true);
                    gender.setText(newGender);
                    childGender.setValue(newGender);
                }
                if(changeAge == true) {

                    eAge.setEnabled(false);
                    age.setEnabled(true);
                    age.setText(newAge);
                    childAge.setValue(newAge);
                }




            }
        });

    }
}
