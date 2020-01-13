/*
Imperial College Biomedical Engineering Programing Group Project
Developed by Alex McKinnon, Elliot Quigley, Julia Gimbernat, Ronald Hsu
With the help of official Android Studio documentation and GraphView support by jjoe64
*/
package com.example.calendarattempt4;

import android.content.Intent;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import android.view.MenuItem;
import android.widget.TextView;
import java.util.Locale;
import java.util.Calendar;
import android.view.View;
import androidx.annotation.NonNull;
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
import android.widget.Toast;
import java.sql.SQLException;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Drawer objects to be called by various functions
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;



    // Sets first child to display info first
    private static int child_selected = 1;
    // Initialises objects
    private static Database db = new Database();
    private static parent P = new parent();
    private static child C = new child();
    private static day D = new day();
    // Getters
    public static Database getDb() {
        return db;
    }
    public static parent getP() {
        return P;
    }
    public static child getC() {
        return C;
    }
    public static day getD() {
        return D;
    }
    public static int getChild_selected() {
        return child_selected;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check device system settings to see if user is logged in
        if (SaveSharedPreference.getUserName(MainActivity.this).length() == 0) {
            // Prompts log in window
            Intent intent = new Intent(MainActivity.this, Log_in.class);
            startActivity(intent);
        } else {
            // Continues app activity
        }

        // Connects Database and displays relevant parent & child info
        if (db.connect()) {
            // Passes on Statement s to parent
            P.setS(db.getConnection());
            // Logs in with saved account credentials to retrieve data
            try {
                P.login(SaveSharedPreference.getUserName(MainActivity.this), SaveSharedPreference.getPassword(MainActivity.this));
                // Displays parent username
                TextView P_username = (TextView) findViewById(R.id.username_view);
                P_username.setText("Username: " + P.getUsername());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                // Selects first child to display relevant info
                display_child_info(child_selected);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "CANNOT CONNECT TO DATABASE", Toast.LENGTH_LONG).show();
        }


        // Initialises toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialises Navigation drawer
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_C2, R.id.nav_C3,
                R.id.nav_c_add, R.id.nav_logoff)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
        // Displays logged in user details within drawer
        View header = navigationView.getHeaderView(0);
        TextView username_display = header.findViewById(R.id.username_display);
        username_display.setText(P.getUsername() + " - Logged in");
        TextView email_display = header.findViewById(R.id.email_display);
        email_display.setText(P.getEmail());

        // Initialises graph view
        GraphView graph = (GraphView) findViewById(R.id.graph);
        // Customize graph viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(0);
        viewport.setMaxY(28);
        viewport.setMinX(-10);
        viewport.setMaxX(0);
        viewport.setScrollable(true);
        viewport.setScalable(true);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Weeks ago");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Severity of Eczema (POEM)");
        // Set static labels for x-axis since weeks are shown backwards
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
    }


    // Drawer to select child / add child / log out
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                child_selected = 1;
                try {
                    display_child_info(child_selected);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.nav_C2:
                child_selected = 2;
                try {
                    display_child_info(child_selected);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.nav_C3:
                child_selected = 3;
                try {
                    display_child_info(child_selected);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.nav_c_add:
                if (P.getChild_num() >= 3) {
                    Toast.makeText(this, "Only 3 children can be added! Delete one before adding!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, Child_info.class);
                    startActivity(intent);
                }
                break;
            case R.id.nav_logoff:
                // Updates device system setting
                SaveSharedPreference.setUserName(MainActivity.this, "0");
                Toast.makeText(this, "Logged off", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Log_in.class);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Connects DB with corresponding child_num to retrieve child info and display it
    public void display_child_info(int child_num_from_app) throws SQLException {
        C.connect(db.getConnection(), P.getParent_ID(), child_num_from_app);
        TextView Value = (TextView) findViewById(R.id.Value);
        Value.setText("Child name:\t\t\t" + "\nAge:\t\t\t" + "\nHeight (cm):\t\t\t" + "\nWeight (kg):\t\t\t");
        TextView Data = (TextView) findViewById(R.id.Data);
        Data.setText(C.getName() + "\n" + C.getAge() + "\n" + C.getHeight() + "\n" + C.getWeight());


        // Gets child past POEM score and refreshes graphview
        new Thread(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                Calendar calendar = Calendar.getInstance();
                // Gets current date when user opens app
                String date_chosen = sdf.format(new Date());
                try {
                    calendar.setTime(sdf.parse(date_chosen));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // Start plotting from 70 days ago
                calendar.add(Calendar.DAY_OF_MONTH, -70);
                // 10 weeks ago on the x axis
                int lastX = -10;
                GraphView graph = (GraphView) findViewById(R.id.graph);
                graph.removeAllSeries();
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                graph.addSeries(series);
                // Loop for x axis
                for (int i = 0; i < 10; i++) {
                    // Start live plotting with current date
                    int runningTot = 0;
                    for (int j = 0; j <= 6; j++) {
                        runningTot = runningTot + get_answers(date_chosen);
                        // Iterate one day
                        calendar.add(Calendar.DAY_OF_MONTH, +1);
                        date_chosen = sdf.format(calendar.getTime());
                    }
                    final int final_value = runningTot;
                    series.appendData(new DataPoint(lastX++, final_value), false, 11);
                }
            }
        }).start();
    }

    // Menu to delete child from parent account
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_child:
                if (P.getChild_num() >= 1) {
                    // Pops up confirmation
                    Intent intent = new Intent(MainActivity.this, Delete_child.class);
                    startActivity(intent);
                } else {
                    // Disabled when no more child
                    Toast.makeText(this, "No child", Toast.LENGTH_SHORT).show();
                }                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    // Dynamically set the drawer icons depending on number of children
    @Override
    protected void onResume() {
        super.onResume();
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        switch (P.getChild_num()) {
            case 0:
                menu.findItem(R.id.nav_home).setVisible(false);
            case 1:
                menu.findItem(R.id.nav_C2).setVisible(false);
            case 2:
                menu.findItem(R.id.nav_C3).setVisible(false);
                break;
            case 3:
        }

    }


    //Function to retrieve data from database and and pass on to be plotted on graph view
    public int get_answers(String date) {
        int total_sum = 0;
        int togglers[] = {0, 0, 0, 0, 0, 0, 0, 0};

        try {
            if (D.check(C.getChild_ID(), P.getParent_ID(), date, db.getConnection())) {
                // Converts 1 and 0 into int variable array
                for (int i = 0; i < MainActivity.D.getAnswers().length(); i++) {
                    if (MainActivity.D.getAnswers().charAt(i) == '1') {
                        togglers[i] = 1;
                    } else if (MainActivity.D.getAnswers().charAt(i) == '0') {
                        togglers[i] = 0;
                    }
                }
            } else { // zeroes array if no answers
                for (int i = 0; i < togglers.length; i++) {
                    togglers[i] = 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Adds bits to obtain score
        for (int i = 0; i < togglers.length; i++) {
            total_sum = total_sum + togglers[i];
        }
        // Formula for calculating POEM score
        return total_sum / 2;
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
}






