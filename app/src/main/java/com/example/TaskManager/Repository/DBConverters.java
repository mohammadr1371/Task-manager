package com.example.TaskManager.Repository;

import androidx.room.TypeConverter;

import java.util.Date;

public class DBConverters {
    public static class DateConverter{

        @TypeConverter
        public Long dateToLong (Date date){
            return date.getTime();
        }

        @TypeConverter
        public Date longToDate (Long value){
            return new Date(value);
        }
    }
}
