package com.example.g15_gp.job;

import java.io.Serializable;

public class Job implements Serializable {
    public String name;
    public String location;
    public String payRate;
    public String jobType;

    public Job(){
    }

    public Job(String name,String location, String payRate, String jobType){
        this.name =name;
        this.location = location;
        this.payRate = payRate;
        this.jobType = jobType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPayRate(String payRate) {
        this.payRate = payRate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getJobType() {
        return jobType;
    }

    public String getPayRate() {
        return payRate;
    }


}