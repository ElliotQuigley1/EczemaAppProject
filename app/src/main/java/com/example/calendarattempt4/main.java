//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
package com.example.calendarattempt4;

import java.io.*;
import java.time.LocalDate;
import java.sql.*;
import java.util.Base64;


import static java.time.LocalDate.now;


public class main {
    static Statement s = null;
    static parent P = new parent();
    static child C = new child();
    static day D = new day();
    static LocalDate nowday = now();


    public static void main(String[] args) throws SQLException, IOException {
        Database db = new Database();
        if (db.connect()) {
            s = db.getConnection();
            P.connect(s);
            selectChild_button(1);
            checkDay_on_state_change(nowday);
            //System.out.println("DB Statement:\t" + s);
        } else {
            System.out.println("CANNOT CONNECT DB");
        }


/*
        // test upload image
        InputStream inputStream = new FileInputStream("src/main/resources/hhhhhhhhh.jpg"); //You can get an inputStream using any IO API
        byte[] bytes;
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = output.toByteArray();
        //String encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);
        String encodedString = Base64.getEncoder().encodeToString(bytes);

        String sqlStr = "insert into patients (familyname,givenname,phonenumber) values(\'"+encodedString+"\',1,1);";
        s.execute (sqlStr);


        // retrieve image
        String datastring = null;
        sqlStr = "SELECT familyname FROM patients WHERE phonenumber='1';";
        ResultSet rset = s.executeQuery(sqlStr);
        while (rset.next()) {
            datastring = rset.getString("familyname");
        }
        rset.close();


        try {
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(datastring);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
            // write the image to a file
            File outputfile = new File("src/main/resources/myImage.png");
            ImageIO.write(image, "png", outputfile);

        }catch(Exception e) {
            System.out.println(e.getStackTrace());
        }





        // test log in
        LogIn_button("D_username","D_password");


        //onStop
        s.close();*/
    }






    private static void signup_button(String userName_from_app, String password_from_app, String email_from_app) throws SQLException {
        if (P.signup(userName_from_app, password_from_app, email_from_app) == false){
            System.out.println("User already exists" + P.email);
        } else {
            System.out.println("Account created");
            // prompts makeChild_button activity
        }
    }


    private static void LogIn_button(String userName_from_app, String password_from_app) throws SQLException {
        if (P.login(userName_from_app, password_from_app) == true){
            System.out.println("LOGGED IN\nUser email:\t" + P.email);
        } else {
            System.out.println("WRONG LOG IN");
        }
    }


    public static void makeChild_button(String name_from_app) throws SQLException {
        if (C.create(name_from_app, P.parent_ID) == false){
            System.out.println("Child already exists:" + name_from_app);
        } else {
            System.out.println("Child created");
            // ASKS FOR CHILDREN INFO
        }
    }


    public static void selectChild_button(int child_num_from_app) throws SQLException {
        child_num_from_app = 1;
        C.connect(s, P.parent_ID, child_num_from_app);
    }


    public static void checkDay_on_state_change(LocalDate Day_ID) throws SQLException {
        if (D.check(C.child_ID, P.parent_ID, Day_ID.toString(), s)){
            System.out.println("Answers for selected date:\t" + D.answers);
            // displays at editText on Android Studio
        } else {
            System.out.println("This day is empty");
        }
    }


    public static void makeDay(String newAnswers) throws SQLException {
        D.create(newAnswers);
    }


    public static void rewriteDay_button(String newAnswers) throws SQLException {
        D.update(newAnswers);
    }















// back up codes for reconstructing databases

    public void reconstruct_P () throws SQLException {
        try {
            String sqlStr = "create table parents (\n" +
                    "                          PID SERIAL PRIMARY KEY,\n" +
                    "                          username varchar(32) NOT NULL,\n" +
                    "                          password varchar(32) NOT NULL,\n" +
                    "                          email varchar(32) NOT NULL,\n" +
                    "                          Child_num varchar(32),\n" +
                    "                          Child_ID_1 varchar(32),\n" +
                    "                          Child_ID_2 varchar(32),\n" +
                    "                          Child_ID_3 varchar(32)\n" +
                    ");\n" +
                    "insert into parents (username,password,email,Child_num,Child_ID_1,Child_ID_2,Child_ID_3) values('D_username','D_password','D_email','3','1','2','3');";

            s.execute (sqlStr);
            sqlStr = "SELECT * FROM parents WHERE PID=1;";
            ResultSet rset = s.executeQuery(sqlStr);
            while (rset.next()) {
                System.out.println(rset.getInt("PID") + " " + rset.getString("username"));
            }
            rset.close();
        } catch (Exception e) {
            //String stackTrace = Log.getStackTraceString(e);
            //System.out.println(stackTrace);
        }
    }
    public static void reconstruct_C() throws SQLException {
        try {
            String sqlStr = "create table children (\n" +
                    "                          CID SERIAL PRIMARY KEY,\n" +
                    "                          Child_name varchar(32) NOT NULL,\n" +
                    "                          PID varchar(32) NOT NULL,\n" +
                    "                          Animal varchar(32),\n" +
                    "                          Age varchar(32),\n" +
                    "                          Weight varchar(32),\n" +
                    "                          Height varchar(32),\n" +
                    "                          Dates_filled varchar(32)\n" +
                    ");\n" +
                    "insert into children (Child_name,PID,Animal,Age,Weight,Height,Dates_filled) values('Fluffy','1','Puppy','13','40','143','1');";

            s.execute (sqlStr);
            sqlStr = "SELECT * FROM children WHERE CID=1;";
            ResultSet rset = s.executeQuery(sqlStr);
            while (rset.next()) {
                System.out.println(rset.getInt("CID") + " " + rset.getString("animal"));
            }
            rset.close();
        } catch (Exception e) {
            //String stackTrace = Log.getStackTraceString(e);
            //System.out.println(stackTrace);
        }
    }
    public static void reconstruct_D() throws SQLException {
        try {
            String sqlStr = "create table dates (\n" +
                    "                          DID SERIAL PRIMARY KEY,\n" +
                    "                          CID varchar(32) NOT NULL,\n" +
                    "                          PID varchar(32) NOT NULL,\n" +
                    "                          date varchar(32) NOT NULL,\n" +
                    "                          Record varchar(32) NOT NULL\n" +
                    ");\n" +
                    "insert into dates (CID,PID,date,Record) values('1','1','2019-12-10','1101110110');";

            s.execute (sqlStr);
            sqlStr = "SELECT * FROM dates WHERE DID=1;";
            ResultSet rset = s.executeQuery(sqlStr);
            while (rset.next()) {
                System.out.println(rset.getInt("DID") + " " + rset.getString("Record"));
            }
            rset.close();
        } catch (Exception e) {
            System.out.println(e);

            //String stackTrace = Log.getStackTraceString(e);
            //System.out.println(stackTrace);
        }
    }
}

