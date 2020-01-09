package edu.pdx.cs410J.punam;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;

import java.util.ArrayList;
import java.util.Collections;

public class PhoneBill extends AbstractPhoneBill {
    ArrayList<PhoneCall> pcCollection = new ArrayList<PhoneCall>(); //Refers to a new list of phonecalls
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
    public void addPhoneCall(AbstractPhoneCall phoneCall) {
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
