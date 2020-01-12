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
        int output = 0;
        C.setChild_ID(input);
        output = C.getChild_ID();
        Assert.assertEquals(input, output);
    }

    @Test
    public void testParent_ID() {
        child C = new child();
        int input = 5;
        int output = 0;
        C.setParent_ID(input);
        output = C.getParent_ID();
        Assert.assertEquals(input, output);
    }

    @Test
    public void testName() {
        child C = new child();
        String input = "Hello";
        String output = "World";
        C.setName(input);
        output = C.getName();
        Assert.assertEquals(input, output);
    }

    @Test
    public void testAnimal() {
        child C = new child();
        String input = "Hello";
        String output = "World";
        C.setAnimal(input);
        output = C.getAnimal();
        Assert.assertEquals(input, output);
    }

    @Test
    public void testAge() {
        child C = new child();
        int input = 5;
        int output = 0;
        C.setAge(input);
        output = C.getAge();
        Assert.assertEquals(input, output);
    }

    @Test
    public void testWeight() {
        child C = new child();
        int input = 5;
        int output = 0;
        C.setWeight(input);
        output = C.getWeight();
        Assert.assertEquals(input, output);
    }

    @Test
    public void testHeight() {
        child C = new child();
        int input = 5;
        int output = 0;
        C.setHeight(input);
        output = C.getHeight();
        Assert.assertEquals(input, output);
    }

    @Test
    public void testDates() {
        child C = new child();
        String input = "Hello";
        String output = "World";
        C.setDates(input);
        output = C.getDates();
        Assert.assertEquals(input, output);
    }

    @Test
    public void testConnect(){
        child C = new child();
        String Jason = "Jason Doe";
        int MrsDoe = 1;
        try {
            Assert.assertEquals(C.create(Jason,MrsDoe), true);
        } catch (SQLException e) {
            Assert.assertEquals(1,2);
            e.printStackTrace();
        }

    }
}