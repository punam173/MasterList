package edu.pdx.cs410J.punam;

import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validate3 {
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
                    if(!arg.equals("-README") && !arg.equals("-print") && !arg.equals("-textFile") && !arg.equals("-pretty") && !arg.equals("-")){
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

        if (args == null || args.length == 0 || args.length > 14) {
            if(args.length > 14)
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
        StringBuffer res = new StringBuffer();


        try {
            if(args[j+3] != null) {
                String startDate = args[j+3];

                if(!startDate.matches("\\d{1,2}[/]\\d{1,2}[/]\\d{4}")) {
                    System.out.println("Invalid Start Date Format");
                    System.exit(1);
                }
                res.append(args[j+3]);
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
                res.append(" ").append(args[j+4]);
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("Start time is missing!");
            System.exit(1);
        }
        try{
            if(args[j+5] != null){
                String am_pm = args[j+5];
               // System.out.println("****"+am_pm);
                if(!(am_pm.equalsIgnoreCase("PM"))&& (!(am_pm.equalsIgnoreCase("AM")))){
                    System.out.println("Invalid (am/pm) format!");
                    System.exit(1);
                }
                res.append(" ").append(args[j+5].toUpperCase());
            }
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("(am/pm) is missing!");
        }
        StringBuffer res1 = new StringBuffer();
        try {
            if(args[j+6] != null) {
                String endDate = args[j+6];
                if(!endDate.matches("\\d{1,2}[/]\\d{1,2}[/]\\d{4}")) {
                    System.out.println("Invalid End Date Format");
                    System.exit(1);
                }
                res1.append(args[j+6]);
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("End date is missing!");
            System.exit(1);
        }
        try {
            if(args[j+7] != null) {
                String endTime = args[j+7];
                if(!endTime.matches("\\d{1,2}[:]\\d{2}")) {
                    System.out.println("Invalid End Time format");
                    System.exit(1);
                }
                res1.append(" ").append(args[j+7]);
            }
        }catch(ArrayIndexOutOfBoundsException e ){
            System.out.println("End time is missing!");
            System.exit(1);
        }
        try{
            if(args[j+8] != null){
                String am_pm = args[j+8];
                if(!(am_pm.toUpperCase().contains("PM"))&& (!(am_pm.toUpperCase().contains("AM")))){
                    System.out.println("Invalid (am/pm) format!");
                    System.exit(1);
                }
                res1.append(" ").append(args[j+8].toUpperCase());
            }
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("(am/pm) is missing!");
        }
        try{
            Date a = parseDateAndTime(res.toString());
            //System.out.println(a);
            Date b = parseDateAndTime(res1.toString());
            //System.out.println(b);
            if(a.compareTo(b) > 0){
                System.out.println("Start time is after the end time!");
                System.exit(1);
            }
        }catch (ParserException e) {
            e.printStackTrace();
        }
        try {
            if(args[j+9] != null) {
                System.out.println("Unknown command line arguments!");
                System.exit(1);
            }
        }catch(ArrayIndexOutOfBoundsException e ){
             System.out.println("");
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

    /**
     *  Parses the dateString string against two different string formats, MM/dd/yyyy HH:mm  aa and "M/dd/yyyy HH:mm aa, to make
     *  sure that they were entered in under the correct format. If it's not in the proper format, <code>ParseDateAndTime</code>
     * will throw a <code>ParseException</code>, give an error message, and exit the program.
     * @param dateString The date and time string from the command line that will be parsed.
     * @throws ParserException Throws this exception if the date is in the wrong format.
     * @return Returns the new <code>Date</code> object that was parsed if it was in the correct format.
     */
    public static Date parseDateAndTime(String dateString) throws ParserException {

        String pattern ="MM/dd/yyyy hh:mm aa";
        String[] dateTempArray = dateString.split(" ");
        String [] dateStr = dateTempArray[0].split("/");
        String [] timeD = dateTempArray[1].split(":");
        String day,month,year;
        String hr,minute;
        String ampm;
        if(dateStr[0].length()==1)
            day="0"+dateStr[0];
        else day=dateStr[0];
        if(dateStr[1].length()==1)
            month="0"+dateStr[1];
        else month=dateStr[1];

        year=dateStr[2];

        if(timeD[0].length()==1)
            hr="0"+ timeD[0];
        else hr=timeD[0];
        minute=timeD[1];

        if(dateTempArray[2].toUpperCase().contains("AM"))
            ampm="AM";
        else ampm="PM";
        // dateString = dateString.replace("pm", "PM").replace("am", "AM");
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(day + "/" + month + "/" + year + " " + hr + ":" + minute + " " + ampm);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ParserException("Date must be in the format MM/dd/yyyy hh:mm (am/pm).");

        }

    }

}
