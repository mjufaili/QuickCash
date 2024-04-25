package com.example.g15_gp;

import static org.junit.Assert.assertEquals;

import com.example.g15_gp.user.User;

import org.junit.Test;

public class UserTest {

    @Test
    public void validUserTypeTest() {
        User user = new User("Manar", "blahblah@dal.ca", "9025255322", "Halifax", "employer");

        user.setUserType("employee");
        assertEquals("employee", user.getUserType());
    }

    @Test
    public void validUserLocationTest() {
        User user = new User("Manar", "blahblah@dal.ca",
                "9025255322", "Halifax", "employer");

    }

    @Test
    public void validUserNameTest() {
        User user = new User("Manar", "blahblah@dal.ca",
                "9025255322", "NS", "employer");

        user.setFullName("Ali");
        assertEquals("Ali", user.getFullName());
    }

    @Test
    public void validUserEmailTest() {
        User user = new User("Manar", "blahblah@dal.ca",
                "9025255322", "NB",  "employer");

        user.setEmail("myLife@dal.ca");
        assertEquals("myLife@dal.ca", user.getEmail());
    }

    @Test
    public void validUserPhoneTest() {
        User user = new User("Manar", "blahblah@dal.ca",
                "9025255322", "Toronto", "employer");

        user.setPhone("9202225242");
        assertEquals("9202225242", user.getPhone());
    }
}

