package com.example.ba9_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class AddPostActivity extends AppCompatActivity {

    private Button mAddPostBtn;
    private EditText mCaptionText;
    private ImageView mPostImage;
    private ProgressBar mProgressBar;
    private Uri postImageUri = null;
    private StorageReference storageReference;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    private String currentUserId;
    private Toolbar postToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mAddPostBtn = findViewById(R.id.save_post_btn);
        mCaptionText = findViewById(R.id.caption_text);
        mPostImage = findViewById(R.id.post_image);
        mProgressBar = findViewById(R.id.post_progressBar);
        mProgressBar.setVisibility(View.INVISIBLE);
        postToolbar = findViewById(R.id.addpost_toolbar);
        setSupportActionBar(postToolbar);
        getSupportActionBar().setTitle("Add Post");

        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        currentUserId = auth.getCurrentUser().getUid();

        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        mAddPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPost();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    private void addPost() {
        mProgressBar.setVisibility(View.VISIBLE);
        String caption = mCaptionText.getText().toString();

        if (!caption.isEmpty() && postImageUri != null) {
            StorageReference postRef = storageReference.child("post_images").child(FieldValue.serverTimestamp().toString() + ".jpg");
            postRef.putFile(postImageUri)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            postRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                HashMap<String, Object> postMap = new HashMap<>();
                                postMap.put("image", uri.toString());
                                postMap.put("user", "medamine_baklouti");
                                postMap.put("caption", caption);
                                postMap.put("time", FieldValue.serverTimestamp());

                                mProgressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(AddPostActivity.this, "Post Added Successfully !!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddPostActivity.this, MainActivity.class));

                                firestore.collection("Posts").add(postMap)
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {

                                            } else {
                                                mProgressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(AddPostActivity.this, task1.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            });
                        } else {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(AddPostActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(AddPostActivity.this, "Please Add Image and Write Your caption", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            postImageUri = data.getData();
            mPostImage.setImageURI(postImageUri);
        }
    }
}
