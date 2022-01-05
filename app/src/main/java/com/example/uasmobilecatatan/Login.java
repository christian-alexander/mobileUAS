package com.example.uasmobilecatatan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private TextInputEditText userName1,pass1;
    private Button login;
    private TextView signupTV;
    private ProgressBar Load;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName1 = findViewById(R.id.Username);
        pass1 = findViewById(R.id.Password);

        login = findViewById(R.id.btn_Login);
        Load = findViewById(R.id.Loading);
        signupTV = findViewById(R.id.idTVSignUp);
        mAuth = FirebaseAuth.getInstance();


        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,SignUp.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Load.setVisibility(View.VISIBLE);
                String username = userName1.getText().toString();
                String pass = pass1.getText().toString();


                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(pass)){
                    Toast.makeText(Login.this,"Please add form", Toast.LENGTH_SHORT).show();
                return;
                }else{
                    mAuth.signInWithEmailAndPassword(username,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Load.setVisibility(View.GONE);
                                Toast.makeText(Login.this,"Login Succesfull", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Login.this,MainActivity.class);
                                startActivity(i);
                                finish();


                            }else {
                                Load.setVisibility(View.GONE);
                                Toast.makeText(Login.this,"Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            Intent i = new Intent(Login.this,MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}