package edu.pdx.cstemp.punam;
import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

/**
 * <code>Project2</code>
 * The main Class for the Project 2, where the program actually begins
 */
public class Project2 {
    /**
     * <code>main</code>
     * @param args
     *        Takes the command line arguments f
     */
    public static void main(String[] args) throws ParseException {
        PhoneCall call = new PhoneCall(); //an instance of the phonecill class
        PhoneBill bill = new PhoneBill(); //an instance of the phone call class
        String fileName = "bill.text"; //intialized with a temporary file name
        boolean isPrintOption = false; //validates -print option
        boolean isTextFileOption = false; //validates -TextFile
        int argIndex = 0; //keeps track of the options position
        int flag = 0;
        //checking fo rthe validation of the number of arguments
        if (Validate2.validateNumberOfArgs(args) && Validate2.validateOptions(args)) {
            if (containsOption(args, "-README"))
                printReadMe();
            else {
                //validate logic
                if(args.length <= 10) {
                    if (containsOption(args, "-textFile")) {
                        isTextFileOption = true;
                        // if (args.length >= 9) {
                        int optionIndex = findOptionIndex(args, "-textFile");
                        if (Validate2.validatTextFile(args, optionIndex + 1)) {
                            fileName = args[optionIndex + 1];
                            TextParser readFromFile = new TextParser(fileName);
                            try {
                                //System.out.println("&&&&");
                                bill = (PhoneBill) readFromFile.parse();
                                //System.out.println(bill.getCustomer()+"****");
                            } catch (ParserException e) {
                                e.printStackTrace();
                                System.exit(1);
                            }

                        } else { //creates a new file if the file is not present
                            int optionindex = findOptionIndex(args, "-textFile");
                            fileName = args[optionindex + 1];
                            TextDumper create = new TextDumper(fileName);
                            create.createNewFile();
                            //flag = 1;
                        }
                    }
                    if (containsOption(args, "-print")) {
                        isPrintOption = true;
                        argIndex = findOptionIndex(args, "-print") + 1;
                        Validate2.validateArgsFormate(args, argIndex);
                    } else { //validating the args for without -print and with -TextFile
                        if ((isTextFileOption)) {
                            argIndex = 2;
                            Validate2.validateArgsFormate(args, argIndex);
                        } else {
                            Validate2.validateArgsFormate(args, argIndex);
                            System.out.println("All command line arguments are validated");
                            System.exit(0);
                        }
                    }
                    //if the new call information gets validated then it populates the arguments
                    try {
                        call = populatePhoneCall(args, argIndex);
                    } catch (ParserException e) {
                        e.printStackTrace();
                    }
                    bill.pcCollection.add(call);  //adding a new call
                    if (isPrintOption) {
                        System.out.println(call);
                    }
                    PhoneBill newBill = new PhoneBill(); //creating a new phone bill with the new call info
                    newBill.setCustomer(args[argIndex]);
                    newBill.pcCollection.add(call);
                    if (isTextFileOption && isCustomerMatch(bill, args[argIndex], fileName)) {
                        TextDumper writeToFile = new TextDumper(fileName);
                        try {
                            writeToFile.dump(newBill);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                    } else {
                        if (!isCustomerMatch(bill, args[argIndex], fileName))
                            System.out.println("Customer name did not match for the given file name!");
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
        if (!Validate2.notEmpty(name))
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
        call.setStartDateTime(args[argIndex + 3] + " " + args[argIndex + 4]);
        call.setEndDateTime(args[argIndex + 5] + " " + args[argIndex + 6]);
        return call;
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
                "or write based on the option given." + "\nIt maintains the call information of a " +
                "a person. Apart from that it optionally prints the new call information" +
                ".\nThis program accepts following arguments:" +
                "\nCustomer name(ex: Punam Pal )\nCaller number and Callee number(123-234-3456)\n" +
                "Start Date and Time (mm/dd/yyyy  hh:mm)\nEnd Date and Time (mm/dd/yyyy  hh:mm)\n" +
                "-textFile : It wites/read the phone bill text file\n-print : print the information " +
                "of the new call\n-README  :will show you this text\nThis program terminates if the " +
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
