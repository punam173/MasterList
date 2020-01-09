package edu.pdx.cs410J.punam.client;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.io.Serializable;
import java.lang.Override;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
/**
 * This class contains a list of phone calls in a phone bill
 * it extends abstract phone bill class and implements it methods
 * it also implements serializable
 * @author Punam Rani Pal
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> implements Serializable{
  private Collection<PhoneCall> calls = new ArrayList<>(); //list to hold the phone calls

  /**
   * In order for GWT to serialize this class (so that it can be sent between
   * the client and the server), it must have a zero-argument constructor.
   */
  public PhoneBill() {

  }

  public ArrayList<PhoneCall> pcCollection = new ArrayList<PhoneCall>();
  //Refers to a new list of phonecalls
  private String customer = ""; // refers to a customer name

  /**
   * This method sets the name of the customer <code>setCustomer</code>.
   */
  public void setCustomer(String Cus){

    this.customer = Cus;
  }
  @Override
  /**
   * This method get the name of the customer <code>getCustomer</code>
   * @return the name of the customer.
   */
  public String getCustomer() {

    return this.customer;
  }
  @Override
  /**
   * This method adds a phone call to this phonebill <code>addPhoneCall</code>
   * @param phoneCall
   *        new phone call entry
   */
  public void addPhoneCall(PhoneCall phoneCall) {
    this.pcCollection.add((PhoneCall)phoneCall);
    Collections.sort((ArrayList)this.pcCollection);
  }
  @Override
  /**
   * This method returns all the phone calls made by the customer <code>Collection</code>
   * @return all the phone calls
   */
  public ArrayList<PhoneCall> getPhoneCalls() {
    //   Collections.sort(pcCollection);

    return this.pcCollection;
  }
}
