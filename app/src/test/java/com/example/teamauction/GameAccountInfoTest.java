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
        assertEquals(name, gameAccountInfo.getGameName());
    }

    private Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {"name1"},
                {"name2"},
                {"name3"},
        });
    }

}