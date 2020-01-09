package edu.pdx.cstemp.punam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <code>Validate2</code>
 */
public class Validate2 {
    /**
     * <code>validateOption</code>
     * This method checks the option
     * @param args
     *        command line arguments
     * @return boolean
     */
    public static boolean validateOptions(String[] args) {
        boolean validOption = true;
        if(args != null && args.length > 0) {
            for (String arg : args) {
                if (arg.startsWith("-")) {
                    if(!arg.equals("-README") && !arg.equals("-print") && !arg.equals("-textFile")) {
                        validOption = false;
                        System.out.println("Err: Invalid Option type");
                        break;
                    }
                }
            }
        }
        return validOption;
    }
    /**
     * This method validates the arguments passed in the command line <code>validateArgs</code>
     * @param args
     *        takes an array of arguments the maximum size if 9
     * @return boolean result
     *         false if the arguments are invalid
     *         true if teh arguments are valid
     */
    public static boolean validateNumberOfArgs(String[] args) {

        if (args == null || args.length == 0 || args.length > 11) {
            if(args.length > 11)
                System.out.println("There are extraneous command line arguments");
            else
                System.out.println("No command line argument");
            return false;
        } else
            return true;
    }

    /**
     * <code>validArgumentsFormate</code>
     * checks the length of the arguments
     * @param args
     * @param j
     *        index of the argumnets
     */

    public static  void validateArgsFormate(String[] args, int j) {

        try {
            if(args[j] != null) {
                String customer = args[j];
                if (!customer.matches("[a-zA-Z0-9\\s]+")) {
                    System.out.println("Invalid customer name format");
                    System.exit(1);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("Customer name is missing!");
            System.exit(1);
        }
        try {
            if(args[j+1] != null) {
                //System.out.println(args[j+1]);
                String caller = args[j+1];
                if (!caller.matches("\\d{3}[-]\\d{3}[-]\\d{4}")) {
                    System.out.println("Invalid caller format");
                    System.exit(1);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("Caller number is missing!");
            System.exit(1);
        }
        try {
            if(args[j+2] != null) {
                String callee = args[j+2];
                if (!callee.matches("\\d{3}[-]\\d{3}[-]\\d{4}")) {
                    System.out.println("Invalid callee format");
                    System.exit(1);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("Callee number is missing!");
            System.exit(1);
        }
        try {
            if(args[j+3] != null) {
                String startDate = args[j+3];
                if(!startDate.matches("\\d{1,2}[/]\\d{1,2}[/]\\d{4}")) {
                    System.out.println("Invalid Start Date Format");
                    System.exit(1);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("Start date is missing!");
            System.exit(1);
        }
        try {
            if(args[j+4] != null) {
                String startTime = args[j+4];
                if(!startTime.matches("\\d{1,52}[:]\\d{2}")) {
                    System.out.println("Invalid Start Time format");
                    System.exit(1);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("Start time is missing!");
            System.exit(1);
        }
        try {
            if(args[j+5] != null) {
                String endDate = args[j+5];
                if(!endDate.matches("\\d{1,2}[/]\\d{1,2}[/]\\d{4}")) {
                    System.out.println("Invalid End Date Format");
                    System.exit(1);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("End date is missing!");
            System.exit(1);
        }
        try {
            if(args[j+6] != null) {
                String endTime = args[j+6];
                if(!endTime.matches("\\d{1,2}[:]\\d{2}")) {
                    System.out.println("Invalid End Time format");
                    System.exit(1);
                }
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("End time is missing!");
            System.exit(1);
        }
        try {
            if(args[j+7] != null) {
                    System.out.println("Unknown command line arguments!");
                    System.exit(1);
            }
        }catch(ArrayIndexOutOfBoundsException e ){
              e.fillInStackTrace();
        }

    }
    public static boolean isOptionReadME(String option) {
        if(option.equals("-README")) {
            return true;
        }else
            return false;
    }
    public static boolean isOptionPrint(String option) {
        if(option.equals("-print"))
            return true;
        else
            return false;
    }
    public static boolean isOptionTextFile(String option) {
        if(option.equals("-textFile"))
            return true;
        else
            return false;
    }

    /**
     * <code>validatetestFile</code>
     * this method checks the file name
     * @param args
     * @param i
     *        position of the file in the command line argument
     * @return boolean
     */

   public static boolean validatTextFile(String[] args, int i ){

       try {
           if(args[i] != null) {
               //File f = new File(args[i]);
               boolean s = Files.exists(Paths.get(args[i]));
               if(!s) {
                 //  System.out.println("File name is not correct!");
                   return false;
               }
               // System.out.println(f + (f.exists() ? " is found " : " is missing "));
           }
       }catch(ArrayIndexOutOfBoundsException e){
              System.out.println("Missing file name!");
              System.exit(1);
             // return false;
       }
       return true;
   }

    /**
     * <code>notEmpty</code>
     * this method check if the file is emptyu
     * @param name
     *        file name
     * @return boolean
     */
   public static boolean notEmpty(String name ){
        File New = new File(name);
        if(New.length() == 0)
            return false;
        return true;
   }

}



