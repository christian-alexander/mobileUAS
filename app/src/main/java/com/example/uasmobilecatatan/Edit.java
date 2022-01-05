package com.example.uasmobilecatatan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Edit extends AppCompatActivity {
    private TextInputEditText isiCatatan;

    private Button Delete;
    private ProgressBar Load;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String CatatanID;
    private CatatanM catatanM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        isiCatatan = findViewById(R.id.Catatan2);

        Delete = findViewById(R.id.btn_Del);
        Load = findViewById(R.id.Loading);

        catatanM = getIntent().getParcelableExtra(firebaseUser.getUid());

        if(catatanM!=null){
            isiCatatan.setText(catatanM.getCatatan());
            CatatanID = catatanM.getCatatanID();
        }

        databaseReference = firebaseDatabase.getReference().child(firebaseUser.getUid()).child(CatatanID);


        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletecatatan();
            }
        });


    }

    private void deletecatatan(){
        databaseReference.removeValue();
        Toast.makeText(Edit.this,"Note Deleted", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(Edit.this,MainActivity.class);
        startActivity(i);
    }
}