package com.example.TaskManager.Repository;



import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.TaskManager.Models.Task;
import com.example.TaskManager.Models.User;

import java.io.File;

@Database(entities = {Task.class , User.class}, version = 1, exportSchema = true)
@TypeConverters({DBConverters.DateConverter.class})
public abstract class AppRepository extends RoomDatabase {

    public static final String DATABASE_NAME = "TaskDatabase_db";
    private static Context mContextField;

    public abstract iTaskDatabaseDao mITaskDatabaseDao();

    public static AppRepository sAppRepository;

    public static AppRepository getInstance(Context context){
        Context mContext = context.getApplicationContext();
         mContextField = mContext;
        if(sAppRepository == null){
            sAppRepository = Room.databaseBuilder(mContext,
                    AppRepository.class,
                    DATABASE_NAME).allowMainThreadQueries().build();
            return sAppRepository;
        }
        return sAppRepository;
    }

    public File getPhotoFile (String fileName){
        File photoFile = new File(mContextField.getFilesDir(), fileName);
        return photoFile;
    }
//    HashMap<String , ArrayList<ArrayList<Task>>> mTaskOfUser = new HashMap<>();
//    ArrayList<ArrayList<Task>> mArrayListsOfLists = new ArrayList<>();
//
//    private TaskRepository() {
//        initAdmin();
//    }
//
//    private void initAdmin() {
//        mTaskOfUser.put(ADMIN, new ArrayList<ArrayList<Task>>());
//        ArrayList<Task> ToDOTasksList = new ArrayList<>();
//        ArrayList<Task> DoingTasksList = new ArrayList<>();
//        ArrayList<Task> DoneTasksList = new ArrayList<>();
//        mTaskOfUser.get(ADMIN).add(ToDOTasksList);
//        mTaskOfUser.get(ADMIN).add(DoingTasksList);
//        mTaskOfUser.get(ADMIN).add(DoneTasksList);
//    }
//
//    public ArrayList<ArrayList<Task>> getArrayListsOfLists(String username) {
//        mArrayListsOfLists = mTaskOfUser.get(username);
//        return mArrayListsOfLists;
//    }
//
//    public void insertTodoTaskList(Task task, String username){
//        ArrayList<Task> mTodoTaskList = mTaskOfUser.get(username).get(0);
//        mTodoTaskList.add(task);
//        if(!username.equals(ADMIN)) {
//            ArrayList<Task> adminTodoTaskList = mTaskOfUser.get(ADMIN).get(0);
//            adminTodoTaskList.add(task);
//        }
//    }
//
//    public void insertDoingTaskList(Task task, String username){
//        ArrayList<Task> mDoingTaskList = mTaskOfUser.get(username).get(1);
//        mDoingTaskList.add(task);
//        if(!username.equals(ADMIN)) {
//            ArrayList<Task> adminDoingTaskList = mTaskOfUser.get(ADMIN).get(1);
//            adminDoingTaskList.add(task);
//        }
//    }
//
//    public void insertDoneTaskList(Task task, String username){
//        ArrayList<Task> mDoneTaskList = mTaskOfUser.get(username).get(2);
//        mDoneTaskList.add(task);
//        if(!username.equals(ADMIN)) {
//            ArrayList<Task> adminDoneTaskList = mTaskOfUser.get(ADMIN).get(2);
//            adminDoneTaskList.add(task);
//        }
//    }
//
//    public void setTask(Task inputTask){
//        for (int i=0; i<3; i++){
//            for (Task task: mArrayListsOfLists.get(i)) {
//                if(task.getUUID().equals(inputTask.getUUID())){
//                    task.setName(inputTask.getName());
//                    task.setState(inputTask.getState());
//                    task.setDate(inputTask.getDate());
//                    return;
//                }
//            }
//        }
//    }
//
//    public void deleteTask(Task inputTask){
//        for (int i=0; i<3; i++){
//            for (Task task: mArrayListsOfLists.get(i)) {
//                if(task.getUUID().equals(inputTask.getUUID())){
//                    mArrayListsOfLists.get(i).remove(task);
//                    return;
//                }
//            }
//        }
//    }
//
//
//    public void addArrayListOfUser(String username){
//        if(mTaskOfUser.containsKey(username)){
//            return;
//        } else {
//            ArrayList<ArrayList<Task>> arrayLists = new ArrayList<>();
//            ArrayList<Task> ToDOTasksList = new ArrayList<>();
//            ArrayList<Task> DoingTasksList = new ArrayList<>();
//            ArrayList<Task> DoneTasksList = new ArrayList<>();
//            arrayLists.add(ToDOTasksList);
//            arrayLists.add(DoingTasksList);
//            arrayLists.add(DoneTasksList);
//            mTaskOfUser.put(username, arrayLists);
//        }
//
//    }
}
