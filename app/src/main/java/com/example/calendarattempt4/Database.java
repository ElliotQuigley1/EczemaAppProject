package com.example.calendarattempt4;

import android.os.StrictMode;
import android.util.Log;

import java.sql.*;

public class Database {
    // Initialises variables to connect to database
    protected static String dbUrl = "jdbc:postgresql://ec2-46-137-120-243.eu-west-1.compute.amazonaws.com:5432/daku93qk12ot3o?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory&user=ejzndyzesfoyqk&password=e16216b71f70ac9b098db783817afd90c45b71a0b4c117590985968f9ea31bb8";
    protected Connection conn;
    protected Statement s;

    // Return Statement s to main class
    public Statement getConnection() {
        return this.s;
    }

    // Initiates connection to database and return status
    public boolean connect()  {
        try {
            // Check if device SDK is supported
            if (android.os.Build.VERSION.SDK_INT > 9)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            // Registers the driver
            Class.forName("org.postgresql.Driver");
            conn= DriverManager.getConnection(dbUrl);
            this.s=conn.createStatement();
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            String stack = Log.getStackTraceString(e);
            Log.d("DEBUGGG",stack);
            System.out.println("Could not find the database driver ");
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            String stack = Log.getStackTraceString(e);
            Log.d("DEBUGGG",stack);
            System.out.println("Could not connect to the database");
            return false;
        }
    }
}