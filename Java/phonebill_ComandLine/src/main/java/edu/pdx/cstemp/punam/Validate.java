package edu.pdx.cstemp.punam;

/**
 * This class represents a <code>Validate</code>
 * @author Punam Rani Pal
 */
public class Validate {
    /**
     * This method validates the arguments passed in the command line <code>validateArgs</code>
     * @param args
     *        takes an array of arguments the maximum size if 9
     * @return boolean result
     *         false if the arguments are invalid
     *         true if teh arguments are valid
     */
    public static boolean validateArgs(String[] args) {

        if (args == null || args.length == 0 || args.length < 8 ||args.length > 9) {
            /*if ( args.length < 7 && (!isOptionReadME(args[0]) || (!isOptionReadME(args[1])))){
                System.out.println("Invalid Arguments");
                return false;
            }else{*/
                System.out.println("Invalid Arguments");
                return false;
            //}
        }
       /* if ( args.length == 7 && (!isOptionReadME(args[0]))){
            System.out.println("Invalid Arguments");
            return false;
        }*/
        if (args.length == 8) {
            if(!isOptionPrint(args[0]) && !isOptionReadME(args[0])) {
                return false;
            }
        } else if (args.length == 9) {
            if((!isOptionPrint(args[0]) &&  !isOptionReadME(args[0])) &&
                    (!isOptionPrint(args[1]) && !isOptionReadME(args[1]))) {
                return false;
            }
        }
        //Initialize assuming there are no options
        String customer = args[0];
        String caller = args[1];
        String callee = args[2];
        String startDate = args[3];
        String startTime = args[4];
        String endDate = args[5];
        String endTime = args[6];
        if (args.length == 9) {
            customer = args[2];
            caller = args[3];
            callee = args[4];
            startDate = args[5];
            startTime = args[6];
            endDate = args[7];
            endTime = args[8];
        } else if (args.length == 8) {
            customer = args[1];
            caller = args[2];
            callee = args[3];
            startDate = args[4];
            startTime = args[5];
            endDate = args[6];
            endTime = args[7];
        }
        //regex pattern for alpha-numeric
        if (!customer.matches("[a-zA-Z0-9\\s]+")) {
            System.out.println("Invalid customer name format");
            return false;
        }

        if (!caller.matches("\\d{3}[-]\\d{3}[-]\\d{4}")) {
            System.out.println("Invalid caller format");
            return false;
        }

        if (!callee.matches("\\d{3}[-]\\d{3}[-]\\d{4}")) {
            System.out.println("Invalid callee format");
            return false;
        }

        if(!startDate.matches("\\d{1,2}[/]\\d{1,2}[/]\\d{4}")) {
            System.out.println("Invalid Date Format");
            return false;
        }

        if(!startTime.matches("\\d{1,52}[:]\\d{2}")) {
            System.out.println("Invalid Time format");
            return false;
        }

        if(!endDate.matches("\\d{1,2}[/]\\d{1,2}[/]\\d{4}")) {
            System.out.println("Invalid Date Format");
            return false;
        }

        if(!endTime.matches("\\d{1,2}[:]\\d{2}")) {
            System.out.println("Invalid Time format");
            return false;
        }

        return true;
    }
    /**
     * This method checks for the -print option <code>isOptionPrint</code>
     * @param option
     *        takes the either of first two arguents depending on the size.
     * @return boolean
     *         returns true if the option is -print else false
     */
    public static boolean isOptionPrint(String option) {
       /* if(option != null && option.startsWith("-")) {
            if(!option.equals("-print")) {
                return false;
            }
        }
        return true;
        */
       if(option.equals("-print"))
            return true;
       else
           return false;
    }
    /**
     * This method hecks if the option is -README <code>isOptionReadme</code>
     * @param option
     *        either of the first two arguents
     * @return boolean
     *         true if the option is -README else false
     */
    public static boolean isOptionReadME(String option) {
        /*if(option != null && option.startsWith("-")) {
            if(!option.equals("-README")) {
                return false;
            }
        }
        return true;
        */
        if(option.equals("-README")) {
            return true;
        }else
           return false;
    }
    public static boolean isOptionCorrect(String opt) {

            if (!isOptionPrint(opt) && (!isOptionReadME(opt))) {
                //System.out.println("Invalid option provided!");
                return false;
            }
            return true;
    }

            /**
             * This method printys out the usage of the program's command line arguments <code>printUsage</code>
             */
            public static void printUsage () {
                System.out.println("usage: java edu.pdx.cs410J.<login-id>.Project1 [options] <args>");
                System.out.println("args are (in this order): ");
                System.out.println("1: customer Person whose phone bill we’re modeling");
                System.out.println("2: calleeNumber Phone number of person who was called");
                System.out.println("3: startTime Date and time call began (24-hour time)");
                System.out.println("4: endTime Date and time call ended (24-hour time)");
                System.out.println("options are (options may appear in any order): ");
                System.out.println("5: -print Prints a description of the new phone call");
                System.out.println("6: -README Prints a README for this project and exits");
                System.out.println("7: Date and time should be in the format: mm/dd/yyyy hh:mm");
            }
            /**
             * This method prints the README <code>printReadMe</code>
             */
            public static void printReadMe () {
                System.out.println("README*****************\n" +
                        "Project Name: Phonebill\n" + "Programmed By: Punam Rani Pal\n" +
                        "This program has three classes: PhoneBill class keeps track of a new phone call" +
                        "made by the customer.\nPhoneCall class stores the information about the customer's" +
                        "call.Project1 class has main method that takes\ncommand line arguments options for" +
                        "either(-print/-README)or both. -print option will print the new call details\nlike" +
                        "caller and callee names, start date/time and end date/time.README" +
                        "describes the functionality of the program.\n******************");
                //System.exit(1);
            }
}


