package com.example.mygrocerystore.ui.slideshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mygrocerystore.AddStoreActivity;
import com.example.mygrocerystore.R;
import com.example.mygrocerystore.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

	Activity activity;
	Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

			View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
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