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
//    TextView name,email,gender,age;
    TextView editName,editEmail,editGender,editAge;
    EditText eName,eEmail,eGender,eAge;
    ImageView pic;
    String userUID,userEmail;
    boolean changeName,changeEmail,changeGender,changeAge;
    ArrayList<String> userInfo;

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

        final ArrayList<String> userInfo = new ArrayList<String>();
        User currentUser;
        final String[] nameText = new String[1];
        final String[] emailText = new String[1];
        final String[] genderText = new String[1];
        final String[] ageText = new String[1];

        btnLogOut = (Button) findViewById(R.id.logout);
        update = (Button) findViewById(R.id.update);
//        name = (TextView) findViewById(R.id.name);
//        email = (TextView) findViewById(R.id.email);
//        gender = (TextView) findViewById(R.id.gender);
//        age = (TextView) findViewById(R.id.age);
        pic = (ImageView) findViewById(R.id.propic);
        editName = (TextView) findViewById(R.id.edit1);
        editEmail = (TextView) findViewById(R.id.edit2);
        editGender = (TextView) findViewById(R.id.edit3);
        editAge = (TextView) findViewById(R.id.edit4);
        eName = (EditText) findViewById(R.id.name);
        eEmail = (EditText) findViewById(R.id.email);
        eGender = (EditText) findViewById(R.id.gender);
        eAge = (EditText) findViewById(R.id.age);

        changeName = false;
        changeEmail = false;
        changeGender = false;
        changeAge = false;


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userUID = user.getUid();
        userEmail = user.getEmail();
//        email.setText(userEmail);


        //REFERENCES
        DatabaseReference childInfoName = mRef.child(userUID);
        childName = childInfoName.child("name");
        childEmail = childInfoName.child("email");
        childGender = childInfoName.child("gender");
        childAge = childInfoName.child("age");


//        childInfoName.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
//
//                for(DataSnapshot child : children){
//                    String temp = child.getValue(String.class);
//                    userInfo.add(temp);
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });



//        childName.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String nameText = dataSnapshot.getValue(String.class);
//                name.setText(nameText);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        childEmail.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String emailText = dataSnapshot.getValue(String.class);
//                email.setText(emailText);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        childGender.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String genderText = dataSnapshot.getValue(String.class);
//                gender.setText(genderText);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//        childAge.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String ageText = dataSnapshot.getValue(String.class);
//                age.setText(ageText);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        childEmail.setValue(user.getEmail());
        eName.setText(LoginTransition.userInfo.get(0));
        eEmail.setText(LoginTransition.userInfo.get(1));
        eGender.setText(LoginTransition.userInfo.get(2));
        eAge.setText(LoginTransition.userInfo.get(3));
        eName.setEnabled(false);
        eEmail.setEnabled(false);
        eGender.setEnabled(false);
        eAge.setEnabled(false);

        if(eGender.getText().toString().equalsIgnoreCase("female")) {
            pic.setImageResource(R.drawable.unknownwoman);
        }
        else{
            pic.setImageResource(R.drawable.unknownman);
        }

    }










    @Override
    public void onStart(){
        super.onStart();






//        String email = auth.getInstance().getCurrentUser().getEmail();
//        name.setText(auth.getInstance().getCurrentUser().getEmail());
        if(auth.getCurrentUser() == null)
            eName.setText("Null User");
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                if(auth.getCurrentUser() == null)
                    eName.setText("Success Sign Out");
                else
                    eName.setText("Failed Sign Out");

                finish();
            }
        });




        //Edit

        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eName.setEnabled(true);
                changeName = true;
            }
        });

        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eEmail.setEnabled(true);
                changeEmail = true;
            }
        });


        editGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eGender.setEnabled(true);
                changeGender = true;
            }
        });

        editAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eAge.setEnabled(true);
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
                    childName.setValue(newName);
                }

                if(changeEmail == true) {

                    eEmail.setEnabled(false);
                    childEmail.setValue(newEmail);
                    user.updateEmail(newEmail);
                }
                if(changeGender == true) {

                    eGender.setEnabled(false);
                    childGender.setValue(newGender);
                }
                if(changeAge == true) {

                    eAge.setEnabled(false);
                    childAge.setValue(newAge);
                }




            }
        });

    }
}
