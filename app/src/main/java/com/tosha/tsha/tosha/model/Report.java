package com.tosha.tsha.tosha.model;

public class Report {
    private String ReportingUser;
    private String Location;
    private String Date;
    private String Time;
    private String Description;
    private int upvotes;
    private int downvotes;
    private boolean flagged;

    public Report(String location,String Description){
        this.Location = location;
        this.Description = Description;
        upvotes = 0;
        downvotes = 0;
        flagged = false;
    }

    public String getReportingUser() {
        return ReportingUser;
    }

    public void setReportingUser(String reportingUser) {
        ReportingUser = reportingUser;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }
}