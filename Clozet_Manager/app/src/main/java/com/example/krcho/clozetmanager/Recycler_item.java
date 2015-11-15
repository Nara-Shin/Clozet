package com.example.krcho.clozetmanager;

/**
 * Created by ShinNara on 2015-11-13.
 */
public class Recycler_item {
    int image;
    String title;

    int getImage(){
        return this.image;
    }

    String getTitle(){
        return this.title;
    }

    Recycler_item(int image, String title){
        this.image=image;
        this.title=title;
    }
}
