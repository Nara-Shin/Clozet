package org.sgen.club.sgenapplication;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by HOJU on 2016-01-09.
 */
public class LogProcess {

    private static final String TAG="LogProcess";

    public static void  errorLog(Exception e){

        StackTraceElement[] stackTraceElements= e.getStackTrace();
        Log.d(TAG, "---------------------------------------------------");
        for(StackTraceElement ste : stackTraceElements){
            String className = ste.getClassName();
            String methodName = ste.getMethodName();
            int lineNumber = ste.getLineNumber();
            Log.d(TAG,"ClassName : "+className);
            Log.d(TAG,"MethodName : "+methodName+ "-(Line : "+lineNumber+")");


        }
        Log.d(TAG,"Exception Message : "+e.getMessage());
        Log.d(TAG,"---------------------------------------------------\n");
    }

    public static void normalLog(String site, String str){
        Log.d(TAG,"---------------------------------------------------");
        Log.d(TAG,"From : "+ site);
        Log.d(TAG,"Log : "+ str);
        Log.d(TAG,"---------------------------------------------------\n");
    }
    public static void normalLog(Class site, String str){
        Log.d(TAG,"---------------------------------------------------");
        Log.d(TAG,"From : "+ site.getName());
        Log.d(TAG,"Log : "+ str);
        Log.d(TAG,"---------------------------------------------------\n");
    }
    public static void normalLog(Class site,String array_name,ArrayList<String> array_str){
        Log.d(TAG,"---------------------------------------------------");
        Log.d(TAG,"From : "+ site.getName());
        Log.d(TAG,"ArrayName : "+ array_name);
        for(String str :array_str){
            Log.d(TAG,"{ Array Elements : "+ str+ " }");
        }
        Log.d(TAG,"ArraySize : "+ array_str.size());
        Log.d(TAG,"---------------------------------------------------\n");
    }
}

