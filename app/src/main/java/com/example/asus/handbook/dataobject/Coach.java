package com.example.asus.handbook.dataobject;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Coach extends BmobObject {
    private String coachname;
    private BmobFile coachimage;

    public String getCName(){
        return coachname;
    }

    public void setCName(String coachname){
        this.coachname = coachname;
    }

    public String getImage(){
        return coachimage.getFileUrl();
    }

}
