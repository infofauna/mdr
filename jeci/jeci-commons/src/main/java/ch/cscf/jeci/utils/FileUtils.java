package ch.cscf.jeci.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: henryp
 */
public class FileUtils {

    /**
     * This methods checks and returns true if the given File object exists, is a directory, and a file can be created inside it.
     * @param dir
     * @return
     */
    public static boolean canCreateFileInDirectory(File dir){
        if(!dir.exists() && dir.isDirectory()){
            return false;
        }
        try{
            File sample = new File(dir,"test.txt");
            sample.createNewFile();
            sample.delete();
        }catch(IOException e){
            return false;
        }
        return true;
    }

    public static String getDateStamp(){
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }
}
