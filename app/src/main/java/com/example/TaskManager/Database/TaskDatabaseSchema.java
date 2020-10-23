package com.example.TaskManager.Database;

public class TaskDatabaseSchema {
    public static String NAME = "task manager database.db";
    public static int VERSION = 1;

    public static class TaskTable {
        public static final String NAME = "task table";

        public static class Columns {
            public static String ID = "id";
            public static String TITLE = "title";
            public static String STATE = "state";
            public static String DESCRIPTION = "description";
            public static String DATE = "date";
        }
    }

    public static class UserTable{
        public static final String NAME = "user table";

        public static class Columns {
            public static String ID = "id";
            public static String USERNAME = "username";
            public static String PASSWORD = "password";
        }
    }
}
