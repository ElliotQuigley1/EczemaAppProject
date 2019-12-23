package com.example.calendarattempt4;
import java.sql.*;

public class child {
    // Initialises objects
    protected int child_ID;
    protected int parent_ID;
    protected String name;
    protected String animal;
    protected int age;
    protected int weight;
    protected int height;
    protected String dates;
    // Variable to check if data is returned from database
    protected String AUTH = null;
    // Statement to connect to Postgresql
    protected Statement s;
    public child(){
    }

    // Retrieves child data with pre-selected CID and saves into variables
    public void connect(Statement s, int parent_ID, int child_num) throws SQLException {
        this.s = s;
        // Gets CID from slected child from specific parent
        String sqlStr = "SELECT Child_ID_"+child_num+" FROM parents WHERE PID =\'"+parent_ID+"\';";
        ResultSet rset=s.executeQuery(sqlStr);
        while(rset.next()) {
            this.child_ID = rset.getInt("Child_ID_1");
        }
        sqlStr = "SELECT Child_name, animal, age, weight, height, dates_filled  FROM children WHERE CID ="+child_ID+";";
        rset=s.executeQuery(sqlStr);
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
        String sqlStr = "SELECT CID FROM children WHERE child_name =\'"+new_name+"\' and PID = \'"+parent_ID+"\';";
        ResultSet rset=s.executeQuery(sqlStr);
        AUTH = null;
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
