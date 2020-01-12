package com.example.calendarattempt4;
import java.sql.*;
import java.util.Date;
import java.time.LocalDate;

public class day {
    // Initialises objects
    private int Day_ID;               //Stores DAY ID, unique, used for database search
    private int Parent_ID;            //Stores PARENT ID for each CHILD's entry
    private int Child_ID;             //Stores CHILD ID for each entry
    private String date;              //Stores actual date as a string
    protected String image;              //Stores image as a string
    protected String answers;           //Stores answers to eczema questions as a string
    // Variable to check if data is returned from database
    protected String AUTH = null;
    // Statement to connect to Postgresql
    protected Statement s;



    public day(){}

    public void setChild_ID(int cid){ this.Child_ID = cid ;}
    public void setDate(String date) {this.date = date ;}
    public void setDay_ID(int did) { this.Day_ID = did; }
    public void setParent_ID(int pid){ this.Parent_ID = pid; }
    public String getDate() { return date; }
    public int getChild_ID() { return Child_ID;}
    public int getDay_ID() { return Day_ID; }
    public int getParent_ID() { return Parent_ID ; }

    //Checking if date entry already exists
    // Passes on Statement s to day and retrieve data for selected day
    public boolean check(int CID, int PID, String date, Statement s) throws SQLException {
        AUTH = null;
        image = null;
        this.s = s;
        setParent_ID(PID);
        setChild_ID(CID);
        setDate(date);
        // Retrieves answers using CHILD ID and date, Then resets answers to 0 if NULL
        String sqlStr = "SELECT * FROM dates WHERE CID =\'"+CID+"\' and date =\'"+date+"\';";
        ResultSet rset=s.executeQuery(sqlStr);
        while(rset.next()) {
            setDay_ID(rset.getInt("DID"));
            this.answers = rset.getString("Record");
            this.image = rset.getString("image");
            AUTH = rset.getString("DID");
        }
        if (AUTH != null){
            // Returns status to main class to show user message
            return true;
        } else {
            this.answers = "00000000";
            // Returns status to main class to show user message
            return false;
        }
    }

    // Update existing answers
    public void update(String answers, String image) throws SQLException {
        String sqlStr = "UPDATE dates SET record = \'" + answers + "\' WHERE DID = \'" + getDay_ID() +"\';";
        s.execute (sqlStr);
        sqlStr = "UPDATE dates SET image = \'" + image + "\' WHERE DID = \'" + getDay_ID() +"\';";
        s.execute (sqlStr);
    }

    // Create answers for new date
    public void create(String answers, String image) throws SQLException {
        String sqlStr = "INSERT INTO public.dates(cid, pid, date, record, image) VALUES (\'"+ getChild_ID() +"\',\'"+ getParent_ID() +"\',\'"+ getDate() +"\',\'"+ answers +"\',\'" + image + "\');";
        s.execute (sqlStr);
    }
}
