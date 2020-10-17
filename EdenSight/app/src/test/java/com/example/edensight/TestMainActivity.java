package com.example.edensight;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class TestMainActivity {
    private MainActivity activity;

    @Before
    public void createMainActivity(){
        activity = new MainActivity();
        activity.username = "username";
        activity.password = "password";
    }

    @Test
    public void getAlarmStatusAtStart(){
        assertFalse(activity.alarmIsRunning);
    }

    @Test
    public void getUsername(){
        assertEquals("username", activity.username);
    }

    @Test
    public void getPassword(){
        assertEquals("password", activity.password);
    }
}
