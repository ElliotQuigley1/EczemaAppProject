package com.example.calendarattempt4;

import java.sql.*;

public class parent {
    protected int parent_ID;
    protected String username;
    protected String password;
    protected String email;
    protected int Child_num;
    protected int CID_1;
    protected int CID_2;
    protected int CID_3;
    String AUTH = null;
    Statement s;

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

    public void connect(Statement s){
        this.s = s;
    }

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
            return false;
        }else{
            return true;
        }
    }

    public boolean signup(String username, String password, String email) throws SQLException {
        String sqlStr = "SELECT PID FROM parents WHERE username =\'"+username+"\' or email = \'"+email+"\';";
        ResultSet rset=s.executeQuery(sqlStr);
        while(rset.next()) {
            AUTH = rset.getString("PID");
        }
        if (AUTH != null){
            return false;
        }else{
            sqlStr = "insert into parents (username,password,email) values(\'"+username+"\',\'"+password+"\',\'"+email+"\');";
            s.execute (sqlStr);
            return true;
        }
    }

}