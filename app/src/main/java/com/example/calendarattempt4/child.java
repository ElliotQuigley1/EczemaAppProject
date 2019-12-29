package com.example.calendarattempt4;
import java.sql.*;

public class child {
    // Initialises objects
    protected int child_ID;         //Stores CHILD's ID, unique, used for database search
    protected int parent_ID;        //Stores PARENT's ID which CHILD belongs to
    protected String name;          //Stores CHILD's real name, used for displaying in app
    protected String animal;        //Stores CHILD's favourite animal, used for displaying the CHILD's avatar
    protected int age;              //Stores data for database
    protected int weight;           //Stores data for database
    protected int height;           //Stores data for database
    protected String dates;         //Stores previous dates that have been filled out
    // Variable to check if data is returned from database
    protected String AUTH = null;
    // Statement to connect to Postgresql
    protected Statement s;

    public child(){}

    // Retrieves CHILD's data with pre-selected CHILD ID, and then saves data into variables
    public void connect(Statement s, int parent_ID, int child_num) throws SQLException {
        this.s = s;
        // Gets CHILD ID using selected CHILD from specific PARENT
        String sqlStr = "SELECT Child_ID_"+child_num+" FROM parents WHERE PID =\'"+parent_ID+"\';";
        ResultSet rset=s.executeQuery(sqlStr);
        while(rset.next()) {this.child_ID = rset.getInt("Child_ID_1"); }
        // Gets CHILD's data using CHILD ID
        sqlStr = "SELECT Child_name, animal, age, weight, height, dates_filled  FROM children WHERE CID ="+child_ID+";";
        rset=s.executeQuery(sqlStr);
        // Stores CHILD's data in variables
        while(rset.next()) {
            this.name = rset.getString("Child_name");
            this.animal = rset.getString("animal");
            this.age = rset.getInt("age");
            this.weight = rset.getInt("weight");
            this.height = rset.getInt("height");
            this.dates = rset.getString("dates_filled");
        }
        this.parent_ID = parent_ID;
    }


    // Checks if Child name already exists and creates new child if not
    public boolean create(String new_name, int parent_ID) throws SQLException {
        // Gets CHILD ID using CHILD's name and PARENT ID
        String sqlStr = "SELECT CID FROM children WHERE child_name =\'"+new_name+"\' and PID = \'"+parent_ID+"\';";
        ResultSet rset=s.executeQuery(sqlStr);
        AUTH = null;
        // If no there is no matching CHILD ID, then return false
        while(rset.next()) {
            AUTH = rset.getString("CID");
        }
        if (AUTH != null){
            // Returns status to main class to show user message
            return false;
        }else{
            sqlStr = "insert into children (child_name,PID) values(\'"+new_name+"\',\'"+parent_ID+"\');";
            // Creates child info
            s.execute (sqlStr);
            // Returns status to main class to show user message
            return true;
        }
    }
}
