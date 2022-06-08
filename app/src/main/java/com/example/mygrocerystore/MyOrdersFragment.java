package com.example.mygrocerystore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.mygrocerystore.activities.OrderDetail;
import com.example.mygrocerystore.adapter.CustomerOrderAdapter;
import com.example.mygrocerystore.adapter.MyCartAdapter;
import com.example.mygrocerystore.models.MyCartModel;
import com.example.mygrocerystore.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersFragment extends Fragment {

    FirebaseDatabase database;
    FirebaseAuth auth;
    RecyclerView type1View,type2View,type3View,type4View;
    CustomerOrderAdapter type1Adapter,type2Adapter,type3Adapter,type4Adapter;
    List<MyCartModel> type1lList,type2lList,type3lList,type4lList;
    String name;
    ProgressBar progressBar;

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        progressBar = root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        OrderDetail.customer=userModel.getName();
                        name=userModel.getName();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        type1View = root.findViewById(R.id.type1rec);
        type2View = root.findViewById(R.id.type2rec);
        type3View = root.findViewById(R.id.type3rec);
        type4View = root.findViewById(R.id.type4rec);

        type1View.setVisibility(View.GONE);
        type2View.setVisibility(View.GONE);
        type3View.setVisibility(View.GONE);
        type4View.setVisibility(View.GONE);


        //待商家接受訂單
        type1View.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        type1lList = new ArrayList<>();
        type1Adapter = new CustomerOrderAdapter(getActivity(),type1lList);
        type1View.setAdapter(type1Adapter);
        DatabaseReference type1Ref = database.getReference().child("Orders");
        type1Ref.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("customer").getValue().toString().equals(OrderDetail.customer)&&
                            ds.child("type").getValue().toString().equals("1")
                    ) {
                        int key=Integer.parseInt(ds.child("key").getValue().toString());
                        String store = ds.child("store").getValue().toString();
                        String customer = ds.child("customer").getValue().toString();
                        String delivery = ds.child("delivery").getValue().toString();
                        int type = Integer.parseInt(ds.child("type").getValue().toString());
                        String remark= ds.child("remark").getValue().toString();
                        String payment= ds.child("payment").getValue().toString();
                        String address= ds.child("address").getValue().toString();
                        type1lList.add(new MyCartModel(key,store, customer, delivery, type,remark,payment,address));
                        type1Adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        type1View.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        //待外送員接受訂單
        type2View.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        type2lList = new ArrayList<>();
        type2Adapter = new CustomerOrderAdapter(getActivity(),type2lList);
        type2View.setAdapter(type2Adapter);
        DatabaseReference type2Ref = database.getReference().child("Orders");
        type2Ref.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("customer").getValue().toString().equals(OrderDetail.customer)&&
                            ds.child("type").getValue().toString().equals("2")
                    ) {
                        int key=Integer.parseInt(ds.child("key").getValue().toString());
                        String store = ds.child("store").getValue().toString();
                        String customer = ds.child("customer").getValue().toString();
                        String delivery = ds.child("delivery").getValue().toString();
                        int type = Integer.parseInt(ds.child("type").getValue().toString());
                        String remark= ds.child("remark").getValue().toString();
                        String payment= ds.child("payment").getValue().toString();
                        String address= ds.child("address").getValue().toString();
                        type2lList.add(new MyCartModel(key,store, customer, delivery, type,remark,payment,address));
                        type2Adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        type2View.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });



        //待接收訂單
        type3View.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        type3lList = new ArrayList<>();
        type3Adapter = new CustomerOrderAdapter(getActivity(),type3lList);
        type3View.setAdapter(type3Adapter);
        DatabaseReference type3Ref = database.getReference().child("Orders");
        type3Ref.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("customer").getValue().toString().equals(OrderDetail.customer)&&
                            ds.child("type").getValue().toString().equals("3")
                    ) {
                        int key=Integer.parseInt(ds.child("key").getValue().toString());
                        String store = ds.child("store").getValue().toString();
                        String customer = ds.child("customer").getValue().toString();
                        String delivery = ds.child("delivery").getValue().toString();
                        int type = Integer.parseInt(ds.child("type").getValue().toString());
                        String remark= ds.child("remark").getValue().toString();
                        String payment= ds.child("payment").getValue().toString();
                        String address= ds.child("address").getValue().toString();
                        type3lList.add(new MyCartModel(key,store, customer, delivery, type,remark,payment,address));
                        type3Adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        type3View.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        //已完成訂單

        type4View.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
        type4lList = new ArrayList<>();
        type4Adapter = new CustomerOrderAdapter(getActivity(),type4lList);
        type4View.setAdapter(type4Adapter);
        DatabaseReference type4Ref = database.getReference().child("Orders");
        type4Ref.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.child("customer").getValue().toString().equals(OrderDetail.customer)&&
                            ds.child("type").getValue().toString().equals("4")
                    ) {
                        int key=Integer.parseInt(ds.child("key").getValue().toString());
                        String store = ds.child("store").getValue().toString();
                        String customer = ds.child("customer").getValue().toString();
                        String delivery = ds.child("delivery").getValue().toString();
                        int type = Integer.parseInt(ds.child("type").getValue().toString());
                        String remark= ds.child("remark").getValue().toString();
                        String payment= ds.child("payment").getValue().toString();
                        String address= ds.child("address").getValue().toString();
                        type4lList.add(new MyCartModel(key,store, customer, delivery, type,remark,payment,address));
                        type4Adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        type4View.setVisibility(View.VISIBLE);
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