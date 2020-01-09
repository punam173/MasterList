package edu.pdx.cs410J.punam;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

/**
 * <code>TextParse</code>
 * This class reads the phone bill to the text file
 * @author  Punam Rani Pal
 */
public class TextParser3 implements PhoneBillParser {
    private String fileName; //file name
    /**
     * <code>TextParse</code>
     * this method is the constructor
     * @param fileName
     */
    public TextParser3(String fileName) {
        this.fileName = fileName;
    }
    @Override
    /**
     * <code>AbstractPhoneBill</code>
     * This method parse the input from the file and then store it into the phone bill object
     * @return bill
     * @Throws ParserException
     */
    public AbstractPhoneBill parse() throws ParserException {
    PhoneBill bill = new PhoneBill(); //creats a new phone bill object
    Scanner sc = null;
        try {

        sc = new Scanner(new File(this.fileName));
        File file = new File(this.fileName);

        if (file.length() != 0) {
            bill.setCustomer(sc.next());
            sc.nextLine();
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if(line.startsWith("[Phone") && line.endsWith("]")) {
                    String[] details = line.replace("[","").replace("]","").split(" ");
                    PhoneCall b = new PhoneCall();
                    if (!details[3].matches("\\d{3}[-]\\d{3}[-]\\d{4}")) {
                      System.out.println("Invalid caller format in file");
                      System.exit(1);
                    }
                    b.setCaller(details[3]);

                    if (!details[5].matches("\\d{3}[-]\\d{3}[-]\\d{4}")) {
                        System.out.println("Invalid callee format in file");
                        System.exit(1);
                    }
                    b.setCallee(details[5]);
                    String z1 = details[7].replace(",", "");
                    if(!z1.matches("\\d{1,2}[/]\\d{1,2}[/]\\d{2,4}")){
                        System.out.println("Invalid Start date Formate in File");
                        System.exit(1);
                    }

                    if(!details[8].matches("\\d{1,2}[:]\\d{2}")){
                        System.out.println("Invalid Start Time Formate in File");
                        System.exit(1);
                    }
                    if(!details[9].equalsIgnoreCase("AM") && !details[9].equalsIgnoreCase("PM")){
                        System.out.println("Invalid Start Time (am/pm)Formate in File");
                        System.exit(1);
                    }
                    String a1 = z1 + " " + details[8]+ " "+ details[9];
                    Date a2 = Validate3.parseDateAndTime(a1);
                    b.setStartDateTime(a2);
                    String z = details[13];
                    String z2 = details[11].replace(",","");
                    if(!z2.matches("\\d{1,2}[/]\\d{1,2}[/]\\d{2,4}")){
                        System.out.println("Invalid End Date Formate in File");
                        System.exit(1);
                    }

                    if(!details[12].matches("\\d{1,2}[:]\\d{2}")){
                        System.out.println("Invalid End Time Formate in File");
                        System.exit(1);
                    }
                    if(!details[13].equalsIgnoreCase("AM") && !details[13].equalsIgnoreCase("PM")){
                        System.out.println("Invalid End Time (am/pm)Formate in File");
                        System.exit(1);
                    }
                    String a3 = z2 + " " + details[12]+ " " +z;
                    Date a4 = Validate3.parseDateAndTime(a3);
                    if((a2.compareTo(a4) > 0)){
                        System.out.println("End Time Is Before It's Start Time! ");
                        System.exit(1);
                    }
                    b.setEndDateTime(a4);

                    bill.addPhoneCall(b);
                }else {
                    System.out.println("File Format is not correct!");
                    System.exit(1);
                }
            }
        }
    }catch(IOException e) {
        e.fillInStackTrace();
    }catch (ArrayIndexOutOfBoundsException e) {
        e.printStackTrace();
    } catch (ParseException e) {
        e.printStackTrace();
    } finally{
        sc.close();
    }
        return bill;
}
}
