package com.example.calendarattempt4;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Random;

import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.Date;

import static java.time.LocalDate.now;

public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration mAppBarConfiguration;

    private TextView thedate;
    private TextView score;
    private Button btngocalendar;
    public Database db = new Database();
    static Statement s=null;
    static parent P = new parent();
    static child C = new child();
    static day D = new day();
    static Date nowday = Calendar.getInstance().getTime();

    private static final Random RANDOM = new Random();
    protected LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    private double[]  x_array = new double[300];
    private double[]  y_array = new double[300];

    public void main(String[] args) throws SQLException, IOException {

    }

    public Database getDB(){return this.db;}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (db.connect()) {
            s = db.getConnection();
            P.connect(s);
            try {
                selectChild_button(1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String date_chosen = sdf.format(nowday);


                checkDay_on_state_change(date_chosen);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //System.out.println("DB Statement:\t" + s);
        } else {
            System.out.println("CANNOT CONNECT DB");
        }


        s = db.getConnection();


        thedate = (TextView) findViewById(R.id.date);
        btngocalendar = (Button) findViewById(R.id.btngocalendar);


        score = (TextView) findViewById(R.id.Score);
        score.setText("text you want to display");

        Intent incoming = getIntent();
        String date = incoming.getStringExtra("date");
        thedate.setText(date);

        btngocalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        // we get graph view instance

        GraphView graph = (GraphView) findViewById(R.id.graph);
        // data
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        // customize a little bit viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(12);
        viewport.setMinX(00);
        viewport.setMaxX(50);
        viewport.setScrollable(true);
        viewport.setScalable(true);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Weeks");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Severity of Eczema (POEM)");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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

    @Override
    protected void onResume() {
        super.onResume();
        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 50; i++) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry();
                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }

    // add random data to graph
    private void addEntry() {
        // here, we choose to display max 10 points on the viewport and we scroll to end
        //series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), false, 50);
        series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), false, 50);

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
    public static boolean checkDay_on_state_change(String Day_ID) throws SQLException {
        if (D.check(C.child_ID, P.parent_ID, Day_ID, s)){
            System.out.println("Answers for selected date:\t" + D.answers);
            // displays at editText on Android Studio
            return true;
        } else {
            System.out.println("This day is empty");
            return false;
        }
    }
    public static void makeDay(String newAnswers) throws SQLException {
        D.create(newAnswers);
    }
    public static void rewriteDay_button(String newAnswers) throws SQLException {
        D.update(newAnswers);
    }

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

        }catch(Exception e) {
            System.out.println(e.getStackTrace());
        }





        // test log in
        LogIn_button("D_username","D_password");


        //onStop
        s.close();*/

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

