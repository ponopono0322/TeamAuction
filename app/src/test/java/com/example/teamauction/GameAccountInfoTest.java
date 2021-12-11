package com.example.teamauction;

import org.junit.Test;
import static org.junit.Assert.*;

public class GameAccountInfoTest {

    @Test
    public void nametest() {
        GameAccountInfo gameAccountInfo = new GameAccountInfo();
        gameAccountInfo.setGameName("name1");
        assertEquals("name1", gameAccountInfo.getGameName());
        assertEquals("name2", gameAccountInfo.getGameName());
        assertEquals(123, gameAccountInfo.getGameName());
    }

}