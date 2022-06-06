package com.example.mygrocerystore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mygrocerystore.activities.LoginActivity;
import com.example.mygrocerystore.activities.RegistrationActivity;
import com.google.android.gms.tasks.OnCompleteListener;

import java.io.Serializable;

public class DebugFragment extends Fragment {

	Activity activity;
	Button button;
	public DebugFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
														 Bundle savedInstanceState) {
			// Inflate the layout for this fragment
			View root = inflater.inflate(R.layout.fragment_debug, container, false);

			activity = getActivity();
			button = root.findViewById(R.id.button);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(activity, AddStoreActivity.class);
					startActivity(intent);
				}
			});

			return root;
    }



}