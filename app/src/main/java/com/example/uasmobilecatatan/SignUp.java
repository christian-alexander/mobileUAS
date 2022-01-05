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

public class SignUp extends AppCompatActivity {
    private TextInputEditText userName1,pass1,conPass1;
    private Button signUP;
    private TextView loginTV;
    private ProgressBar Load;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName1 = findViewById(R.id.Username);
        pass1 = findViewById(R.id.Password);
        conPass1 = findViewById(R.id.ConPassword);
        signUP = findViewById(R.id.btn_SignUp);
        Load = findViewById(R.id.Loading);
        loginTV = findViewById(R.id.idTVLogin);
        mAuth = FirebaseAuth.getInstance();

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignUp.this,Login.class);
                startActivity(i);
            }
        });

        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Load.setVisibility(View.VISIBLE);
                String username = userName1.getText().toString();
                String pass = pass1.getText().toString();
                String conpass = conPass1.getText().toString();
                if(!pass.equals(conpass)){
                    Toast.makeText(SignUp.this,"Please Enter a Valid Pasword", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(username) && TextUtils.isEmpty(pass) && TextUtils.isEmpty(conpass)){
                    Toast.makeText(SignUp.this,"Please add form", Toast.LENGTH_SHORT).show();

                }else {
                    mAuth.createUserWithEmailAndPassword(username,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Load.setVisibility(View.GONE);
                                Toast.makeText(SignUp.this,"User Registered", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(SignUp.this,Login.class);
                                startActivity(i);
                                finish();
                            }else {
                                Load.setVisibility(View.GONE);
                                Toast.makeText(SignUp.this,"User Fail to Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });



    }
}