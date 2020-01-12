package com.example.calendarattempt4;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class parentTest {

    @Test
    public void testChild_ID() {
        int Child_ID1=1, Child_ID2=2, Child_ID3=3, Child_ID=4;
        parent P = new parent();
        P.setChild_num(Child_ID);
        P.setCID_1(Child_ID1);
        P.setCID_2(Child_ID2);
        P.setCID_3(Child_ID3);

        Assert.assertEquals(P.getChild_num(), Child_ID);
        Assert.assertEquals(P.getCID_1(), Child_ID1);
        Assert.assertEquals(P.getCID_2(), Child_ID2);
        Assert.assertEquals(P.getCID_3(), Child_ID3);
    }

    @Test
    public void testParent_ID(){
        parent P = new parent();
        int input = 5;
        P.setParent_ID(input);
        Assert.assertEquals(P.getParent_ID(), input);
    }

    @Test
    public void testUsername() {
        parent P = new parent();
        String input = "Hello";
        P.setUsername(input);
        Assert.assertEquals(P.getUsername(),input);
    }

    @Test
    public void testPassword() {
        parent P = new parent();
        String input = "Hello";
        P.setPassword(input);
        Assert.assertEquals(P.getPassword(),input);
    }

    @Test
    public void testEmail() {
        parent P = new parent();
        String input = "Hello";
        P.setEmail(input);
        Assert.assertEquals(P.getEmail(),input);
    }


}