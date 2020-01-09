package edu.pdx.cs410J.punam.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Date;

/**
 * The client-side interface to the phone bill service
 * that interact with the remote server and get the appropriate actions
 * @author Punam Rani Pal
 */
public interface PhoneBillServiceAsync {

  /**
   * This method get the details from the client side and get the information from
   * the server
   * @param customer
   * @param startDate
   * @param endDate
   * @param phoneBill
   */
  void getPhoneBill(String customer,Date startDate ,Date endDate,AsyncCallback<PhoneBill> phoneBill);

  /**
   * This method add a details to the phone bill in the remote server
   * @param customer
   * @param caller
   * @param callee
   * @param startDate
   * @param endDate
   * @param phoneBill
   */
  void addPhoneBill(String customer, String caller, String callee, Date startDate, Date endDate,AsyncCallback<PhoneBill> phoneBill);

  /**
   * Always throws an exception so that we can see how to handle uncaught
   * exceptions in GWT.
   */
  void throwUndeclaredException(AsyncCallback<Void> async);

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException(AsyncCallback<Void> async);

}
