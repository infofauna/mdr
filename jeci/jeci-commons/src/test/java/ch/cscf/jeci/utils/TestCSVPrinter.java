package ch.cscf.jeci.utils;

import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by henryp on 26/06/15.
 */
public class TestCSVPrinter extends TestCase {

    private List<Map<String,Object>> buildTestData(){

        Map<String, Object> p1 = new HashMap<>();
        p1.put("name", "John");
        p1.put("country", "Switzerland");
        p1.put("age", 33);
        p1.put("date", new Date());

        Map<String, Object> p2 = new HashMap<>();
        p2.put("name", "Jane");
        p2.put("country", "France");
        p2.put("age", 22);
        p2.put("date", new Date());

        Map<String, Object> p3 = new HashMap<>();
        p3.put("name", "Patrick");
        p3.put("country", "Ireland");
        p3.put("age", 44);
        p3.put("date", new Date());

        return Lists.newArrayList(p1,p2,p3);

    }

    @Test
    public void testSimple() throws Exception {

        StringWriter sw = new StringWriter();

        CSVWriter printer = new CSVWriter(';', Lists.newArrayList("name","age"), null, buildTestData(), sw);
        printer.writeToOutput();

        String result = sw.toString();
        System.out.println(result);

    }

    @Test
    public void testWithHeaderMapping() throws Exception {

        StringWriter sw = new StringWriter();

        Map<String,String> headerMapping = new HashMap<>();
        headerMapping.put("name", "Nom");
        headerMapping.put("age", "Age");

        CSVWriter printer = new CSVWriter(';', Lists.newArrayList("name","age"), headerMapping, buildTestData(), sw);
        printer.writeToOutput();

        String result = sw.toString();
        System.out.println(result);

    }

    @Test
    public void testWithDate() throws Exception {

        StringWriter sw = new StringWriter();

        Map<String,String> headerMapping = new HashMap<>();
        headerMapping.put("name", "Nom");
        headerMapping.put("age", "Age");
        headerMapping.put("date", "Date de création");


        CSVWriter printer = new CSVWriter(';', Lists.newArrayList("name","age","date"), headerMapping, buildTestData(), sw);
        printer.writeToOutput();

        String result = sw.toString();
        System.out.println(result);

    }

    @Test
    public void testWithCustomDateFormatter() throws Exception {

        StringWriter sw = new StringWriter();

        Map<String,String> headerMapping = new HashMap<>();
        headerMapping.put("name", "Nom");
        headerMapping.put("age", "Age");
        headerMapping.put("date", "Date de création");


        CSVWriter printer = new CSVWriter(';', Lists.newArrayList("name","age","date"), headerMapping, buildTestData(), sw);
        printer.setFormatter(Date.class, date -> new SimpleDateFormat("dd.mm.yyyy").format(date));

        printer.writeToOutput();

        String result = sw.toString();
        System.out.println(result);

    }

}