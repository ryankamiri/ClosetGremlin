package com.example.closetgremlin;

import android.Manifest;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 22;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 2;

    private RecyclerView recyclerView;
    private FloatingActionButton addButton;

    private SQLite db;
    private ArrayList<ClothData> clothData;
    private boolean showCamera = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = new SQLite(this);

        // db.reset();

        // db.createTests();

        try {
            setClothes();
        } catch (ParseException e) {
            Log.d("setClothes", e.toString());
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!showCamera) {
                    return;
                }
                showCamera = false;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                else {
                    Toast.makeText(v.getContext(), "Camera not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public ArrayList<ClothData> getClothData() {
        return clothData;
    }

    public SQLite getDb() {
        return db;
    }


    public void setClothes() throws ParseException {
        clothData = new ArrayList<ClothData>();
        Cursor res = db.getData();
        while(res.moveToNext()){
            //Log.d("DATABASE", res.getString(0) + " " + res.getString(1) + " " + res.getString(2));
            clothData.add(new ClothData(res.getString(0), res.getString(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), (res.getInt(6) == 1)? true : false));
        }

        ClothingAdapter clothingAdapter = new ClothingAdapter(clothData, this, this);
        recyclerView.setAdapter(clothingAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && !showCamera) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Intent i = new Intent(getBaseContext(), AddActivity.class);
            Bundle dataBundle = new Bundle();
            dataBundle.putParcelable("bitmap", photo);
            i.putExtras(dataBundle);
            startActivity(i);
        }
        showCamera = true;
//        else {
//            Toast.makeText(this, "Error on Camera", Toast.LENGTH_SHORT).show();
//        }
    }

}