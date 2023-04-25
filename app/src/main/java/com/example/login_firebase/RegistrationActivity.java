package com.example.login_firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    String sname, semail, spassword;
    FirebaseDatabase db;
    DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void login(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }

    public void register(View view) {

        EditText name = findViewById(R.id.fname);
        sname = name.getText().toString();
        EditText email = findViewById(R.id.mail);
        semail = email.getText().toString();
        EditText password = findViewById(R.id.pass);
        spassword = password.getText().toString();

        if(!sname.isEmpty() && !semail.isEmpty() && !spassword.isEmpty()){
            Users users = new Users(sname, semail, spassword);

            db = FirebaseDatabase.getInstance();
            reference = db.getReference("Users");
            reference.child(sname).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(RegistrationActivity.this, "Successfully Registered!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                }
            });
        }else{
            Toast.makeText(RegistrationActivity.this, "Enter credentials!!", Toast.LENGTH_SHORT).show();
        }
    }

}
