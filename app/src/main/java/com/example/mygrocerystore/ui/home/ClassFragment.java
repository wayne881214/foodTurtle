package com.example.mygrocerystore.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygrocerystore.R;
import com.example.mygrocerystore.adapter.HomeAdapter;
import com.example.mygrocerystore.adapter.PopularAdapters;
import com.example.mygrocerystore.adapter.RecommendedAdapter;
import com.example.mygrocerystore.models.HomeCategory;
import com.example.mygrocerystore.models.PopularModel;
import com.example.mygrocerystore.models.RecommendedModel;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ClassFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    RecyclerView popularRec, homeCatRec, recommendedRec;
    FirebaseFirestore db;
    //Popular items
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        String storetype = getActivity().getIntent().getStringExtra("storetype");
        View root = inflater.inflate(R.layout.fragment_class, container, false);
        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.class_rec);
        scrollView = root.findViewById(R.id.scroll_view);
        progressBar = root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        //Class items
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(), popularModelList);
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
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {

                                                PopularModel popularModel = document.toObject(PopularModel.class);
                                                popularModelList.add(popularModel);
                                                popularAdapters.notifyDataSetChanged();

                                                progressBar.setVisibility(View.GONE);
                                                scrollView.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
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

        return root;
    }
}