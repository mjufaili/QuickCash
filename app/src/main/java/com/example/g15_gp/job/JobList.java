package com.example.g15_gp.job;

public class JobList {
    private String title;
    private String name;

    // this empty constructor is necessary
    public JobList(){
    }

    public JobList(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
