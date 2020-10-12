package com.example.homework12.Repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.homework12.Models.Task;
import com.example.homework12.Models.User;

import java.util.List;

@Dao
public interface iTaskDatabaseDao {

    @Query("SELECT * From tasks")
    List<Task> getTaskList();

    @Insert
    void insertTask (Task task);

    @Delete
    void deleteTask (Task task);

    @Update
    void updateTask (Task task);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();

    @Query("SELECT * FROM users")
    List<User> getUserList();

    @Insert
    void insertUser (User user);

    @Delete
    void deleteUser (User user);

    @Update
    void updateUser (User user);

    @Query("DELETE FROM users")
    void deleteAllUsers();
}
