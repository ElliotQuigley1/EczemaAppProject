package com.example.calendarattempt4;

import java.sql.ResultSet;
import java.sql.SQLException;

public class backup_codes {
}


    /*





        // NOTE TO SELF: Create signup activity
    private void signup_button(String userName_from_app, String password_from_app, String email_from_app) throws SQLException {
        if (P.signup(userName_from_app, password_from_app, email_from_app) == false){
            Toast.makeText(this, "User already exists" + P.getEmail() + ", " + P.getUsername(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
            /*
            // Code
            Prompts makeChild_button activity to create child

}
    }
        @RequiresApi(api = Build.VERSION_CODES.O)
    public int getScore()throws SQLException {
        int i=0;

        LocalDate date = java.time.LocalDate.now();
        String sqlStr = "SELECT record WHERE DID = \'"+date+"\';";
        ResultSet rset=s.executeQuery(sqlStr);
        while(rset.next()) {i=rset.getInt("DID");}
        return i;
    }


            // Sets calendar button
        btngocalendar = (Button) findViewById(R.id.btngocalendar);
        btngocalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

     */













    //Image Upload and download
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

            Bitmap decodedByte = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);


        }catch(Exception e) {
            System.out.println(e.getStackTrace());
        }





        // test log in
        LogIn_button("D_username","D_password");


        //onStop
        s.close();

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
                    "                          Age varchar(32),\n" +
                    "                          Weight varchar(32),\n" +
                    "                          Height varchar(32),\n" +
                    ");\n" +
                    "insert into children (Child_name,PID,Age,Weight,Height) values('Fluffy','1','Puppy','13','40','143','1');";

            s.execute (sqlStr);
            sqlStr = "SELECT * FROM children WHERE CID=1;";
            ResultSet rset = s.executeQuery(sqlStr);
            while (rset.next()) {
                System.out.println(rset.getInt("CID"));
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


}*/

