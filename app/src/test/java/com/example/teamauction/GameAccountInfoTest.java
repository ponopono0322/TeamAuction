package com.example.teamauction;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import junit.framework.TestCase;

public class GameAccountInfoTest {

    GameAccountInfo gameAccountInfo;

    @Before
    public void setUp(){
        gameAccountInfo = new GameAccountInfo();
    }

    @Test
    public void nametest() {
        gameAccountInfo.setGameName("name1");
        assertEquals("name1", gameAccountInfo.getGameName());
    }

}