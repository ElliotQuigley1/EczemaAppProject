package com.example.calendarattempt4;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class dayTest {

    @Test
    public void testChild_ID() {
        day D = new day();
        int input = 5;
        D.setChild_ID(input);
        Assert.assertEquals(D.getChild_ID(),input);
    }

    @Test
    public void testDate() {
        day D = new day();
        String input = "Hello";
        D.setDate(input);
        Assert.assertEquals(D.getDate(),input);
    }

    @Test
    public void testDay_ID() {
        day D = new day();
        int input = 5;
        D.setDay_ID(input);
        Assert.assertEquals(D.getDay_ID(),input);
    }

    @Test
    public void testParent_ID() {
        day D = new day();
        int input = 5;
        D.setParent_ID(input);
        Assert.assertEquals(D.getParent_ID(),input);
    }
}