package com.example.calendarattempt4;

import java.sql.*;

public class parent {
    // Initialises objects
    protected int parent_ID;        //Stores PARENT's ID, unique, used for database searching
    protected String username;      //Stores PARENT's username, unique, used for login
    protected String password;      //Stores PARENT's password, used for login
    protected String email;         //Stores PARENT's email, unique, used for login and account retrieval
    protected int Child_num;        //Stores ID of CHILD currently being viewed or logged
    protected int CID_1;            //Stores ID of 1st CHILD, The ID of each CHILD belonging to current PARENT is stored
    protected int CID_2;            //Stores ID of 2nd CHILD
    protected int CID_3;            //Stores ID of 3rd CHILD
    // Variable to check if data is returned from database
    protected String AUTH = null;
    // Statement to connect to Postgresql
    protected Statement s;


    // HARD CODED DATA
    // USED FOR DEVELOPMENT
    // REAL DATA WILL BE OBTAINED WITH PARENT LOG IN
    // WILL BE DELETED BEFORE SUBMITTING
    public parent(){
        this.parent_ID = 1;
        this.username = "D_username";
        this.password = "D_password";
        this.email = "D_email";
        this.Child_num = 3;
        this.CID_1 = 1;
        this.CID_2 = 2;
        this.CID_3 = 3;
    }

    // Passes on Statement s to parent
    public void connect(Statement s){
        this.s = s;
    }

    // Retrieves info associated with parent login credentials and return state to main class
    public boolean login(String username, String password) throws SQLException {
        String sqlStr = "SELECT PID, email, Child_num, Child_ID_1, Child_ID_2, Child_ID_3  FROM parents WHERE username =\'"+username+"\' and password = \'"+password+"\';";
        ResultSet rset=s.executeQuery(sqlStr);
        AUTH = null;
        while(rset.next()) {
            this.parent_ID = rset.getInt("PID");;
            this.email = rset.getString("email");
            AUTH = rset.getString("PID");
            this.Child_num = rset.getInt("Child_num");
            this.CID_1 = rset.getInt("Child_ID_1");
            this.CID_2 = rset.getInt("Child_ID_2");
            this.CID_3 = rset.getInt("Child_ID_3");
        }

        if (AUTH == null){
            // Returns status to main class to show user message
            return false;
        }else{
            // Returns status to main class to show user message
            return true;
        }
    }

    // Checks if account already exists and creates new account if not
    public boolean signup(String username, String password, String email) throws SQLException {
        String sqlStr = "SELECT PID FROM parents WHERE username =\'"+username+"\' or email = \'"+email+"\';";
        ResultSet rset=s.executeQuery(sqlStr);
        while(rset.next()) {
            AUTH = rset.getString("PID");
        }
        if (AUTH != null){
            // Returns status to main class to show user message
            return false;
        }else{
            sqlStr = "insert into parents (username,password,email) values(\'"+username+"\',\'"+password+"\',\'"+email+"\');";
            s.execute (sqlStr);
            // Returns status to main class to show user message
            return true;
        }
    }

}