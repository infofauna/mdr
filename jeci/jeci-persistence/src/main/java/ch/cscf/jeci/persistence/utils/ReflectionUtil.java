package ch.cscf.jeci.persistence.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: henryp
 */
public class ReflectionUtil {

    /**
     * @param clazz
     * @return All declared fields of the given class, including the ones inherited from superclasses.
     */
    public static List<Field> getAllFields(Class clazz){

        List<Field> allFields = new ArrayList<>();

        while(clazz != null){
            allFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return allFields;
    }

    /**
     * @param clazz
     * @param fieldName
     * @return The field with the given class name, if it exists within the given class or any of its superclasses. <code>null</code> if this field does not exist.
     */
    public static Field getDeclaredFieldFromClassAndSuperClasses(Class clazz, String fieldName){

        while(clazz != null){
            try{
                return clazz.getDeclaredField(fieldName);
            }catch(NoSuchFieldException e){}
            clazz = clazz.getSuperclass();
        }
        return null;
    }
}
