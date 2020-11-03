package ch.cscf.jeci.utils;

/**
 * @author: henryp
 */
public class StringUtil {

    public static String formatNames(String firstName, String lastName){
        if(firstName != null){
            if(lastName != null){
                return firstName + " " + lastName;
            }else{
                return firstName;
            }
        }else{
            if(lastName != null){
                return lastName;
            }
        }

        return "";
    }
}
