package edu.pdx.cstemp.punam;
import java.io.*;
import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * <code>textDumper</code>
 * This class dump the the new call information to a text file
 */
public class TextDumper implements PhoneBillDumper {
    private String fileName;

    /**
     * <code>TextDumper</code>
     * constructor that intialize the file name
     * @param fileName
     */
    public TextDumper(String fileName) {
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
        if (Ff.length() == 0) {
            flag = 1;
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(this.fileName, true); //the true will append the new data
            if(flag == 1) {
                fw.write(abstractPhoneBill.getCustomer());
                fw.write("\n");
            }
            fw.write(String.valueOf(abstractPhoneBill.getPhoneCalls()));//appends the string to the file
            fw.write("\n");
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
