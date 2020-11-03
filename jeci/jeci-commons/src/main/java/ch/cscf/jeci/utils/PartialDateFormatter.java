package ch.cscf.jeci.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple utility class to format a potentially partial date to string.
 * The date can be a regular java.util.Date, or composed of 3 fields day, month, year.
 * If the date is provided it is used. Otherwise the fields are used. Any field can be absent.
 *
 * This class does not validate the date components. So while a partial date should always have at least the year (otherwise it is useless), this formatter will happily print a date with only a day.
 * This class will also not validate the date fields when they are present. 31.02.2015 will be formatted. As well as 222.-123.1. Any non null integer will be used.
 *
 * Garbage in, garbage out.
 */
public class PartialDateFormatter {

    private Date date;
    private Integer day, month, year;

    private DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

    public PartialDateFormatter(Date date, Integer day, Integer month, Integer year) {
        this.date = date;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getFormattedDate(){
        if(date != null){
            return formatter.format(date);
        }

        StringBuilder builder = new StringBuilder();

        if(day != null){
            builder.append(String.format("%02d", day));
            builder.append(".");
        }

        if(month != null){
            builder.append(String.format("%02d", month));
            builder.append(".");
        }

        if(year != null){
            builder.append(year);
        }

        return builder.toString();
    }
}
