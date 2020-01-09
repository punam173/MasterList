package edu.pdx.cstemp.punam;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.io.*;
import java.text.ParseException;
import java.util.Scanner;

/**
 * <code>TextParser</code>
 * This method implements an interface phonebillParse and imlements its method
 * It parse the call information from a text file and store it into the phone
 * bill object.
 */
public class TextParser implements PhoneBillParser {
    private String fileName; //file name

    /**
     * <code>TextParse</code>
     * this method is the constructor
     * @param fileName
     */
    public TextParser(String fileName) {
        this.fileName = fileName;
    }
    @Override
    /**
     * <code>AbstractPhoneBill</code>
     * This method parse the input from the file and then store it into the bhone bill object
     * Throws ParserException
     */
    public AbstractPhoneBill parse() throws ParserException {

        PhoneBill bill = new PhoneBill(); //creats a new phone bill object
        Scanner sc = null;
        try {

            sc = new Scanner(new File(this.fileName));
            File file = new File(this.fileName);

            if (file.length() != 0) {
             ////   String customer = sc.next();
               // if (!customer.matches("[a-zA-Z0-9\\s]+")) {
                  //  System.out.println("Invalid customer name format");
                  //  System.exit(1);
             //   }
                bill.setCustomer(sc.next());
                sc.nextLine();
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if(line.startsWith("[Phone") && line.endsWith("]")) {
                      ////  System.out.println("asfsadf");
                        String[] details = line.split(" ");
                        PhoneCall b = new PhoneCall();
                        //String caller = details[3];
                        //if (!caller.matches("\\d{3}[-]\\d{3}[-]\\d{4}")) {
                          //  System.out.println("Invalid caller format");
                            //System.exit(1);
                        //}
                        b.setCaller(details[3]);
                        b.setCallee(details[5]);
                        //String startDate = details[7];
                        //if(!startDate.matches("\\d{1,2}[/]\\d{1,2}[/]\\d{4}")) {
                          //  System.out.println("Invalid Start Date Format");
                            //System.exit(1);
                        //}
                        b.setStartDateTime(details[7] + " " + details[8]);
                        b.setEndDateTime(details[10] + " " + details[11]);
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



