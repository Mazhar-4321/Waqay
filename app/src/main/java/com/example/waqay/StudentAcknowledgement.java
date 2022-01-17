package com.example.waqay;

import java.util.HashMap;

public class StudentAcknowledgement {
    private static StudentAcknowledgement studentAcknowledgement=null;
    HashMap<String,String> student_acknowledgement_hashmap= new HashMap<>();
    private StudentAcknowledgement()
    {

    }
    public static StudentAcknowledgement getInstance()
    {
        if(studentAcknowledgement==null)
        {

            return  new StudentAcknowledgement();

        }
        return  studentAcknowledgement;
    }
}
