package com.example.calendarattempt4;
import java.sql.*;

public class child extends MainActivity{

    // Initialises objects
    private int child_ID;         //Stores CHILD's ID, unique, used for database search
    private int parent_ID;        //Stores PARENT's ID which CHILD belongs to
    private String name;          //Stores CHILD's real name, used for displaying in app
    private int age;              //Stores data for database
    private int weight;           //Stores data for database
    private int height;           //Stores data for database
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
        while(rset.next()) {
            this.child_ID = rset.getInt("Child_ID_"+child_num);
        }

        // Gets CHILD's data using CHILD ID
        sqlStr = "SELECT * FROM children WHERE CID ="+child_ID+";";
        rset=s.executeQuery(sqlStr);
        // Stores CHILD's data in variables
        while(rset.next()) {
            setName(rset.getString("Child_name"));
            setAge(rset.getInt("age"));
            setWeight(rset.getInt("weight"));
            setHeight(rset.getInt("height"));
        }
        this.parent_ID = parent_ID;
    }


    // Checks if Child name already exists and creates new child if not
    public boolean create(String new_name, int age, int height, int weight) throws SQLException {
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
            sqlStr = "insert into children (child_name,PID,age,weight,height) values(\'"+new_name+"\',\'"+parent_ID+"\',\'"+age+"\',\'"+weight+"\',\'"+height+"\');";
            // Creates child info
            s.execute (sqlStr);
            // Gets newly created child ID
            sqlStr = "SELECT CID FROM children WHERE child_name =\'"+new_name+"\' and PID = \'"+parent_ID+"\';";
            rset=s.executeQuery(sqlStr);
            // If no there is no matching CHILD ID, then return false
            while(rset.next()) {
                setChild_ID(rset.getInt("CID"));
            }

            int updated_child_num = MainActivity.getP().getChild_num() +1;
            sqlStr = "UPDATE parents SET child_num = \'" + updated_child_num + "\' WHERE PID=\'" + getParent_ID() + "\';";
            s.execute (sqlStr);
            sqlStr = "UPDATE parents SET child_id_" + updated_child_num + " = \'" + getChild_ID() + "\' WHERE PID=\'" + getParent_ID() + "\';";
            s.execute (sqlStr);
            // Returns status to main class to show user message
            return true;
        }
    }

    public void delete() {
        try {
            String sqlStr = "DELETE FROM children WHERE CID=\'" + getChild_ID() + "\';";
            s.execute (sqlStr);
            int updated_child_num = MainActivity.getP().getChild_num()-1;
            sqlStr = "UPDATE parents SET child_num = \'" + updated_child_num + "\' WHERE PID=\'" + getParent_ID() + "\';";
            s.execute (sqlStr);
            sqlStr = "DELETE child_id_" + MainActivity.getChild_selected() + " FROM parents WHERE PID=\'" + getParent_ID() + "\';";
            s.execute (sqlStr);

        } catch (Exception e) {
        }
    }


    public void setChild_ID(int child_ID) { this.child_ID = child_ID; }
    public void setParent_ID(int parent_ID) { this.parent_ID = parent_ID; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setWeight(int weight) { this.weight = weight; }
    public void setHeight(int height) { this.height = height; }
    public int getChild_ID() { return child_ID; }
    public int getParent_ID() { return parent_ID; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public int getWeight() { return weight; }
    public int getHeight() { return height; }

}
