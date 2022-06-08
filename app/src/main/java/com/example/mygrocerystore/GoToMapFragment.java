package com.example.mygrocerystore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mygrocerystore.activities.MapActivity;

public class GoToMapFragment extends Fragment {
    Activity activity;
    Button button;
    public GoToMapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_go_to_map, container, false);
        activity = getActivity();
        button = root.findViewById(R.id.go_to_map);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, MapActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}