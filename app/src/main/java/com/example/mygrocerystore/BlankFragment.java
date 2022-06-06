package com.example.mygrocerystore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mygrocerystore.models.ViewAllModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class BlankFragment extends Fragment {
	Button selectImg, uploadImg,b1,addButton;


	private EditText etName;

	private EditText etFoodName;
	private EditText etFoodCommit;
	private EditText etFoodMoney;
	private EditText etFoodType;
	private EditText etFoodRate;
	private EditText etFoodImg;
	FirebaseStorage storage;

	Uri imageUri;

	String storename="";
	String FoodType="";
	String FoodName="null";
	String Name="null";

	// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//				setContentView(R.layout.activity_start);

		}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
			System.out.println("!!!!!!!!!!!!");
			View root = inflater.inflate(R.layout.fragment_blank, container, false);

			selectImg=root.findViewById(R.id.selectImg);
			uploadImg=root.findViewById(R.id.uploadImg);

			etName =  root.findViewById(R.id.storeName1);
			etFoodName = root.findViewById(R.id.foodName);
			etFoodCommit = root.findViewById(R.id.foodDes);
			etFoodMoney = root.findViewById(R.id.foodPrice1);
			etFoodType = root.findViewById(R.id.foodType);
			etFoodRate = root.findViewById(R.id.foodRate);

			addButton=root.findViewById(R.id.addButton);

			selectImg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");
					startActivityForResult(intent, 33);
				}
			});
			addButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					addFood1();
				}
			});
        return root;
    }

	public void addFood1() {
    Name = etName.getText().toString();
		FoodName = etFoodName.getText().toString();
		String FoodCommit = etFoodCommit.getText().toString();
		String FoodMoney = etFoodMoney.getText().toString();
		String FoodRate = etFoodRate.getText().toString();

		FirebaseDatabase database;
		database = FirebaseDatabase.getInstance();
		FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();

		ViewAllModel food=new ViewAllModel(FoodName,FoodCommit,FoodRate,Name,FoodType,imageUri.toString(),Integer.parseInt(FoodMoney));
		firebaseDatabase.collection("AllProducts").document(FoodName).set(food);
//		Toast.makeText(this,FoodName+"新增成功", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (data.getData() != null) {
			Uri profileUri = data.getData();
			storage= FirebaseStorage.getInstance();
			Name = etName.getText().toString();
			FoodName = etFoodName.getText().toString();
			System.out.println("YYYYYYYYYY"+Name+"!"+FoodName);
			final StorageReference reference = storage.getReference().child("food_picture").child(Name+"_"+FoodName);
			reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
				@Override
				public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
					reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
						@Override
						public void onSuccess(Uri uri) {
							imageUri=uri;
						}
					});
				}
			});
		}
	}

}