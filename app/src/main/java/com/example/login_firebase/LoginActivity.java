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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    String name, pass;
    DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }

    public void login(View view) {

        EditText userName = findViewById(R.id.fname);
        name = userName.getText().toString();
        EditText userPass = findViewById(R.id.pass);
        pass = userPass.getText().toString();

        if(!name.isEmpty()){
            readdata(name, pass);
        }else{
            Toast.makeText(LoginActivity.this, "Enter credentials!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void readdata(String name, String pass) {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(name).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if(task.isSuccessful()){

                    if(task.getResult().exists()){

                        DataSnapshot snap = task.getResult();
                        String storedPass = String.valueOf(snap.child("password").getValue());
                        if(storedPass.equals(pass)){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Wrong Password!!", Toast.LENGTH_SHORT).show();
                        }



                    }else{
                        Toast.makeText(LoginActivity.this, "User doesn't exists!!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "Failed to read!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
