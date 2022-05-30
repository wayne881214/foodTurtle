package fcu.app.foodturtle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.json.JSONObject;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import android.os.Bundle;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.UnsupportedEncodingException;
import java.util.Base64;


public class chatActivity extends AppCompatActivity {

    LinearLayout layout;
    ImageView sendButton;
    ImageView sendPicture;
    ImageView takePicture;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1;
    FirebaseStorage storage;
    String imagePath = "";
    String cloudImageName = "";
    BitmapFactory.Options bfoOptions = new BitmapFactory.Options();
    private Thread thread;
    final Base64.Decoder decoder = Base64.getDecoder();
    final Base64.Encoder encoder = Base64.getEncoder();


    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = (LinearLayout) findViewById(R.id.layout1);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        sendPicture = (ImageView) findViewById(R.id.sendPicture);
        takePicture = (ImageView)findViewById(R.id.takePicture);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        //取得firebase storage
        storage = FirebaseStorage.getInstance();
        String yourDatabaseURL="https://foodturtle-dfb4c-default-rtdb.firebaseio.com/";
        Firebase.setAndroidContext(this);
        reference1 = new Firebase(yourDatabaseURL+"messages/" +UserDetail.order);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();
                messageArea.setText("");
                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    try {
                        final byte[] textByte = messageText.getBytes("UTF-8");
                        final String encodedText = encoder.encodeToString(textByte);
                        map.put("message", encodedText);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
//                    map.put("message", messageText);
                    map.put("user", UserDetail.username);
                    reference1.push().setValue(map);
                }
            }
        });
        takePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openCamera();
            }
        });
        //傳送圖片
        sendPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fill in code by yourself
                Intent intent = new Intent();
                intent.setType("image/*");  // 開啟Pictures畫面Type設定為image
                intent.setAction(Intent.ACTION_GET_CONTENT);  // 使用Intent.ACTION_GET_CONTENT
                startActivityForResult(intent,1);  // 取得相片後，返回
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if (userName.equals(UserDetail.username)) {
                    addMessageBox("You:\n", message, 1);
                } else {
                    addMessageBox(userName + ":\n", message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void addMessageBox(String message, String original, int type) {
        try {
            message = message + new String(decoder.decode(original), "UTF-8");
            original = new String(decoder.decode(original), "UTF-8");
        }catch (Exception e){
        }

            TextView textView = new TextView(chatActivity.this);
            textView.setText(message);
            if (type == 1) {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(750, 10, 20, 20);
                textView.setLayoutParams(lp);
            } else {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(20, 10, 20, 20);
                textView.setLayoutParams(lp);
            }
            if (type == 1) {
                textView.setBackgroundResource(R.drawable.rounded_corner1);
            } else {
                textView.setBackgroundResource(R.drawable.rounded_corner2);
            }
            layout.addView(textView);
            scrollView.fullScroll(View.FOCUS_DOWN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String imageType = ".jpg";
        Bitmap bitmap = null;
        if (resultCode == RESULT_OK ) {
            //路徑轉換
            if(requestCode == 1 ){
                Uri uri = data.getData();
                if (DocumentsContract.isDocumentUri(this, uri)) {
                    String docId = DocumentsContract.getDocumentId(uri);
                    if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                        //Fill in code by yourself
                        String id = docId.split(":")[1];
                        String selection = MediaStore.Images.Media._ID + "=" + id;
                        imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                    } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                        //Fill in code by yourself
                        Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"),
                                Long.valueOf(docId));
                        imagePath = getImagePath(contentUri, null);
                    }
                } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                    //Fill in code by yourself
                    imagePath = getImagePath(uri, null);
                }
                Log.e("url: ", imagePath);
                //判斷圖片型態
                if (imagePath.indexOf(".jpg") > -1) {
                    imageType = ".jpg";
                } else if (imagePath.indexOf(".png") > -1) {
                    imageType = ".png";
                } else if (imagePath.indexOf(".gif") > -1) {
                    imageType = ".gif";
                }
                //選擇的圖片轉換成bitmap
                bitmap = BitmapFactory.decodeFile(imagePath, bfoOptions);

            }
            else if(requestCode == 2){
                //Fill in code by yourself
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
            }
            //建立時戳
            Long tsLong = System.currentTimeMillis() / 1000;
            String ts = tsLong.toString();
            //要圖片的新檔名
            String picname = UserDetail.username + "-" + ts + imageType;
            //要上傳到遠端路徑
            StorageReference imageRef = storage.getReference().child(picname);
            byte[] data2 = null ;
            if(imagePath.indexOf(".gif") > -1){
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(imagePath);
                    data2 = new byte[fis.available()];
                    fis.read(data2);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                //Fill in code by yourself
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                data2 = baos.toByteArray();
            }
            UploadTask uploadTask = imageRef.putBytes(data2);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    // Continue with the task to get the download URL
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Map<String, String> map = new HashMap<String, String>();
                        try {
                            final byte[] textByte;
                            textByte = String.valueOf(downloadUri).getBytes("UTF-8");
                            final String encodedText = encoder.encodeToString(textByte);
                            map.put("message", String.valueOf(encodedText));

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        //map.put("message", String.valueOf(downloadUri));
                        map.put("user", UserDetail.username);
                        reference1.push().setValue(map);

                    } else {

                    }
                }
            });
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("Range")
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
    public  void openCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, 2);
        } catch (ActivityNotFoundException e) {
            // display error state to the user
        }
    }
}