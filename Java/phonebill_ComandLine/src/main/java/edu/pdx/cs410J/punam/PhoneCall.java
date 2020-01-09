package edu.pdx.cs410J.punam;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * The <code>PhoneBill</code> class extends the <code>AbstractPhoneBill</code> class which creates
 * a bill consisting of a series of phone calls addressed to one person.
 * @author Punam Rani Pal
 */

public class PhoneCall extends AbstractPhoneCall implements Comparable<PhoneCall> {
    private String caller = ""; //refers to the caller(customer) name
    private String callee = ""; //refers to the callee name(person who is being called)
    private Date startDateTime; //holds the start date and time
    private Date endDateTime;   //holds the End date and time
    /**
     * This method set the customet name <code>setCaller</code>
     */
    public void setCaller(String Caller){
        this.caller = Caller;
    }
    @Override
    /**
     * This method get the customet name <code>getCaller</code>
     * @return the customer name
     */
    public String getCaller() {

        return this.caller;
    }
    /**
     * This method set the callee name <code>setCallee</code>
     */
    public void setCallee( String Callee){
        this.callee = Callee;
    }
    @Override
    /**
     * This method get the callee name <code>getCallee</code>
     * @return the callee name
     */
    public String getCallee() {

        return this.callee;
    }
    /**
     * This method set the start date and time of a phone call <code>setStartTimeString</code>
     */
    public void setStartDateTime( Date dateTime) throws ParseException {
        this.startDateTime = dateTime;
    }
    @Override
    /**
     * This method get the start date and time of a phone call <code>getStartTimeString</code>
            * @return the start date and time of a call
     */
    public String getStartTimeString() {
        String a = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(this.startDateTime);
        return a;
    }
    /**
     * This method set the end date and time of a phone call <code>setEndTimeString</code>
     */
    public void setEndDateTime( Date dateTime){
        this.endDateTime = dateTime;
    }
    @Override
    /**
     * This method get the end date and time of a phone call <code>getEndTimeString</code>
     * @return the end date and time of a call
     */
    public String getEndTimeString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(this.endDateTime);
    }
    /**
     * This method <code>getStartTime</code> returns the string (date) into date formate
     * @return start date and time
     */
    public Date getStartTime(){
        return this.startDateTime;
    }
    /**
     * This method <code>getEndTime</code> returns the string (date) into date formate
     * @return End date and time
     */
    public Date getEndTime(){
        return this.endDateTime;
    }

    /**
     * This method <code>compareTo</code> compares the start time an dif they are equal
     * the it compares the caller no
     * @param o
     * @return the comapared value
     */
    @Override
    public int compareTo(PhoneCall o) {
        int diff = this.startDateTime.compareTo(o.getStartTime());
        if (diff == 0) {
            return this.caller.compareTo(o.getCaller());
        } else {
            return diff;
        }
    }
}



