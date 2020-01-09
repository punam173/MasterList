package edu.pdx.cstemp.punam;

import edu.pdx.cs410J.AbstractPhoneCall;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


/**
 * This class represents a PhoneCall that extends an abstract PhoneCall class
 * and override all its abstract methods. This class which stores all the information
 * regarding a call made, like, caller name, callee name, start and end time and date.
 * <code>PhoneCall</code>
 * @author Punam Rani pal
 */
public class PhoneCall extends AbstractPhoneCall {
  private String caller = ""; //refers to the caller(customer) name
  private String callee = ""; //refers to the callee name(person who is being called)
  private String startDateTime = "";//refers to the time when the call is made
  private String endDateTime = "";//refers to the time when the call is terminated

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
  public void setStartDateTime( String dateTime) throws ParseException {
    this.startDateTime = dateTime;
  }
  @Override
  /**
   * This method get the start date and time of a phone call <code>getStartTimeString</code>
   * @return the start date and time of a call
   */
  public String getStartTimeString() {
   return this.startDateTime;
  }
  /**
   * This method set the end date and time of a phone call <code>setEndTimeString</code>
   */
  public void setEndDateTime( String dateTime){
    this.endDateTime = dateTime;
  }
  @Override
  /**
   * This method get the end date and time of a phone call <code>getEndTimeString</code>
   * @return the end date and time of a call
   */
  public String getEndTimeString() {
   return this.endDateTime;
  }
}
