package com.example.calendarattempt4;

import java.sql.*;

public class parent {
    // Initialises objects
    private int parent_ID;        //Stores PARENT's ID, unique, used for database searching
    private String username;      //Stores PARENT's username, unique, used for login
    private String password;      //Stores PARENT's password, used for login
    private String email;         //Stores PARENT's email, unique, used for login and account retrieval
    private int Child_num;        //Stores ID of CHILD currently being viewed or logged
    private int CID_1;            //Stores ID of 1st CHILD, The ID of each CHILD belonging to current PARENT is stored
    private int CID_2;            //Stores ID of 2nd CHILD
    private int CID_3;            //Stores ID of 3rd CHILD
    // Variable to check if data is returned from database
    private String AUTH = null;
    // Statement to connect to Postgresql
    private Statement s;


    // HARD CODED DATA
    // USED FOR DEVELOPMENT
    // REAL DATA WILL BE OBTAINED WITH PARENT LOG IN
    // WILL BE DELETED BEFORE SUBMITTING
    public parent() {
        this.parent_ID = 1;
        this.username = "example_username";
        this.password = "example_password";
        this.email = "example_gmail.com";
        this.Child_num = 3;
        this.CID_1 = 1;
        this.CID_2 = 2;
        this.CID_3 = 3;
    }

    // Passes on Statement s to parent
    public void connect(Statement s) {
        this.s = s;
    }

    // Retrieves info associated with parent login credentials and return state to main class
    public boolean login(String username, String password) throws SQLException {
        String sqlStr = "SELECT PID, email, Child_num, Child_ID_1, Child_ID_2, Child_ID_3  FROM parents WHERE username =\'" + username + "\' and password = \'" + password + "\';";
        ResultSet rset = s.executeQuery(sqlStr);
        AUTH = null;
        while (rset.next()) {
            this.parent_ID = rset.getInt("PID");
            ;
            this.email = rset.getString("email");
            AUTH = rset.getString("PID");
            this.Child_num = rset.getInt("Child_num");
            this.CID_1 = rset.getInt("Child_ID_1");
            this.CID_2 = rset.getInt("Child_ID_2");
            this.CID_3 = rset.getInt("Child_ID_3");
        }

        if (AUTH == null) {
            // Returns status to main class to show user message
            return false;
        } else {
            // Returns status to main class to show user message
            return true;
        }
    }

    // Checks if account already exists and creates new account if not
    public boolean signup(String username, String password, String email) throws SQLException {
        String sqlStr = "SELECT PID FROM parents WHERE username =\'" + username + "\' or email = \'" + email + "\';";
        ResultSet rset = s.executeQuery(sqlStr);
        while (rset.next()) {
            AUTH = rset.getString("PID");
        }
        if (AUTH != null) {
            // Returns status to main class to show user message
            return false;
        } else {
            sqlStr = "insert into parents (username,password,email) values(\'" + username + "\',\'" + password + "\',\'" + email + "\');";
            s.execute(sqlStr);
            // Returns status to main class to show user message
            return true;
        }
    }



////////////////////////////////////////////////////////////////////////////////////////////////
    public void create_child(String username, String password, String email) throws SQLException {


    }


    public int getParent_ID() {
        return parent_ID;
    }

    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }

    public int getChild_num() {
        return Child_num;
    }

    public int getCID_1() {
        return CID_1;
    }

    public int getCID_2() {
        return CID_2;
    }

    public int getCID_3() {
        return CID_3;
    }


}