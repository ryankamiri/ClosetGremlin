package com.example.closetgremlin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 22;

    private RecyclerView recyclerView;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addButton = findViewById(R.id.addButton);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ClothData[] clothData = new ClothData[]{
                new ClothData("Avengers","2019 film",R.drawable.avenger),
                new ClothData("Venom","2018 film",R.drawable.venom),
                new ClothData("Batman Begins","2005 film",R.drawable.batman),
                new ClothData("Jumanji","2019 film",R.drawable.jumanji),
                new ClothData("Good Deeds","2012 film",R.drawable.good_deeds),
                new ClothData("Hulk","2003 film",R.drawable.hulk),
                new ClothData("Avatar","2009 film",R.drawable.avatar),
        };

        ClothingAdapter clothingAdapter = new ClothingAdapter(clothData, this);
        recyclerView.setAdapter(clothingAdapter);

        Log.println(Log.DEBUG, "onCreate", "App has been created");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.println(Log.DEBUG, "AddButton", "Button was clicked");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Log.println(Log.DEBUG, "AddButton", "Took Picture");
            Bitmap photo = (Bitmap) data.getExtras().get("data");
        }
        else {
            Log.println(Log.ERROR, "AddButton", "Error on opening camera");
            Toast.makeText(this, "Error on Camera", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}