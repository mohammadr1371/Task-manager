package com.example.homework12.Repository;



import android.icu.util.TaiwanCalendar;

import com.example.homework12.Models.Task;
import com.example.homework12.Models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class TaskRepository {

    public static TaskRepository sTaskRepository;

    public static TaskRepository getInstance(){
        if(sTaskRepository == null){
            sTaskRepository = new TaskRepository();
            return sTaskRepository;
        }
        return sTaskRepository;
    }
    HashMap<String , ArrayList<ArrayList<Task>>> mTaskOfUser = new HashMap<>();
    ArrayList<ArrayList<Task>> mArrayListsOfLists = new ArrayList<>();

    private TaskRepository() {
    }

    public ArrayList<ArrayList<Task>> getArrayListsOfLists(String username) {
        mArrayListsOfLists = mTaskOfUser.get(username);
        return mArrayListsOfLists;
    }

    public void insertTodoTaskList(String name, String username, String description){
        Task task = new Task(name, 0, description );
        ArrayList<Task> mTodoTaskList = mTaskOfUser.get(username).get(0);
        mTodoTaskList.add(task);
    }

    public void insertDoingTaskList(String name, String username, String description){
        Task task = new Task(name, 1, description);
        ArrayList<Task> mDoingTaskList = mTaskOfUser.get(username).get(1);
        mDoingTaskList.add(task);
    }

    public void insertDoneTaskList(String name, String username, String description){
        Task task = new Task(name, 2, description);
        ArrayList<Task> mDoneTaskList = mTaskOfUser.get(username).get(2);
        mDoneTaskList.add(task);
    }

    public void setTask(Task inputTask){
        for (int i=0; i<3; i++){
            for (Task task: mArrayListsOfLists.get(i)) {
                if(task.getUUID().equals(inputTask.getUUID())){
                    task.setName(inputTask.getName());
                    task.setState(inputTask.getState());
                    task.setDate(inputTask.getDate());
                    return;
                }
            }
        }
    }


    public void addArrayListOfUser(String username){
        if(mTaskOfUser.containsKey(username)){
            return;
        } else {
            ArrayList<ArrayList<Task>> arrayLists = new ArrayList<>();
            ArrayList<Task> ToDOTasksList = new ArrayList<>();
            ArrayList<Task> DoingTasksList = new ArrayList<>();
            ArrayList<Task> DoneTasksList = new ArrayList<>();
            arrayLists.add(ToDOTasksList);
            arrayLists.add(DoingTasksList);
            arrayLists.add(DoneTasksList);
            mTaskOfUser.put(username, arrayLists);
        }

    }
}
