package edu.pdx.cs410J.punam.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.punam.client.PhoneBill;
import edu.pdx.cs410J.punam.client.PhoneCall;
import edu.pdx.cs410J.punam.client.PhoneBillService;
import java.util.*;
/**
 * The server-side implementation of the Phone Bill service
 * @author Punam Rani Pal
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService
{
  private final PhoneCall call = new PhoneCall(); //an instance of the phonebill class
  private final PhoneBill bill = new PhoneBill(); //an instance of the phone call class

  private static final Map<String, PhoneBill> customerList = new HashMap<String, PhoneBill>();
  private ArrayList<PhoneCall> callList; //holds the call list

    /**
     * This method retuns the data from the server to the client
     * if all the imputs are validated
     * @param customer
     * @param startDate
     * @param endDate
     * @return phone bill
     */
  @Override
  public PhoneBill getPhoneBill(String customer,Date startDate ,Date endDate ) {
    PhoneBill bill=new PhoneBill();
      if(!customerList.containsKey(customer)){
         // bill=new PhoneBill();
          bill.setCustomer("Customer Not found");
          bill.addPhoneCall(new PhoneCall());
         return bill;
      }
      if (startDate != null && endDate != null) {
        bill=customerList.get(customer);
        callList = bill.getPhoneCalls();

       PhoneBill tempPhoneBill=new PhoneBill();
       tempPhoneBill.setCustomer(customer);
       Collections.sort(callList);
        //long duration;
       PhoneCall callTempList;

          for (PhoneCall call : callList) {
        if(call.getStartTime().compareTo(startDate)>=0  && call.getEndTime().compareTo(endDate)<=0){
          callTempList=new PhoneCall();

            callTempList.setCaller(call.getCaller());
            callTempList.setCallee(call.getCallee());
            callTempList.setStartDateTime(call.getStartTime());
            callTempList.setEndDateTime(call.getEndTime());

            tempPhoneBill.addPhoneCall(callTempList);
           }

        }
           return tempPhoneBill;

      } else {
        if(customer!=null && !customer.isEmpty())
        bill=customerList.get(customer);
      }
    return bill;
  }

    /**
     * This method add a new call details from the client to the server
     * @param customer
     * @param caller
     * @param callee
     * @param startDate
     * @param endDate
     * @return phone bill
     */
  @Override
  public PhoneBill addPhoneBill(String customer, String caller, String callee, Date startDate, Date endDate) {

    PhoneCall phoneCall= new PhoneCall();
    phoneCall.setCaller(caller);
    phoneCall.setCallee(callee);
    phoneCall.setStartDateTime(startDate);
    phoneCall.setEndDateTime(endDate);
    PhoneBill phoneBill;
    if(customerList.get(customer)!=null)
    {
        phoneBill=customerList.get(customer);
      phoneBill.pcCollection.add(phoneCall);
    }
    else
    {
      phoneBill=new PhoneBill();
      phoneBill.setCustomer(customer);
      phoneBill.pcCollection.add(phoneCall);
      customerList.put(customer,phoneBill);
    }
    return phoneBill;
  }
  @Override
  public void throwUndeclaredException() {
    throw new IllegalStateException("Expected undeclared exception");
  }

  @Override
  public void throwDeclaredException() throws IllegalStateException {
    throw new IllegalStateException("Expected declared exception");
  }
  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */

  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }

}


