package com.example.closetgremlin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText name;
    private EditText type;
    private EditText occasion;
    private EditText location;
    private Button addButton;
    private Button cancelButton;

    private Bitmap imageBitmap;
    private SQLite db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        imageView = findViewById(R.id.addImageView);
        name = findViewById(R.id.editName);
        type = findViewById(R.id.editType);
        occasion = findViewById(R.id.editOccasion);
        location = findViewById(R.id.editLocation);
        addButton = findViewById(R.id.addButton);
        cancelButton = findViewById(R.id.cancelButton);

        db = new SQLite(this);

        Bundle extras = getIntent().getExtras();
        imageBitmap = (Bitmap) extras.getParcelable("bitmap");

        imageView.setImageBitmap(imageBitmap);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = addClothing();
                if (result) {
                    switchToMain();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToMain();
            }
        });

    }

    private String saveImage(Bitmap imageBitmap) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // Get the directory for saving images
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        try {
            // Create a temporary file to save the image
            File imageFile = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save the image to the file
            try (FileOutputStream out = new FileOutputStream(imageFile)) {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                Log.d("ImageCapture", "Image saved: " + imageFile.getAbsolutePath());
                return imageFile.getAbsolutePath();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }

        return "failed";
    }

    private boolean addClothing() {
        if(name.getText().toString().equals("") || type.getText().toString().equals("") || occasion.getText().toString().equals("") || location.getText().toString().equals(""))
            return false;
        // First save image
        String imagePath = saveImage(imageBitmap);
        if (imagePath == "failed") {
            return false;
        }
        boolean result = db.insert(imagePath, name.getText().toString(), type.getText().toString(), occasion.getText().toString(), location.getText().toString(), true);
        if (result) {
            Log.d("DATABASE", "Clothing has been added successfully.");
        }
        else
            Log.d("DATABASE", "Clothing failed to add.");
        return result;

    }

    private void switchToMain() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
    }

}