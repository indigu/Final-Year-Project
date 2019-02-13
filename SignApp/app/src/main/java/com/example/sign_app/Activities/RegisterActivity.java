package com.example.sign_app.Activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sign_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {

    // declaring variables
    ImageView userAvatar;
    static int PReqCode = 1;
    static int RequestCode = 1;
    Uri chosenImgUri;

    // variables used to store user registration information
    private EditText userName, userEmail, userPass1, userPass2;
    private ProgressBar loadingProgress;
    private Button regBtn;

    private FirebaseAuth userAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // inu views
        userName = findViewById(R.id.regName);
        userEmail = findViewById(R.id.regEmail);
        userPass1 = findViewById(R.id.regPass);
        userPass2 = findViewById(R.id.regPass2);

        loadingProgress = findViewById(R.id.progressBar);
        regBtn = findViewById(R.id.regButton);
        loadingProgress.setVisibility(View.INVISIBLE);

        userAuth = FirebaseAuth.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regBtn.setVisibility(View.INVISIBLE);
                loadingProgress.setVisibility(View.VISIBLE);

                final String name = userName.getText().toString();
                final String email = userEmail.getText().toString();
                final String password1 = userPass1.getText().toString();
                final String password2 = userPass2.getText().toString();

                // error check to make sure user has inputted in values
                if (name.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
                    // displays an error message to user
                    showMessage("Please make sure all fields are not empty");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);

                } else if (!password1.equals(password2)) {
                    // displays an error message to user
                    showMessage("Please make sure both passwords are equal");
                    regBtn.setVisibility(View.VISIBLE);
                    loadingProgress.setVisibility(View.INVISIBLE);
                } else {
                    // no errors
                    CreateUser(email, name, password1);
                }
            }
        });

        userAvatar = findViewById(R.id.regAvatar);

        userAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22) {
                    checkAndRequestPermission();
                } else {
                    openGallery();
                }
            }
        });
    }

    /*
    Functions for creating and updating users
    */

    private void CreateUser(String email, final String name, String password1) {
        // this method creates a user
        userAuth.createUserWithEmailAndPassword(email, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // account created successfully
                            showMessage("Account Registered!");
                            // update profile picture and name
                            updateUser(name, chosenImgUri, userAuth.getCurrentUser());
                        } else {
                            // account created unsuccessfully
                            showMessage("Something went wrong..." + task.getException().getMessage());
                            regBtn.setVisibility(View.VISIBLE);
                            loadingProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void updateUser(final String name, Uri chosenImgUri, final FirebaseUser currentUser) {

        if (chosenImgUri == null) {

            Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                    "://" + getResources().getResourcePackageName(R.drawable.logo)
                    + '/' + getResources().getResourceTypeName(R.drawable.logo) + '/' + getResources().getResourceEntryName(R.drawable.logo));

            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(imageUri)
                    .build();

            currentUser.updateProfile(profileUpdate)
                    .addOnCompleteListener((new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // user update success
                                showMessage("User registered!");
                                updateUI();
                            }
                        }
                    }));
        } else {
            // upload photo to FireBase
            StorageReference userStorage = FirebaseStorage.getInstance().getReference().child("user_photos");
            final StorageReference imageFilePath = userStorage.child(chosenImgUri.getLastPathSegment());
            imageFilePath.putFile(chosenImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // uploaded successfully
                    // get image URL
                    imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .setPhotoUri(uri)
                                    .build();

                            currentUser.updateProfile(profileUpdate)
                                    .addOnCompleteListener((new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // user update success
                                                showMessage("User registered!");
                                                updateUI();
                                            }
                                        }
                                    }));
                        }
                    });
                }
            });
        }
    }

    /*
    Function to change pages
    */
    private void updateUI() {
        Intent homePage = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(homePage);
        finish();
    }

    /*
    Function for displaying messages
    */
    private void showMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    /*
    Functions for opening gallery
    */
    private void openGallery() {
        // for older phones
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, RequestCode);
    }

    private void checkAndRequestPermission() {
        // for newer phones
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(RegisterActivity.this, "The app requires access to the storage to work", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        } else {
            openGallery();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RequestCode && data != null) {
            // this signals the user successfully picking an image
            // we then save its reference to a Uri variable
            chosenImgUri = data.getData();
            userAvatar.setImageURI(chosenImgUri);
        }
    }
}
