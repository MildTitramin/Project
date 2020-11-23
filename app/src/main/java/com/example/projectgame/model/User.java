package com.example.projectgame.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "info")
public class User {
    @PrimaryKey(autoGenerate = true)
    public final int id;

    @ColumnInfo(name = "name")
    public final String name;

    @ColumnInfo(name = "bbe")
    public final String bbe;

    @ColumnInfo(name = "mfg")
    public final String mfg;

    @ColumnInfo(name = "exp")
    public final String exp;


    public User(int id, String name, String bbe, String mfg, String exp) {
        this.id = id;
        this.name = name;
        this.bbe = bbe;
        this.mfg = mfg;
        this.exp = exp;
    }
}

