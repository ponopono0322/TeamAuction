package com.example.teamauction;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collection;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ListViewItemTest {

    private ListViewItem listViewItem;

    @Before
    public void SetUp() { listViewItem = new ListViewItem(); }

    @Test
    @Parameters(method = "parameters")
    public void CheckData(String text, String message, boolean bool) {
        listViewItem.setText(text);
        listViewItem.setMassage(message);
        listViewItem.setSelected(bool);

        assertEquals(text, listViewItem.getText());
        assertEquals(message, listViewItem.getMassage());
        assertEquals(bool, listViewItem.getSelected());
    }


    private Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {"text1","message1", false},
                {"text2","message2", true},
                {"text3","message3", false},
        });
    }
}