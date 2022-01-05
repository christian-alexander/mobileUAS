package com.example.uasmobilecatatan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayDeque;
import java.util.ArrayList;


public class Add extends AppCompatActivity {
    private TextInputEditText isiCatatan;

    private Button Add;
    private ProgressBar Load;
    private FirebaseUser firebaseUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String CatatanID;
    protected static final int RESULT_SPEECH = 1;
    private ImageView btnspeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        isiCatatan = findViewById(R.id.Catatan);
        btnspeak = findViewById(R.id.buttonmic);

        Add = findViewById(R.id.btn_Add);
        Load = findViewById(R.id.Loading);
        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();





        databaseReference = firebaseDatabase.getReference().child(firebaseUser.getUid());
        //firebaseUser.getEmail()


        btnspeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "in-ID");
                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    isiCatatan.setText("");
                }catch (ActivityNotFoundException e){
                    e.printStackTrace();
                }
            }
        });


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Load.setVisibility(View.VISIBLE);
                String catatan = isiCatatan.getText().toString();

                CatatanID = databaseReference.push().getKey();
                CatatanM catatanM = new CatatanM(CatatanID,catatan);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Load.setVisibility(View.GONE);
                        databaseReference.child(CatatanID).setValue(catatanM);
                        Toast.makeText(Add.this,"Note Added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Add.this,MainActivity.class);
                        startActivity(i);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Add.this,"error"+error.toString(), Toast.LENGTH_SHORT).show();


                    }
                });




            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    isiCatatan.setText(text.get(0));
                }
                break;
        }
    }
}