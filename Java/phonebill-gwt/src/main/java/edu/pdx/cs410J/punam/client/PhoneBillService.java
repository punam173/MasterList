package edu.pdx.cs410J.punam.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;
import java.util.Date;

/**
 * A GWT remote service that returns Phone Bill with the call details for the given
 * customer
 * @author Punam Rani Pal
 */
@RemoteServiceRelativePath("phoneBill")
public interface PhoneBillService extends RemoteService {
  /**
   * This method gets a phone bill with all the calls
   * @param customer
   * @param startDate
   * @param endDate
   * @return phone bill
   */
  public PhoneBill getPhoneBill(String customer, Date startDate, Date endDate);

  /**
   * This method add a new call to the phone bill
   * @param customer
   * @param caller
   * @param callee
   * @param startDate
   * @param endDate
   * @return phone bill
   */
  public PhoneBill addPhoneBill(String customer, String caller, String callee, Date startDate, Date endDate);
  /**
   * Always throws an undeclared exception so that we can see GWT handles it.
   */
  void throwUndeclaredException();

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException() throws IllegalStateException;

}
