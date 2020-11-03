package ch.cscf.jeci.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: henryp
 */
public class StringArgParser {

    private static final String DEFAULT_LIST_SEPARATOR=",";

    public static Integer parseInt(String value, String name){
        return parseInt(value, name, false);
    }

    public static Integer parseIntOrNull(String value, String name){
        return parseInt(value, name, true);
    }

    private static Integer parseInt(String value, String name, boolean nullAllowed){

        if(value == null){
            if(nullAllowed){
                return null;
            }else{
                throw new IllegalArgumentException("The argument '"+name+"' was passed a null value, but this argument cannot be null !");
            }
        }

        try{
            return Integer.parseInt(value);
        }catch(NumberFormatException e){
            throw new IllegalArgumentException("The argument '\"+name+\"' expects a String value that can be parsed as an integer, but attempt to parse it resulted in a NumberFormatException !", e);
        }
    }

    public static Boolean parseBoolean(String value, String name){
        return parseBoolean(value, name, false);
    }

    public static Boolean parseBooleanOrNull(String value, String name){
        return parseBoolean(value, name, true);
    }

    private static Boolean parseBoolean(String value, String name, boolean nullAllowed){

        if(value == null){
            if(nullAllowed){
                return null;
            }else{
                throw new IllegalArgumentException("The argument '"+name+"' was passed a null value, but this argument cannot be null !");
            }
        }

        switch(value.toLowerCase()){
            case "true":
            case "yes":
            case "1":
                return true;
            default:
                return false;
        }

    }

    public static List<String> parseStringList(String value, String name, String separator){
        return parseStringList(value, name, separator, false);
    }

    public static List<String> parseStringListOrNull(String value, String name, String separator){
        return parseStringList(value, name, separator, true);
    }

    public static List<String> parseStringList(String value, String name){
        return parseStringList(value, name, null, false);
    }

    public static List<String> parseStringListOrNull(String value, String name){
        return parseStringList(value, name, null, true);
    }

    private static List<String> parseStringList(String value, String name, String separator, boolean nullAllowed){

        if(value == null){
            if(nullAllowed){
                return null;
            }else{
                throw new IllegalArgumentException("The argument '"+name+"' was passed a null value, but this argument cannot be null !");
            }
        }

        if(separator == null){
            separator = DEFAULT_LIST_SEPARATOR;
        }

        String[] tokens = value.split(separator);
        List<String> result = new ArrayList<>(tokens.length);

        for(int i=0; i<tokens.length; i++){
            result.add(tokens[i].trim());
        }

        return result;
    }
}
