package com.example.calendarattempt4;

import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;

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
    public void testAnimal() {
        child C = new child();
        String input = "Hello";
        C.setAnimal(input);
        Assert.assertEquals(C.getAnimal(), input);
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
    public void testDates() {
        child C = new child();
        String input = "Hello";
        C.setDates(input);
        Assert.assertEquals(C.getDates(), input);
    }

    /*@Test
    public void testConnect(){
        child C = new child();
        String Jason = "Jason Doe";
        int MrsDoe = 1;
        try {
            Assert.assertEquals(C.create(Jason,MrsDoe), true);
        } catch (SQLException e) {
            //Assert.assertEquals(1,2);
            e.printStackTrace();
        }

    }*/
}
