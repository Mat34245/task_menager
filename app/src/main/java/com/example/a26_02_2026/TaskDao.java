package com.example.a26_02_2026;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks  WHERE is_done= 0")
    List<Task> getTasks();
    @Query("SELECT * FROM tasks WHERE is_done= 1")
    List<Task> getDoneTasks();
    @Query("SELECT * FROM tasks WHERE name LIKE :name AND is_done= 0")
    List<Task> searchTasks(String name);
    @Query("SELECT * FROM tasks WHERE name LIKE :name AND is_done= 1")
    List<Task> searchDoneTasks(String name);
    @Insert
    void insertTask(Task task);
    @Query("UPDATE tasks SET name = :Name, icon = :Icon, due_date = :DueDate WHERE id = :Id")
    void updateTask(int Id, String Name, Integer Icon, String DueDate);
    @Query("DELETE FROM tasks WHERE id = :id")
    void deleteTask(int id);
    @Query("UPDATE tasks SET is_done = :changeIsDone WHERE id = :whereId")
    void updateIsDone(boolean changeIsDone, int whereId);
}
