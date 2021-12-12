package com.yovix.tp06;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class myApp extends Application {
    private static List<Pojo> myList;
    private Context myContext;
    String[] country_names;

    public myApp() {
        myList = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myContext = this;
        country_names = myContext.getResources().getStringArray(R.array.country_names);
        insertElements();
    }

    public List<Pojo> getMyList() {
        return myList;
    }

    public void setMyList(List<Pojo> myList) {
        myApp.myList = myList;
    }

    public Context getContext(){
        return myContext;
    }

    public void insertElements(){
        for(String pays:country_names){
            myList.add(new Pojo(pays));
        }
    }
}
