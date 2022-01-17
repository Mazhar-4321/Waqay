package com.example.waqay;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Status {
    //private static Object object_status;
    LinkedHashMap<String,Boolean> subjects_selected= new LinkedHashMap<>();
    LinkedHashMap<String,String> subjects_to_semester= new LinkedHashMap<>();
  private static Status object_status=null;
    private Status()
    {

    }
    public static Status getInstance()
    {
        if(object_status==null)
        {
            object_status= new Status();
        }
        return  object_status;
    }
}
