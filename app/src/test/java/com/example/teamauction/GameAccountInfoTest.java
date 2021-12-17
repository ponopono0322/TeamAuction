package com.example.teamauction;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class GameAccountInfoTest {

    GameAccountInfo gameAccountInfo;

    @Before
    public void setUp(){
        gameAccountInfo = new GameAccountInfo();
    }

    @Test
    @Parameters(method = "parameters")
    public void NameTest(String name) {
        gameAccountInfo.setGameName(name);
        gameAccountInfo.setCharacterName(name);
        gameAccountInfo.setGamePublisherPW(name);
        gameAccountInfo.setGamePublisherID(name);
        gameAccountInfo.setLoginPW(name);
        gameAccountInfo.setLoginID(name);

        assertEquals(name, gameAccountInfo.getGameName());
        assertEquals(name, gameAccountInfo.getCharacterName());
        assertEquals(name, gameAccountInfo.getGamePublisherPW());
        assertEquals(name, gameAccountInfo.getGamePublisherID());
        assertEquals(name, gameAccountInfo.getLoginPW());
        assertEquals(name, gameAccountInfo.getLoginID());

    }

    @Test
    @Parameters(method = "parameters")
    public void ResetTest(String name) {
        gameAccountInfo.setGameName(name);
        gameAccountInfo.setCharacterName(name);
        gameAccountInfo.setGamePublisherPW(name);
        gameAccountInfo.setGamePublisherID(name);
        gameAccountInfo.setLoginPW(name);
        gameAccountInfo.setLoginID(name);

        gameAccountInfo.resetData();

        assertEquals(name, gameAccountInfo.getLoginID());
        assertEquals(name, gameAccountInfo.getLoginPW());
        assertEquals("", gameAccountInfo.getGameName());
        assertEquals("", gameAccountInfo.getCharacterName());
        assertEquals("", gameAccountInfo.getGamePublisherPW());
        assertEquals("", gameAccountInfo.getGamePublisherID());

    }

    private Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {"name1"},
                {"name2"},
                {"name3"},
        });
    }

}