package com.example.a26_02_2026;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User
{
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "icon")
    public String icon;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "is_done")
    public boolean isDone;

    @ColumnInfo(name = "due_date")
    public String dueDate;

    public User(String icon, String name, boolean isDone, String dueDate) {
        this.icon = icon;
        this.name = name;
        this.isDone = isDone;
        this.dueDate = dueDate;
    }
}
