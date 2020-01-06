package com.example.calendarattempt4;

import android.content.Intent;
import android.graphics.Color;
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
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Random;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;
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
import android.widget.Toast;

import java.sql.SQLException;
import java.util.Date;

import static java.time.LocalDate.now;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    // Declares view objects
    private TextView thedate;
    private TextView score;
    private Button btngocalendar;
    // Initialises objects
    int child_selected = 1;
    public Database db = new Database();
    static Statement s = null;
    static parent P = new parent(); // THIS LINE CREATES PARENT OBJECT BASED ON HARDCODED USERNAME PASSWORD AND ID
    // WILL BE MODIFIED TO GENUINE LOG IN, SEE PARENT() INSIDE PARENT.JAVA
    static child C = new child();
    static day D = new day();
    // Gets current date when user opens app
    //static Date nowday = Calendar.getInstance().getTime();
    static Date nowday = new Date();
    static String date = nowday.toString();
    // Prepares values for graph plotting
    private static final Random RANDOM = new Random();
    protected LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    protected DataPoint E[];
    private double[]  x_array = new double[300];
    private double[]  y_array = new double[300];
    // Log-in state
    static boolean logged_in = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///////////////////////////////////////////////////////////////////////////////////////////////
        // NOTE TO SELF: Verify if user has been logged in, prompts log in activity if not
        ///////////////////////////////////////////////////////////////////////////////////////////////

        // Connects Database and displays current date data
        if (db.connect()) {
            s = db.getConnection();
            // Passes on Statement s to parent
            P.connect(s);
            try {
                // Passes on Statement s to child and selects CID depending on selected child
                selectChild_button(child_selected);
                SimpleDateFormat sdf = new SimpleDateFormat("E HH:mm");
                String date_chosen = sdf.format(nowday);
                checkDay_on_state_change(date_chosen); // can remove???????? A[I think this can be removed]
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "CANNOT CONNECT DB", Toast.LENGTH_SHORT).show();
        }


        // Initialises toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialises Navigation drawer
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_C1, R.id.nav_C2, R.id.nav_C3,
                R.id.nav_c_add, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);



        // Initialises graph view
        GraphView graph = (GraphView) findViewById(R.id.graph);

        series = new LineGraphSeries<>();
        graph.addSeries(series);
        // Customize graph viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(12);
        viewport.setMinX(00);
        viewport.setMaxX(50);
        viewport.setScrollable(true);
        viewport.setScalable(true);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Weeks ago");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Severity of Eczema (POEM)");

        // Set navigation drawer menu based on number of children of user
        Menu menu = navigationView.getMenu();
        if (logged_in) {
            switch (P.Child_num) {
                case 2:
                    menu.findItem(R.id.nav_C3).setVisible(false);
                case 1:
                    menu.findItem(R.id.nav_C2).setVisible(false);
                case 0:
                    menu.findItem(R.id.nav_C1).setVisible(false);
                    break;
                case 3:
                    //menu.findItem(R.id.nav_c_add).setVisible(false);
            }
        }

    }



    ///////////////////////////////////////////////////////////////////////////////////////////////
    // NOTE TO SELF: Code here to pass on add child / log-out from navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_C1:
                Toast.makeText(this, "Child 1", Toast.LENGTH_SHORT).show();
                child_selected = 1;
                break;
            case R.id.nav_C2:
                Toast.makeText(this, "Child 2", Toast.LENGTH_SHORT).show();
                child_selected = 2;
                break;
            case R.id.nav_C3:
                Toast.makeText(this, "Child 3", Toast.LENGTH_SHORT).show();
                child_selected = 3;
                break;
            case R.id.nav_c_add:
                Toast.makeText(this, "Add child", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Add_child.class);
                startActivity(intent);
                break;
            case R.id.nav_share:
                Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this, "send", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        try {
            selectChild_button(child_selected);
            Toast.makeText(this, "Child updated", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Runs with child_num_from_app from onNavigationItemSelected() to connect DB with corresponding child_num
    public static void selectChild_button(int child_num_from_app) throws SQLException {
        C.connect(s, P.parent_ID, child_num_from_app);
    }

    // NOTE TO SELF: Create signup activity
    private void signup_button(String userName_from_app, String password_from_app, String email_from_app) throws SQLException {
        if (P.signup(userName_from_app, password_from_app, email_from_app) == false){
            Toast.makeText(this, "User already exists" + P.email + ", " + P.username, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Account created", Toast.LENGTH_SHORT).show();
            /*
            // Code
            Prompts makeChild_button activity to create child
             */
        }
    }

    // NOTE TO SELF: Create login activity
    private void LogIn_button(String userName_from_app, String password_from_app) throws SQLException {
        if (P.login(userName_from_app, password_from_app) == true){
            Toast.makeText(this, "LOGGED IN\nUser email:\t" + P.username, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,"WRONG LOG IN" , Toast.LENGTH_SHORT).show();
        }
    }

    // NOTE TO SELF: have to create cross checking between DB and navcontroller to display the right amount of children
    // NOTE TO SELF: Create make_child activity
    public void makeChild_button(String name_from_app) throws SQLException {
        if (C.create(name_from_app, P.parent_ID) == false){
            Toast.makeText(this, "Child already exists:" + name_from_app, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Child created", Toast.LENGTH_SHORT).show();
            // ASKS FOR CHILDREN INFO
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////



    ///////////////////////////////////////////////////////////////////////////////////////////////
    // NOTE TO SELF: hardcoded texts and graphs, will be modified to show day info and trend graph
    @Override
    protected void onResume() {
        super.onResume();
        score = (TextView) findViewById(R.id.score_display);
        score.setText("Previous POEM Scores");
        score.setTextColor(Color.BLACK);

        thedate = (TextView) findViewById(R.id.date);
        Intent incoming = getIntent();
        if (incoming.getStringExtra("date") != null){
            date = incoming.getStringExtra("date");
        }
        thedate.setText(date);
        SimpleDateFormat sdf = new SimpleDateFormat(" E HH:mm \ndd/MM/yyyy");
        String date_chosen = sdf.format(nowday);
        thedate.setText(date_chosen);
        thedate.setTextColor(Color.BLACK);

        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {

            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < 50; i++) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            series.appendData(new DataPoint(lastX++, RANDOM.nextDouble() * 10d), false, 50);
                        }
                    });
                }
            }
        }).start();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////






    // Passes on Statement s to day and retrieve data for selected day
    public static boolean checkDay_on_state_change(String date) throws SQLException {
        if (D.check(C.child_ID, P.parent_ID, date, s)){
            System.out.println("Answers for selected date:\t" + D.answers);
            // displays at editText on Android Studio
            return true;
        } else {
            System.out.println("This day is empty");
            return false;
        }
    }

    // Create answers for new date
    public static void makeDay(String newAnswers) throws SQLException {
        D.create(newAnswers);
    }

    // Update existing answers
    public static void rewriteDay(String newAnswers) throws SQLException {
        D.update(newAnswers);
    }

    // Sets button action
    public void calendar_button(View view) {
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }





    // Overrides for Navigation drawer
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





    /*
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

