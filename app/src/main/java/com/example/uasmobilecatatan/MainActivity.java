package com.example.uasmobilecatatan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CatatanAdapter.CatatanClickInterface {

    private RecyclerView alldata;
    private ProgressBar Load;
    private FloatingActionButton Added;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private ArrayList<CatatanM> catatanMArrayList;
    private RelativeLayout bottom;
    private CatatanAdapter catatanAdapter;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alldata = findViewById(R.id.Alldata);
        Load = findViewById(R.id.Loading);
        Added = findViewById(R.id.btn_Added);
        bottom = findViewById(R.id.Botoomsheet);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = firebaseDatabase.getReference().child(firebaseUser.getUid());
        catatanMArrayList = new ArrayList<>();
        catatanAdapter = new CatatanAdapter(catatanMArrayList,this,this);

        alldata.setLayoutManager(new LinearLayoutManager(this));
        alldata.setAdapter(catatanAdapter);
        Added.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Add.class));

            }
        });


        getallcatatan();


    }
    private void getallcatatan(){
        catatanMArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Load.setVisibility(View.GONE);
                catatanMArrayList.add(snapshot.getValue(CatatanM.class));
                catatanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Load.setVisibility(View.GONE);
                catatanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Load.setVisibility(View.GONE);
                catatanAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCatatanClick(int position) {
        displayBottomSheet(catatanMArrayList.get(position));

    }
    private void displayBottomSheet(CatatanM catatanM){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottom);
        Button goedit = layout.findViewById(R.id.GoEdit);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView catatan = layout.findViewById(R.id.TVcatatan);

        catatan.setText(catatanM.getCatatan());

        goedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Edit.class);
                i.putExtra(firebaseUser.getUid(), catatanM);
                startActivity(i);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                Toast.makeText(this,"User Logout", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
                this.finish();
                return true;
            default: return super.onOptionsItemSelected(item);

        }
    }
}