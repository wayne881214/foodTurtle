package com.example.mygrocerystore.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mygrocerystore.R;
import com.example.mygrocerystore.databinding.FragmentGalleryBinding;
import com.example.mygrocerystore.models.PopularModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {
	Button selectImg, uploadImg,addStoreButton;


	private EditText etName;

	private EditText etFreight;
	private EditText etFraction;
	private EditText etType;
	private EditText etDes;
	private EditText etImg;
	FirebaseStorage storage;

	Uri imageUri;

	String storename="";
	String FoodType="";
	String FoodName="null";
	String Name="null";
    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

			System.out.println("!!!!!!!!!!!!");
			View root = inflater.inflate(R.layout.fragment_gallery, container, false);

			selectImg=root.findViewById(R.id.selectImg);
			uploadImg=root.findViewById(R.id.uploadImg);


			etName =  root.findViewById(R.id.storeName1);
			etName = root.findViewById(R.id.storeName1);
			etFreight = root.findViewById(R.id.storeFreight1);
			etFraction = root.findViewById(R.id.storeFraction1);
			etType = root.findViewById(R.id.storeType1);
			etImg = root.findViewById(R.id.storeImg);
			etDes = root.findViewById(R.id.storeDes);

			addStoreButton=root.findViewById(R.id.addStoreButton);

			selectImg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_GET_CONTENT);
					intent.setType("image/*");
					startActivityForResult(intent, 33);
				}
			});
			addStoreButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					addStore1();
				}
			});
			return root;
    }

	public void addStore1() {


		String Name = etName.getText().toString();
		String storeName = etName.getText().toString();
		String Freight = etFreight.getText().toString();
		String Fraction = etFraction.getText().toString();
		String Type = etType.getText().toString();
		String Des = etDes.getText().toString();


		FirebaseFirestore firebaseDatabase = FirebaseFirestore.getInstance();
		PopularModel store =new PopularModel(Name,Des,Fraction,Freight,storeName,Type,imageUri.toString());
		firebaseDatabase.collection("PopularProducts").document(Name).set(store);
		//Real time
		FirebaseDatabase firebaseDatabase2 = FirebaseDatabase.getInstance();
		DatabaseReference storesRef = firebaseDatabase2.getReference("Stores");
		DatabaseReference nameRef = storesRef.child(FirebaseAuth.getInstance().getUid());
		Map<String, Object> store2 = new HashMap<>();
		store2.put("storeName", Name);
		store2.put("type", Type);
		nameRef.updateChildren(store2);

	}
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data.getData() != null) {
			Uri profileUri = data.getData();
			storage= FirebaseStorage.getInstance();
			Name = etName.getText().toString();
			final StorageReference reference = storage.getReference().child("store_picture").child(Name);
			reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
				@Override
				public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
					reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
						@Override
						public void onSuccess(Uri uri) {
							imageUri=uri;
							System.out.println("RRRRRRR"+imageUri);
						}
					});
				}
			});
		}
	}
}