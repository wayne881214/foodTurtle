package com.example.mygrocerystore.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygrocerystore.R;
import com.example.mygrocerystore.adapter.PopularAdapters;
import com.example.mygrocerystore.models.PopularModel;
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

public class ClassActivity extends AppCompatActivity {


    ProgressBar progressBar;
    FirebaseFirestore db;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView popularRec;
    Toolbar toolbar;

    //Popular items
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String storetype = getIntent().getStringExtra("type");
        switch(storetype){
            case "breakfast":
                toolbar.setTitle("早餐店");
                break;
            case "dessert":
                toolbar.setTitle("點心");
                break;
            case "hotpot":
                toolbar.setTitle("火鍋");
                break;
            case "convenient":
                toolbar.setTitle("便當");
                break;
            case "drink":
                toolbar.setTitle("飲料");
                break;
            default:
                toolbar.setTitle("nofound");
                break;
        }
        db = FirebaseFirestore.getInstance();
        popularRec = findViewById(R.id.class_rec);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        popularRec.setLayoutManager(new LinearLayoutManager(this));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(this,popularModelList);
        popularRec.setAdapter(popularAdapters);
        DatabaseReference StoresRef = database.getReference("Stores");
        StoresRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String type =ds.child("type").getValue().toString();
                    if (storetype != null && storetype.equalsIgnoreCase(type)) {
                        db.collection("PopularProducts").whereEqualTo("type",type).get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                                PopularModel popularModel = documentSnapshot.toObject(PopularModel.class);
                                                popularModelList.add(popularModel);
                                                popularAdapters.notifyDataSetChanged();
                                                progressBar.setVisibility(View.GONE);
                                                popularRec.setVisibility(View.VISIBLE);
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
}