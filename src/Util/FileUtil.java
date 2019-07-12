package Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileUtil {

    public static void setUserAndPass(String User, String Pass){
        PrintWriter outputStream = null;

        try{
            outputStream = new PrintWriter(new FileOutputStream("user_pass.txt"));
            outputStream.println(User + "#" + Pass);
            outputStream.flush();

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static String getUserAndPass(){
        Scanner scan = null;
        try{
            scan = new Scanner(new FileInputStream("src\\user_pass.txt"));
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return scan.nextLine();
    }
}
