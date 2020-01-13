package com.example.calendarattempt4;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class childTest {

    @Test
    public void testChild_ID() {
        child C = new child();
        int input = 5;
        C.setChild_ID(input);
        Assert.assertEquals(C.getChild_ID(), input);
    }

    @Test
    public void testParent_ID() {
        child C = new child();
        int input = 5;
        C.setParent_ID(input);
        Assert.assertEquals(C.getParent_ID(), input);
    }

    @Test
    public void testName() {
        child C = new child();
        String input = "Hello";
        C.setName(input);
        Assert.assertEquals(C.getName(), input);
    }


    @Test
    public void testAge() {
        child C = new child();
        int input = 5;
        C.setAge(input);
        Assert.assertEquals(C.getAge(), input);
    }

    @Test
    public void testWeight() {
        child C = new child();
        int input = 5;
        C.setWeight(input);
        Assert.assertEquals(C.getWeight(), input);
    }

    @Test
    public void testHeight() {
        child C = new child();
        int input = 5;
        C.setHeight(input);
        Assert.assertEquals(C.getHeight(), input);
    }


    @Test
    public void testConnect(){
        child C = new child();
        String Jason = "Jason Doe";
        int MrsDoe = 1, child_num=1;
        Database DB = new Database();
        DB.connect();
        Statement S = DB.getConnection();
        try {
            C.connect(S, MrsDoe, child_num);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(C.getName(),"Jason Doe");
        Assert.assertEquals(C.getAge(), 13);
        Assert.assertEquals(C.getWeight(), 40);
        Assert.assertEquals(C.getHeight(),143);
    }
}
