package com.example.waqay;

public class FirebaseUserData {
    public String android_id;
    public String parent_android_id="Not Necessary";
    public String mail_id;
    public String isActive="0";
    public FirebaseUserData()
    {

    }
    public FirebaseUserData(String android_id,String mail_id)
    {

        this.android_id=android_id;
        this.mail_id=mail_id;
    }
}

