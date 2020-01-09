package edu.pdx.cs410J.punam;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

/**
 * <cod>Project3</cod>
 * The main class for the CS410J Phone Bill Project that parses command line arguments to make sure they are in the correct
 * format before creating a <code>PhoneBill</code> and <code>PhoneCall</code> objects of the passed in arguments.
 * @author Punam Rani Pal
 */
public class Project3 {
    /**
     * <code>main</code>
            * @param args
     *        Takes the command line from the argument
     */
    public static void main(String[] args) throws ParseException {
        PhoneCall call = new PhoneCall(); //an instance of the phonecill class
        PhoneBill bill = new PhoneBill(); //an instance of the phone call class
        String fileName = "bill.text"; //intialized with a temporary file name
        String fileName2 = ""; // stores pretty print file
        boolean isPrintOption = false; //validates -print option
        boolean isTextFileOption = false; //validates -TextFile
        boolean isPrettyPrinter = false;
        int argIndex = 0; //keeps track of the options position
        int flag = 0;
        int textPosition = 0;
        int prettyPosition = 0;
        //checking fo rthe validation of the number of arguments
        if (Validate3.validateNumberOfArgs(args) && Validate3.validateOptions(args)) {
            if (containsOption(args, "-README"))
                printReadMe();
            else {
                //validate logic
                if (args.length <= 14) {
                    if (containsOption(args, "-pretty")) {
                        isPrettyPrinter = true;
                        prettyPosition = findOptionIndex(args, "-pretty");
                       // System.out.println(prettyPosition);
                        if (parsePrettyFile(args[prettyPosition + 1])) {
                            fileName2 = args[prettyPosition + 1];
                        }
                    }
                    if (containsOption(args, "-textFile")) {
                        isTextFileOption = true;
                        // if (args.length >= 9) {
                        int optionIndex = findOptionIndex(args, "-textFile");
                        textPosition = optionIndex;
                        if (Validate3.validatTextFile(args, optionIndex + 1)) {
                            fileName = args[optionIndex + 1];
                            TextParser3 readFromFile = new TextParser3(fileName);
                            try {
                                //Systm.out.println("&&&&");
                                bill = (PhoneBill) readFromFile.parse();
                                //System.out.println(bill.getCustomer()+"****");
                            } catch (ParserException e) {
                                e.printStackTrace();
                                System.exit(1);
                            }

                        } else { //creates a new file if the file is not present
                            int optionindex = findOptionIndex(args, "-textFile");
                            fileName = args[optionindex + 1];
                            TextDumper3 create = new TextDumper3(fileName);
                            create.createNewFile();
                            //flag = 1;
                        }
                    }
                    //if print option is there then validate the arguments
                    if (containsOption(args, "-print")) {
                        isPrintOption = true;
                        argIndex = findOptionIndex(args, "-print") + 1;
                        Validate3.validateArgsFormate(args, argIndex);
                    } else { //validating the args for without -print and with -TextFile
                        if ((isTextFileOption)) {
                            if (isPrettyPrinter)
                                argIndex = 4;
                            else
                                argIndex = 2;
                            Validate3.validateArgsFormate(args, argIndex);
                        }
                    }
                    //if the new call information gets validated then it populates the arguments
                    if (isTextFileOption || isPrintOption){
                        try {
                            call = populatePhoneCall(args, argIndex);
                            //System.out.println(call.getEndTimeString());
                        } catch (ParserException e) {
                            e.printStackTrace();
                        }
                        bill.pcCollection.add(call);  //adding a new call
                        if (isPrintOption) {
                        System.out.println(call);
                        }
                    }
                    //if text file is true
                    if (isTextFileOption && isCustomerMatch(bill, args[argIndex], fileName)) {
                        bill.setCustomer(args[argIndex]);
                        TextDumper3 writeToFile = new TextDumper3(fileName);
                        try {
                            writeToFile.dump(bill);
                            //System.exit(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                    } else {
                        if (!isCustomerMatch(bill, args[argIndex], fileName))
                            System.out.println("Customer name did not match for the given file name!");
                            System.exit(1);
                    }
                    //If pretty file option is there
                    if (isPrettyPrinter) {
                        PrettyPrinter print = new PrettyPrinter(fileName2);
                        PrettyPrinter standardOut = new PrettyPrinter();
                        if (isTextFileOption) {
                            TextParser3 readAgain = new TextParser3(fileName);
                            PhoneBill bill2 = new PhoneBill();
                            if (fileName2.equals("-")) {

                                try {
                                    bill2 = (PhoneBill) readAgain.parse();
                                    standardOut.dumpStandardOut(bill2);
                                    System.exit(0);
                                } catch (ParserException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    bill2 = (PhoneBill) readAgain.parse();
                                } catch (ParserException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    print.dump(bill2);
                                    System.exit(0);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (!isPrintOption) {
                            argIndex = 2;
                            Validate3.validateArgsFormate(args, argIndex);
                            try {
                                call = populatePhoneCall(args, argIndex);
                            } catch (ParserException e) {
                                e.printStackTrace();
                            }
                            bill.pcCollection.add(call);
                        }
                        if (fileName2.equals("-")) {
                                print.dumpStandardOut(bill);
                                System.exit(0);
                        } else {
                                try {
                                    print.dump(bill);
                                    System.exit(0);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                        }
                    }else{ //no options
                        Validate3.validateArgsFormate(args, argIndex);
                        System.out.println("All command line arguments are validated");
                        System.exit(0);
                    }

                }else
                    System.out.println("Unknowm Command Line arguments!");
            }
        }
    }

    /**
     * <code>isCustomerMatch</code>
     * This method checks if the customer name is matched with the name in the file
     * @param bill
     *        get the customer name
     * @param arg
     *        get the new customer name
     * @param name
     *        file name
     * @return boolean
     */
    private static boolean isCustomerMatch(PhoneBill bill, String arg, String name) {
        if (!Validate3.notEmpty(name))
            return true;
        if (bill != null && bill.getCustomer() != null && (bill.getCustomer().length() > 0)) {
            return bill.getCustomer().equalsIgnoreCase(arg);
        }
        return false;
    }
    /**
     * <code>populatePhoneCall</code>
     * This method populate the callee information with the arguments from the command line
     * @param args
     *        command line input
     * @param argIndex
     *        to tack the exact position
     * @return phonecall
     */
    private static PhoneCall populatePhoneCall(String[] args, int argIndex) throws ParserException, ParseException {
        PhoneCall call = new PhoneCall();
        call.setCaller(args[argIndex+1]);
        call.setCallee(args[argIndex + 2]);
         String a = args[argIndex + 3] + " " + args[argIndex + 4]+ " "+ args[argIndex + 5];
         Date b = Validate3.parseDateAndTime(a);
        // System.out.println(b.getTime());
         call.setStartDateTime(b);
         String a2 = args[argIndex + 6] + " " + args[argIndex + 7]+" "+args[argIndex + 8];
         Date b2 =Validate3.parseDateAndTime(a2);
       //  System.out.println(b2.getTime());
        if((b.compareTo(b2) > 0)){ //validate the end date occurrence
            System.out.println("End Time Is Before It's Start Time! ");
            System.exit(1);
        }
         call.setEndDateTime(b2);
        return call;
    }

    /**
     * <code>parsePrettyFile</code>
     * This method check the format of pretty file
     * @param aname
     * @return
     */
    private static boolean  parsePrettyFile(String aname){
        if((!aname.contains(".txt") && (!aname.contains("-"))) ){
            System.out.println("Pretty File is missing!");
            System.exit(1);
        }
        return true;
    }
    /**
     * <code>findOptionIndex</code>
     * This method get the index of the option
     * @param args
     *        argument from the command line
     * @param option
     *        option name
     * @return int
     *         gives back the index of the option
     */
    private static int findOptionIndex(String[] args, String option) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(option)) {
                return i;
            }
        }
        return -1;
    }
    /**
     * <code>printReadMe</code>
     * print the text that gives a brief about the project
     */
    private static void printReadMe () {
        System.out.println("README*****************\n" +
                "Project Name: Phone bill Text File" + "  Programmed By: Punam Rani Pal\n" +
                "This program creates a text file of the phone bill for a customer and read " +
                "or write based on the option given It stores the class in sorted order." + "\nIt maintains the call information of a " +
                "a person. Apart from that it optionally prints the new call information" +
                ".\nThis program accepts following arguments:" +
                "\nCustomer name(ex: Punam Pal )\nCaller number and Callee number(123-234-3456)\n" +
                "Start Date and Time (mm/dd/yyyy  hh:mm am/pm)\nEnd Date and Time (mm/dd/yyyy  hh:mm am/pm)\n" +
                "-textFile : It wites/read the phone bill text file\n-print : print the information " +
                "of the new call\n-prett file : It prints the phone bill in the text file or standard output.\n-README  :will show you this text\nThis program terminates if the " +
                "argument is invalid and shows you the proper message.\n******************");
    }

    /**
     * <code>containsOption</code>
     * This method check the options
     * @param args
     *        represents command line argument
     * @param option
     *        option name
     * @return boolean
     */
    private static boolean containsOption (String[]args, String option){
        return Arrays.stream(args).anyMatch(s -> s.equals(option));
    }
}
