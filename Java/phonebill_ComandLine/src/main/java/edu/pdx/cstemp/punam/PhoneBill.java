package edu.pdx.cstemp.punam;
import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a PhoneBill that extends an abstract
 * PhoneBill class which has a collection of PhoneCall classes <code>PhoneBill</code>
 * @author Punam Rani Pal
 */
public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    Collection<PhoneCall> pcCollection = new ArrayList<PhoneCall>(); //Refers to a new list of phonecalls
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

        this.pcCollection.add(phoneCall);
    }
    @Override
    /**
     * This method returns all the phone calls made by the customer <code>Collection</code>
     * @return all the phone calls
     */
    public Collection<PhoneCall> getPhoneCalls() {

        return this.pcCollection;
    }
}
