package com.example.asus.handbook.dataobject;

import java.io.File;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class Course extends BmobObject {
    private String coursename;
    private String coursetype;
    private BmobFile courseimage;
    private String coachname;

    public String getName() {
        return coursename;
    }

    public void setName(String coursename) {
        this.coursename = coursename;
    }
    public String getType() {
        return coursetype;
    }
    public void setType(String coursetype) {
        this.coursetype = coursetype;
    }

    public String getImage(){
        return courseimage.getFileUrl();
    }

    public String getCName(){
        return coachname;
    }

    public void setCName(String coachname){
        this.coachname = coachname;
    }


}
