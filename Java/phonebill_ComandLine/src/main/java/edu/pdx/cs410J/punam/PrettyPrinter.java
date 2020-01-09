package edu.pdx.cs410J.punam;

import edu.pdx.cs410J.PhoneBillDumper;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * This class implements the <code>PhoneBillDumper</code> class so it can print the contents of a phone bill in a human
 * readable format that looks 'pretty'. It can either print to a specific file or it can print to the output stream.
 * @author Punam Rani Pal
 */

public class PrettyPrinter implements PhoneBillDumper<PhoneBill> {
    private String filename; //stores pretty file name
    private long duration;   // holds the length of the phone call in minutes
    private ArrayList<PhoneCall> callList; //holds the list of phone calls
    private String startDate; //holds the starting date
    private String endDate;  //holds the end date
    //constructor to create the Petty printer class without arguments
    public PrettyPrinter(){}

    /**
     * construction to initialize the pretty file name
     * @param filename
     */
    public PrettyPrinter( String filename){this.filename = filename;}

    /**
     * This method <code>dump</code> print the sorted phone bills in the text file
     * @param bill
     * @throws IOException
     */
    @Override
    public void dump(PhoneBill bill) throws IOException {
        PrintWriter writer;
        callList = bill.getPhoneCalls();
        Collections.sort(callList);
        try {
            writer = new PrintWriter(filename);
        }catch(IOException e){
            throw e;
        }
        writer.format("Hello %s! You currently have %d calls logged on your bill.\nHere's a list of all your calls:\n",
                bill.getCustomer(), bill.getPhoneCalls().size());

        writer.format("    Caller               Callee                 StartDateTime                EndDateTime         Duration(min) \n");
        for (PhoneCall call : callList) {
            duration = call.getEndTime().getTime() - call.getStartTime().getTime();
            duration = TimeUnit.MILLISECONDS.toMinutes(duration);
            startDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(call.getStartTime());
            endDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(call.getEndTime());
            writer.format("  %s         %s           %s            %s           %s        \n",
                    call.getCaller(), call.getCallee(), startDate, endDate, duration);
        }
        writer.close();
    }

    /**
     * This method <code>dump</code> do the standard print fiven a file option '-'
     * @param bill
     */
    public void dumpStandardOut(PhoneBill bill) {
        callList = ((PhoneBill) bill).getPhoneCalls();

        System.out.format("Hello %s! You currently have %d calls logged on your bill.\nHere's a list of all your calls:\n",
                bill.getCustomer(), bill.getPhoneCalls().size());
        System.out.format("    Caller               Callee                 StartDateTime                EndDateTime         Duration(min) \n");
        for (PhoneCall call : callList) {
            duration = call.getEndTime().getTime() - call.getStartTime().getTime();
            duration = TimeUnit.MILLISECONDS.toMinutes(duration);
            startDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(call.getStartTime());
            endDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(call.getEndTime());
            System.out.format("  %s         %s           %s            %s           %s        \n",
                    call.getCaller(), call.getCallee(), startDate, endDate, duration);

        }
    }
}

