package ch.cscf.jeci.utils;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by henryp on 01/09/15.
 */
public class TestPartialDateFormatter {

    private Date testDate = new Date(1420110671000l); //01.01.2015 11:11:11

    @Test
    public void testWithDate() throws Exception {
        PartialDateFormatter formatter = new PartialDateFormatter(testDate, 0,0,0);
        assertEquals("01.01.2015", formatter.getFormattedDate());
    }

    @Test
    public void testWithAllFields() throws Exception {
        PartialDateFormatter formatter = new PartialDateFormatter(null, 1, 1,2015);
        assertEquals("01.01.2015", formatter.getFormattedDate());
    }

    @Test
    public void testWithMonthAndYear() throws Exception {
        PartialDateFormatter formatter = new PartialDateFormatter(null, null, 1, 2015);
        assertEquals("01.2015", formatter.getFormattedDate());
    }

    @Test
    public void testWithYearOnly() throws Exception {
        PartialDateFormatter formatter = new PartialDateFormatter(null, null, null, 2015);
        assertEquals("2015", formatter.getFormattedDate());
    }

    @Test
    public void testEmpty() throws Exception {
        PartialDateFormatter formatter = new PartialDateFormatter(null, null, null, null);
        assertEquals("", formatter.getFormattedDate());
    }
}