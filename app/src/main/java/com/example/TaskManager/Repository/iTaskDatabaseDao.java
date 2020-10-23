package com.example.TaskManager.Repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.TaskManager.Models.Task;
import com.example.TaskManager.Models.User;

import java.util.List;

@Dao
public interface iTaskDatabaseDao {

    @Query("SELECT * From tasks")
    List<Task> getTaskList();

    @Insert(entity = Task.class)
    void insertTask (Task task);

    @Delete(entity = Task.class)
    void deleteTask (Task task);

    @Update(entity = Task.class)
    void updateTask (Task task);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();

    @Query("SELECT * FROM users")
    List<User> getUserList();

    @Insert(entity = User.class)
    void insertUser (User user);

    @Delete(entity = User.class)
    void deleteUser (User user);

    @Update(entity = User.class)
    void updateUser (User user);

    @Query("DELETE FROM users")
    void deleteAllUsers();
}
