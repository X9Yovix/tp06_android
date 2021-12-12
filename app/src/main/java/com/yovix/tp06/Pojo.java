package com.yovix.tp06;

import androidx.annotation.NonNull;

import java.util.Comparator;

public class Pojo {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Pojo(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() { return "name=: " + name; }

    public static Comparator<Pojo> listAtoZ = (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName());
    public static Comparator<Pojo> listZtoA = (o1, o2) -> o2.getName().compareToIgnoreCase(o1.getName());
}
