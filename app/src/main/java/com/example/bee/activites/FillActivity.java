package com.example.bee.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bee.HomeA;
import com.example.bee.R;
import com.example.bee.model.UserModel;
import com.example.bee.preferenData.PreferenceData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

public class FillActivity extends AppCompatActivity implements View.OnClickListener{
    String phone, StrName,StrUsername;
    EditText name, username;
    ImageView profilePic;
    UserModel userModel;
    FrameLayout frameLayout;
    MaterialButton btnSaqlash;
    StorageReference storageReference;
    FirebaseStorage storage;
   Uri imageUri,url;
   FirebaseDatabase database;
   DatabaseReference reference;
   PreferenceData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        initViews();
        data = new PreferenceData(this);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images");
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("user");
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,101);
            }
        });
        btnSaqlash.setOnClickListener(this);

    }

    private void initViews() {
        phone = getIntent().getStringExtra("phone");
        name = findViewById(R.id.edtName);
        username = findViewById(R.id.edtUsername);
        profilePic = findViewById(R.id.profilePic);
        frameLayout = findViewById(R.id.frameLay);
        btnSaqlash = findViewById(R.id.btnSaqlash);
    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                imageUri  = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profilePic.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(FillActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(FillActivity.this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        StrName = name.getText().toString();
        StrUsername = username.getText().toString();
        String key = reference.push().getKey();
        setImage(key);


    }

    public void setImage(String key){
        UploadTask task = storageReference.putFile(imageUri);
        storageReference.child( UUID.randomUUID().toString()).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       url = uri;
                        data.saveData("key",key);
                        data.saveData("name",StrName);
                        data.saveData("url", String.valueOf(url));
                        userModel = new UserModel(StrName,phone,StrUsername,key,String.valueOf(url),"",0);
                        reference.child(key).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    startActivity(new Intent(FillActivity.this, HomeA.class));
                                    finish();
                                }
                            }
                        });

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}