package com.example.a26_02_2026;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task
{
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "icon")
    public Integer icon;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "is_done")
    public boolean isDone;

    @ColumnInfo(name = "due_date")
    public String dueDate;

    public Task(Integer icon, String name, boolean isDone, String dueDate) {
        this.icon = icon;
        this.name = name;
        this.isDone = isDone;
        this.dueDate = dueDate;
    }
}
