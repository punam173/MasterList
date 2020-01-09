package edu.pdx.cs410J.punam;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * <code>TextDumper</code>
 * This class writes the phone bill to the text file
 * @author  Punam Rani Pal
 */
public class TextDumper3 implements PhoneBillDumper {
    private String fileName;
    /**
     * <code>TextDumper</code>
     * constructor that intialize the file name
     * @param fileName
     */
    public TextDumper3(String fileName) {
        this.fileName = fileName;
    }
    @Override
    /**
     * <code>dump</code>
     * This class write the information the a file
     * Param AbstractPhoneBill
     *       takes the phone bill class as argumnet
     * Throws IOexception
     */
    public void dump(AbstractPhoneBill abstractPhoneBill) throws IOException {
        int flag = 0;
        File Ff = new File(this.fileName);
        FileWriter fw = null;
        try {
            fw = new FileWriter(this.fileName);
                fw.write(abstractPhoneBill.getCustomer());
                fw.write("\n");
          ArrayList<PhoneCall> aList = new ArrayList<PhoneCall>();
            aList=((PhoneBill)abstractPhoneBill).getPhoneCalls();
            for(PhoneCall aCall: aList) {
                fw.write("["+aCall.toString()+"]");//appends the string to the file
                       fw.write("\n");
          }
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        } finally {
            fw.close();
        }
    }
    /**
     * <code>createNewFile</code>
     * this method creates a new file when the file is present
     */
    public void createNewFile(){
        File file = new File(this.fileName);
        try {
            file.createNewFile();
        }catch (IOException e ){
            e.fillInStackTrace();
        }
    }
}
