package com.example.closetgremlin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

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
    }
}