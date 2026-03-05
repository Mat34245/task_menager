package com.example.a26_02_2026;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users  WHERE is_done= 0")
    List<User> getUsers();

    @Query("SELECT * FROM users WHERE is_done= 1")
    List<User> getDoneTasks();

    @Insert
    void insertUser(User user);

    @Query("UPDATE users SET is_done = :changeIsDone WHERE id = :whereId")
    void updateIsDone(boolean changeIsDone, int whereId);
}
