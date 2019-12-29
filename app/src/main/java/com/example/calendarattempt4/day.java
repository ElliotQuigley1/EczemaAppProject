package com.example.calendarattempt4;
import java.sql.*;
import java.util.Date;
import java.time.LocalDate;

public class day {
    // Initialises objects
    protected int Day_ID;               //Stores DAY ID, unique, used for database search
    protected int parent_ID;            //Stores PARENT ID for each CHILD's entry
    protected int child_ID;             //Stores CHILD ID for each entry
    protected String date;              //Stores actual date as a string
    protected String answers;           //Stores answers to eczema questions as a string
    // Variable to check if data is returned from database
    protected String AUTH = null;
    // Statement to connect to Postgresql
    protected Statement s;

    public day(){}

    //Checking if date entry already exists
    // Passes on Statement s to day and retrieve data for selected day
    public boolean check(int CID, int PID, String date, Statement s) throws SQLException {
        AUTH = null;
        this.s = s;
        this.parent_ID = PID;
        this.child_ID = CID;
        this.date = date;
        // Retrieves answers using CHILD ID and date, Then resets answers to 0 if NULL
        String sqlStr = "SELECT * FROM dates WHERE CID =\'"+CID+"\' and date =\'"+date+"\';";
        ResultSet rset=s.executeQuery(sqlStr);
        while(rset.next()) {
            this.Day_ID = rset.getInt("DID");
            this.answers = rset.getString("Record");
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
    public void update(String answers) throws SQLException {
        String sqlStr = "UPDATE dates SET record = \'" + answers + "\' WHERE DID = \'" + this.Day_ID +"\';";
        s.execute (sqlStr);
    }

    // Create answers for new date
    public void create(String answers) throws SQLException {
        String sqlStr = "INSERT INTO public.dates(cid, pid, date, record) VALUES (\'"+ this.child_ID +"\',\'"+ this.parent_ID +"\',\'"+ this.date +"\',\'"+ answers +"\');";
        s.execute (sqlStr);
    }
}
