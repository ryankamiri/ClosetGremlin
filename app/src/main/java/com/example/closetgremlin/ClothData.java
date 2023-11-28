package com.example.closetgremlin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;

public class ClothData {
    private String id;
    private Bitmap clothingImage;
    private int clothingResource;
    private String name;
    private String type;
    private String occasion;
    private String location;
    private Boolean bitmap;

    public ClothData(String id, String imagePath, String name, String type, String occasion, String location, Boolean bitmap) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.occasion = occasion;
        this.location = location;
        this.bitmap = bitmap;

        if(this.bitmap) {
            this.clothingImage = loadImageFromStorage((imagePath));
        }
        else {
            this.clothingResource = Integer.parseInt(imagePath);
        }
    }

    private Bitmap loadImageFromStorage(String imagePath) {
        try {
            File file = new File(imagePath);
            if (file.exists()) {
                return BitmapFactory.decodeFile(file.getAbsolutePath());
            } else {
                Log.e("ImageLoad", "File not found: " + file.getAbsolutePath());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ImageLoad", "Error loading image from storage");
            return null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getClothingImage() {
        return clothingImage;
    }

    public void setClothingImage(Bitmap clothingImage) {
        this.clothingImage = clothingImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getBitmap() {
        return bitmap;
    }

    public void setBitmap(Boolean bitmap) {
        this.bitmap = bitmap;
    }

    public int getClothingResource() {
        return clothingResource;
    }

    public void setClothingResource(int clothingResource) {
        this.clothingResource = clothingResource;
    }
}
