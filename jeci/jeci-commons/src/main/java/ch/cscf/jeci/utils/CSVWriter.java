package ch.cscf.jeci.utils;

import com.google.common.base.Joiner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by henryp on 26/06/15.
 */
public class CSVWriter {

    private char separator;
    private Collection<String> columns;
    private Map<String, String> headerMapping;
    private List<Map<String, Object>> data;
    private Appendable out;
    private Map<Class, ObjectFormatter> formatters = new HashMap<>();

    public CSVWriter(char separator, Collection<String> columns, Map<String, String> headerMapping, List<Map<String, Object>> data, Appendable out) {
        this.separator = separator;
        this.columns = columns;
        this.headerMapping = headerMapping;
        this.data = data;
        this.out = out;

        formatters.put(Date.class, new DateFormatter());
    }

    public void setFormatter(Class clazz, ObjectFormatter formatter){
        formatters.put(clazz, formatter);
    }

    public void writeToOutput() throws IOException{

        writeExcelSeparatorLine();
        writeHeader();

        for(Map<String,Object> row : data){
            List<Object> values = new ArrayList<Object>(columns.size());
            for(String colName : columns){
                values.add(row.get(colName));
            }
            formatObjects(values);
            writeLine(values);
        }
    }

    private void writeHeader() throws IOException{
        if(headerMapping == null){
            writeLine(columns);
            return;
        }

        List<String> header = new ArrayList<>(columns.size());
        for(String col : columns){
            header.add(headerMapping.get(col));
        }
        writeLine(header);
    }

    private void writeLine(Collection<? extends Object> values) throws IOException {

        Joiner.on(separator).useForNull("").appendTo(out, values);
        out.append('\n');
    }

    private void formatObjects(List<Object> values) {
        //format special values such as dates
        for(int i=0; i<values.size(); i++){
            Object value = values.get(i);
            if(value != null) {
                ObjectFormatter formatter = formatters.get(value.getClass());
                if (formatter != null) {
                    values.set(i, formatter.toString(value));
                }
            }
        }
    }

    private void writeExcelSeparatorLine() throws IOException{
        //this line allows to specify to Excel the separator character used
        out.append("sep=" + separator);
        out.append("\n");
    }

    public interface ObjectFormatter<T> {
        public String toString(T value);
    }

    class DateFormatter implements ObjectFormatter<Date> {
        private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        @Override
        public String toString(Date value) {
            return format.format(value);
        }
    }


}

