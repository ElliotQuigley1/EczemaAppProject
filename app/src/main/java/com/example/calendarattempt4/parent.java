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
        setParent_ID(1);
        setUsername("example_username");
        setPassword("example_password");
        setEmail("example_gmail.com");
        setChild_num(Child_num = 3);
        setCID_1(1);
        setCID_2(2);
        setCID_3(6);
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
            setParent_ID(rset.getInt("PID"));
            ;
            setEmail(rset.getString("email"));
            AUTH = rset.getString("PID");
            setChild_num(rset.getInt("Child_num"));
            setCID_1(rset.getInt("Child_ID_1"));
            setCID_2(rset.getInt("Child_ID_2"));
            setCID_3(rset.getInt("Child_ID_3"));
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
    public void setParent_ID(int parent_ID) { this.parent_ID = parent_ID; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email) { this.email = email; }
    public void setChild_num(int child_num) { Child_num = child_num; }
    public void setCID_1(int CID_1) { this.CID_1 = CID_1; }
    public void setCID_2(int CID_2) { this.CID_2 = CID_2; }
    public void setCID_3(int CID_3) { this.CID_3 = CID_3; }

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