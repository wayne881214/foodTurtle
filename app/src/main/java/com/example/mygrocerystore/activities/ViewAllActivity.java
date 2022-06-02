package com.example.mygrocerystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.mygrocerystore.MainActivity;
import com.example.mygrocerystore.R;
import com.example.mygrocerystore.adapter.ViewAllAdapter;
import com.example.mygrocerystore.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ViewAllModel> viewAllModelList;
    Toolbar toolbar;
    ProgressBar progressBar;
    String storeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        storeName = getIntent().getStringExtra("storeName");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        firestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.view_all_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this, viewAllModelList);
        recyclerView.setAdapter(viewAllAdapter);
        toolbar.setTitle(storeName);
        DatabaseReference StoresRef = database.getReference("Stores");
        StoresRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String name =ds.child("storeName").getValue().toString();
                    if (storeName != null && storeName.equalsIgnoreCase(name)) {
                        firestore.collection("AllProducts").whereEqualTo("storeName", name).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                        for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()) {
                                            ViewAllModel viewAllModel = documentSnapshot.toObject(ViewAllModel.class);
                                            viewAllModelList.add(viewAllModel);
                                            viewAllAdapter.notifyDataSetChanged();
                                            progressBar.setVisibility(View.GONE);
                                            recyclerView.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                        break;
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void shopcar(View view){
        Intent intent=new Intent();
        intent.setClass(ViewAllActivity.this, shopcarActivity.class);
        intent.putExtra("storeName",storeName);
        startActivity(intent);
    }
}