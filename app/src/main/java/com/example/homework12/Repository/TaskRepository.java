package com.example.homework12.Repository;



import android.icu.util.TaiwanCalendar;

import com.example.homework12.Models.Task;
import com.example.homework12.Models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class TaskRepository {

    public static final String ADMIN = "admin";
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
        initAdmin();
    }

    private void initAdmin() {
        mTaskOfUser.put(ADMIN, new ArrayList<ArrayList<Task>>());
        ArrayList<Task> ToDOTasksList = new ArrayList<>();
        ArrayList<Task> DoingTasksList = new ArrayList<>();
        ArrayList<Task> DoneTasksList = new ArrayList<>();
        mTaskOfUser.get(ADMIN).add(ToDOTasksList);
        mTaskOfUser.get(ADMIN).add(DoingTasksList);
        mTaskOfUser.get(ADMIN).add(DoneTasksList);
    }

    public ArrayList<ArrayList<Task>> getArrayListsOfLists(String username) {
        mArrayListsOfLists = mTaskOfUser.get(username);
        return mArrayListsOfLists;
    }

    public void insertTodoTaskList(Task task, String username){
        ArrayList<Task> mTodoTaskList = mTaskOfUser.get(username).get(0);
        mTodoTaskList.add(task);
        if(!username.equals(ADMIN)) {
            ArrayList<Task> adminTodoTaskList = mTaskOfUser.get(ADMIN).get(0);
            adminTodoTaskList.add(task);
        }
    }

    public void insertDoingTaskList(Task task, String username){
        ArrayList<Task> mDoingTaskList = mTaskOfUser.get(username).get(1);
        mDoingTaskList.add(task);
        if(!username.equals(ADMIN)) {
            ArrayList<Task> adminDoingTaskList = mTaskOfUser.get(ADMIN).get(1);
            adminDoingTaskList.add(task);
        }
    }

    public void insertDoneTaskList(Task task, String username){
        ArrayList<Task> mDoneTaskList = mTaskOfUser.get(username).get(2);
        mDoneTaskList.add(task);
        if(!username.equals(ADMIN)) {
            ArrayList<Task> adminDoneTaskList = mTaskOfUser.get(ADMIN).get(2);
            adminDoneTaskList.add(task);
        }
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

    public void deleteTask(Task inputTask){
        for (int i=0; i<3; i++){
            for (Task task: mArrayListsOfLists.get(i)) {
                if(task.getUUID().equals(inputTask.getUUID())){
                    mArrayListsOfLists.get(i).remove(task);
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
