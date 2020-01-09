package edu.pdx.cs410J.punam.client;
import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.datepicker.client.DateBox;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This the GWT class that sends, receives and search the phone bill for a customer from the server and to teh server
 * @author Punam Rani Pal
 */
public class PhoneBillGwt implements EntryPoint {
  private final Alerter alerter; //holds teh alert message
  private PhoneBillServiceAsync phoneBillService; // get the phone bill service from the server
  private final Logger logger;
  private PhoneBill callBackPhonebill; //holds the phone bill information
  private Date startFinal;    //holds the stating date and time of the call
  private Date endFinal;      //holds teh end date and time of the call
    private static class PhoneCallDetails {
        private String caller ; //refers to the caller(customer) name
        private String callee ; //refers to the callee name(person who is being called)
        private Date startDateTime; //holds the start date and time
        private Date endDateTime;   //holds the End date and time
        private long duration;       //the holds the duration of the call made

        public PhoneCallDetails(String caller, String callee,Date startDateTime, Date endDateTime,long duration) {
            this.callee=callee;
            this.caller=caller;
            this.duration=duration;
            this.startDateTime=startDateTime;
            this.endDateTime=endDateTime;
        }
    }

    /**
     * this method set the alert message on teh window
     */
  public PhoneBillGwt() {
    this(new Alerter() {
      @Override
      public void alert(String message) {
        Window.alert(message);
      }
    });
  }

  @VisibleForTesting
  PhoneBillGwt(Alerter alerter) {
    this.alerter = alerter;
    this.phoneBillService = GWT.create(PhoneBillService.class);
    this.logger = Logger.getLogger("phoneBill");
    Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
  }

  private void alertOnException(Throwable throwable) {
    Throwable unwrapped = unwrapUmbrellaException(throwable);
    StringBuilder sb = new StringBuilder();
    sb.append(unwrapped.toString());
    sb.append('\n');

    for (StackTraceElement element : unwrapped.getStackTrace()) {
      sb.append("  at ");
      sb.append(element.toString());
      sb.append('\n');
    }

    this.alerter.alert(sb.toString());
  }

  private Throwable unwrapUmbrellaException(Throwable throwable) {
    if (throwable instanceof UmbrellaException) {
      UmbrellaException umbrella = (UmbrellaException) throwable;
      if (umbrella.getCauses().size() == 1) {
        return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
      }
    }
    return throwable;
  }
    /**
     * This method <code>createAddCallDialogBox</code> adds a box to get the details
     * @return dialogue Box
     */
    private DialogBox createAddCallDialogBox() {
        // Create a dialog box and set the caption text
        final DialogBox dialogBox = new DialogBox();
        dialogBox.ensureDebugId("cwDialogBox");
        dialogBox.setText("Add New Call Details  ");

        DateTimeFormat formatterFull = DateTimeFormat.getFormat("MM/dd/yyyy h:m a");
        DateTimeFormat formatter = DateTimeFormat.getFormat("MM/dd/yyyy");
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yyyy");

        DateBox startdateDB = new DateBox();   //holds the start date
        DateBox enddateDB = new DateBox();     //hols the end date

        TextBox customer= new TextBox();
        TextBox caller= new TextBox();
        TextBox callee= new TextBox();

        Button SaveButton;
        Button cancelButton;

        VerticalPanel panelV = new VerticalPanel();
        panelV.setBorderWidth(1);
        panelV.setWidth("400");

        //Start date time
        ListBox lb = new ListBox();
        ListBox lb1 = new ListBox();
        ListBox lb2 = new ListBox();

        //End date time
        ListBox eb = new ListBox();
        ListBox eb1 = new ListBox();
        ListBox eb2 = new ListBox();

        customer.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                String input = customer.getText();
                input=input.trim();
                if (!input.matches("[a-zA-Z0-9\\s]+")) {
                    return;
                }
            }
        });

        caller.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                String input = caller.getText();
                input=input.trim();
                if (!input.matches("\\d{3}[-]\\d{3}[-]\\d{4}")) {

                    return;
                }
            }
        });

        caller.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                String input = caller.getText();
                input=input.trim();
                if (!input.matches("\\d{3}[-]\\d{3}[-]\\d{4}")) {

                    return;
                }
            }
        });
           for(int i = 1; i < 13; ++i){
                lb.addItem(String.valueOf(i));
            }
            for(int i = 0; i < 60; ++i){
                lb1.addItem(String.valueOf(i));
            }

            lb2.addItem("am");
            lb2.addItem("pm");
        for(int i = 1; i < 13; ++i){
            eb.addItem(String.valueOf(i));
        }
        for(int i = 0; i < 60; ++i){
            eb1.addItem(String.valueOf(i));
        }
        eb2.addItem("am");
        eb2.addItem("pm");

        DateBox.DefaultFormat defaultFormat = new DateBox.DefaultFormat(dateTimeFormat);
        startdateDB.setFormat(defaultFormat);
        enddateDB.setFormat(defaultFormat);

        FlexTable layout = new FlexTable();
        layout.setCellSpacing(6);
        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        // Add a title to the phone bill table
        layout.setHTML(0, 0, "Add Call Details");
        cellFormatter.setColSpan(0, 0, 4);
        cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);

        // Add some standard form options
        layout.setHTML(1, 0, "Customer Name:");
        layout.setWidget(1, 1, customer);
        layout.setHTML(1, 4, "e.g David");
        layout.setHTML(2, 0, "Caller Number:");
        layout.setWidget(2, 1, caller);
        layout.setHTML(2,4, "e.g 503-234-4567");
        layout.setHTML(3, 0, "Callee Number:");
        layout.setWidget(3, 1, callee);
        layout.setHTML(3,4, "e.g 503-204-4867");
        layout.setHTML(4, 0, "Start Date Time:");
        layout.setWidget(4, 1, startdateDB);
        layout.setWidget(4, 2, lb);
        layout.setWidget(4,3, lb1);
        layout.setWidget(4, 4, lb2 );
        layout.setHTML(5, 0, "End Date Time:");
        layout.setWidget(5, 1, enddateDB);
        layout.setWidget(5, 2, eb);
        layout.setWidget(5,3, eb1);
        layout.setWidget(5, 4, eb2 );

        if (phoneBillService == null) {
            phoneBillService = GWT.create(PhoneBillService.class);
        }

        // Set up the callback object.
        AsyncCallback<PhoneBill> callback = new AsyncCallback<PhoneBill>() {
            public void onFailure(Throwable caught) {
                Window.alert("error"+caught.getMessage());
                // TODO: Do something with errors.
            }
            public void onSuccess(PhoneBill result) {
                updatePhoneBill(result);
            }
        };

        // Add a close button at the bottom of the dialogue
        SaveButton = new Button(
                "Save", new ClickHandler() {
                    //during saving validate the input
            public void onClick(ClickEvent event) {

                String customerName = customer.getText().trim();
                if(customerName.isEmpty())
                {
                    Window.alert("Please provide all the input correctly!!");
                    return ;
                }
                if (!customerName.matches("[a-zA-Z0-9\\s]+")) {
                    customer.setText("");
                    Window.alert("Only alphnumaric data with Spaces are allowed for name !!!");
                    return;
                }
                String callerInput = caller.getText().trim();
                if(callerInput.isEmpty())
                {
                    Window.alert("Please provide caller number!!");
                    return ;
                }

                if (!callerInput.matches("\\d{3}[-]\\d{3}[-]\\d{4}")) {
                    caller.setText("");
                    Window.alert("Caller phone number " + callerInput + " is not allowed. Allowed format is (123-234-4567)");
                    return;
                }

                String calleeInput = callee.getText().trim();
                if(calleeInput.isEmpty())
                {
                    Window.alert("Please provide callee number!!");
                    return ;
                }

                if (!calleeInput.matches("\\d{3}[-]\\d{3}[-]\\d{4}")) {
                    // show some error
                    callee.setText("");
                    Window.alert("Callee phone number " + callerInput + " is not allowed. Allowed format is (123-234-4567)");

                    return;
                }

                //concatenate date and time input
                Date startDate = startdateDB.getValue();
                String startDateString = null;
                try {
                    startDateString =  formatter.format(startDate)
                                        + " "
                                        + lb.getSelectedValue()
                                        + ":"
                                        + lb1.getSelectedValue()
                                        + " "
                                        + lb2.getSelectedValue();

                    startFinal = formatterFull.parse(startDateString);
                } catch (Exception e) {

                    Window.alert("Please select a start date from the date picker");
                    return;
                }

                Date endDate = enddateDB.getValue();
                String endDateString = null;
                try {
                    endDateString = formatter.format(endDate)
                        + " "
                        + eb.getSelectedValue()
                        + ":"
                        + eb1.getSelectedValue()
                        + " "
                        + eb2.getSelectedValue();

                    endFinal = formatterFull.parse(endDateString);
                } catch (Exception e) {
                    Window.alert("Please select an end date from the date picker");
                    return;
                }

               if(startFinal.after(endFinal)) {
                   Window.alert("Start should be less then end date!!");
                   return ;
               }
                   phoneBillService.addPhoneBill(customer.getText(),caller.getText(),callee.getText(),
                            startFinal,endFinal,callback);

                Window.alert("Inserted Sucessfully!! ");

                  customer.setText("");
                  caller.setText("");
                  callee.setText("");
                  startdateDB.setValue(null);
                  enddateDB.setValue(null);
                 dialogBox.hide();
            }
        });

         cancelButton = new Button(
                "Cancel", new ClickHandler() {
            public void onClick(ClickEvent event) {
                customer.setText("");
                caller.setText("");
                callee.setText("");
                startdateDB.setValue(null);
                enddateDB.setValue(null);
                dialogBox.hide();

            }});
        layout.setWidget(6, 0, SaveButton);
        layout.setWidget(6, 2, cancelButton);

        cellFormatter.setColSpan(6, 0, 2);
        cellFormatter.setHorizontalAlignment(
                6, 0, HasHorizontalAlignment.ALIGN_CENTER);
        cellFormatter.setColSpan(6, 2, 2);
        cellFormatter.setHorizontalAlignment(
                6, 1, HasHorizontalAlignment.ALIGN_CENTER);

        dialogBox.setWidget(layout);

        return dialogBox;
    }

    /**
     * This method <code>ShowTableDialogue</code> shows the customer phone bill
     * in a table formate, flex widget have been for the layout
     * @param phoneCallDetails
     * @param custName
     * @return table
     */
    private DialogBox ShowTableDialog(List<PhoneCallDetails> phoneCallDetails,String custName ){
        final DialogBox dialogBox = new DialogBox();
        dialogBox.ensureDebugId("cwDialogBox");
        dialogBox.setText("Call Details  ");
        Button okButton;
        FlexTable layout = new FlexTable();
        layout.setCellSpacing(6);

        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        CellTable<PhoneCallDetails> table = new CellTable<PhoneCallDetails>();
        table.setKeyboardSelectionPolicy(HasKeyboardSelectionPolicy.KeyboardSelectionPolicy.ENABLED);

        // Add a text column to show the name.
        TextColumn<PhoneCallDetails> callerColumn =new TextColumn<PhoneCallDetails>() {
            @Override
            public String getValue(PhoneCallDetails object) {
                return object.caller;
            }
        };
        table.addColumn(callerColumn, "Caller");

        TextColumn<PhoneCallDetails> calleeColumn =new TextColumn<PhoneCallDetails>() {
            @Override
            public String getValue(PhoneCallDetails object) {
                return object.callee;
            }
        };
        table.addColumn(calleeColumn, "Callee");

        DateTimeFormat formatter = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");

        // Add a startdateColumn column
        TextColumn<PhoneCallDetails> startdateColumn =new TextColumn<PhoneCallDetails>() {
            @Override
            public String getValue(PhoneCallDetails object) {
                if(object.startDateTime!=null){
                    return formatter.format(object.startDateTime);
                }
                else return "";

            }
        };
        table.addColumn(startdateColumn, "Start Date");

        // Add an enddateColumn column
        TextColumn<PhoneCallDetails> enddateColumn =
                new TextColumn<PhoneCallDetails>() {
                    @Override
                    public String getValue(PhoneCallDetails object) {
                        if(object.endDateTime!=null){

                            return formatter.format(object.endDateTime);
                        }
                        else return "";
                    }
                };

        table.addColumn(enddateColumn, "End Date");

        // Add  durationColumn
        TextColumn<PhoneCallDetails> durationColumn = new TextColumn<PhoneCallDetails>() {
            @Override
            public String getValue(PhoneCallDetails object) {
                return object.duration > 0 ? String.valueOf(object.duration) : "";
            }
        };

        table.addColumn(durationColumn, "Duration (mins)");
        table.setVisible(true);
        table.flush();

        table.setRowCount(phoneCallDetails.size(), true);
        table.setRowData(0, phoneCallDetails);

        layout.setWidget(0, 0, new Label("Hello "+ custName.toUpperCase()+"! You currently have "+ phoneCallDetails.size()+" calls logged on your bill."));
        cellFormatter.setColSpan(0, 0, 3);
        cellFormatter.setHorizontalAlignment(
                0, 0, HasHorizontalAlignment.ALIGN_CENTER);

        layout.setWidget(1, 0, new Label("Here's a list of all your calls:"));
        cellFormatter.setColSpan(1, 0, 3);
        cellFormatter.setHorizontalAlignment(
                1, 0, HasHorizontalAlignment.ALIGN_CENTER);
        phoneCallDetails.clear();


        layout.setWidget(2, 0, table);
        cellFormatter.setColSpan(2, 0, 3);
        cellFormatter.setHorizontalAlignment(
                2, 0, HasHorizontalAlignment.ALIGN_CENTER);

        okButton = new Button(
                "Ok", new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });

        layout.setWidget(3, 0, okButton);
        cellFormatter.setColSpan(3, 0, 3);
        cellFormatter.setHorizontalAlignment( 3, 0, HasHorizontalAlignment.ALIGN_CENTER);
        dialogBox.setWidget(layout);
        dialogBox.show();
        dialogBox.center();
        return  dialogBox;

    }

    /**
     * This method <code>showPhoneBillTable</code> displaya a dialogue bos
     * to take the customer name to show the phonebill
     * @return dialogue box
     */
   private DialogBox showPhoneBillTable()
   {
       final DialogBox dialogBox = new DialogBox();
       dialogBox.ensureDebugId("cwDialogBox");
       dialogBox.setText("Call Details  ");
       //hols the call details
       List<PhoneCallDetails> phoneCallDetails = new ArrayList<PhoneCallDetails>();
       TextBox  customerName=new TextBox();
       Button cancelButton= new Button("Cancel");
       Button search;
       FlexTable layout = new FlexTable();
       layout.setCellSpacing(6);
       FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

       if (phoneBillService == null) {
           phoneBillService = GWT.create(PhoneBillService.class);
       }
       // Set up the callback object.
       AsyncCallback<PhoneBill> callback = new AsyncCallback<PhoneBill>() {
           public void onFailure(Throwable caught) {
               Window.alert(caught.getMessage());
               // TODO: Do something with errors.
           }
           //if the customer does not exist gives an alert message otherwise display the table
           public void onSuccess(PhoneBill result) {
               if(result.getCustomer().contains("Customer Not found")){
                   Window.alert("Customer Not found");
                   customerName.setText("");
                   return;}
               else {
                   updatePhoneBill(result);

                   if(callBackPhonebill!=null) {

                       ArrayList<PhoneCall> tempPhoneC = callBackPhonebill.getPhoneCalls();
                       if (tempPhoneC != null) {
                           long duration =0;
                           for (PhoneCall call : tempPhoneC) {
                               // Check Date is not null to calculate duration
                               if(call.getEndTime()!=null && call.getStartTime()!=null)
                               {
                                   duration = TimeUnit.MILLISECONDS.toMinutes(call.getEndTime().getTime() - call.getStartTime().getTime());
                               }

                               phoneCallDetails.add(new PhoneCallDetails(call.getCaller(),
                                       call.getCallee(), call.getStartTime(), call.getEndTime(), duration));
                           }
                           if(!callBackPhonebill.getCustomer().contains("Customer Not found")){
                               DialogBox abc=ShowTableDialog(phoneCallDetails,callBackPhonebill.getCustomer());
                               abc.center();
                               abc.show();
                               customerName.setText("");
                           }
                       }
                   }
               }
           }
       };
       //to serach a phone bill
       search=new Button("Search");
       search.setFocus(true);
       search.addClickHandler(new ClickHandler() {
           @Override
           public void onClick(ClickEvent clickEvent) {  //if there is no input
               if(customerName.getText().isEmpty())
               {
                   Window.alert("Please provide your Input!!");
                   return ;
               }

               //clickEvent.notify();
               //phoneCallDetails.clear();
               phoneBillService.getPhoneBill(customerName.getText(), null, null, callback);



           }
       });
       cancelButton.addClickHandler(new ClickHandler() {
       @Override
       public void onClick(ClickEvent clickEvent) {

           customerName.setText("");
           dialogBox.hide();
       }
   });
       layout.setHTML(0, 0, "Enter Customer  Name");
       layout.setWidget(0, 1, customerName);
       layout.setWidget(0, 2, search);
       layout.setWidget(0, 3, cancelButton);
       dialogBox.setWidget(layout);
       return dialogBox;
   }

    /**
     * This method <code>showPhoneBillTableByDate</code> shows phone bill for searching
     * calls by date
     * @return dialogue box
     */
    private DialogBox showPhoneBillTableByDate()
    {
        final DialogBox dialogBox = new DialogBox();
        dialogBox.ensureDebugId("cwDialogBox");
        dialogBox.setText("Call Details  ");

        List<PhoneCallDetails> phoneCallDetails = new ArrayList<PhoneCallDetails>(); //hold the call details
        TextBox customerName=new TextBox();
        DateTimeFormat formatterFull = DateTimeFormat.getFormat("MM/dd/yyyy h:m a");
        DateTimeFormat formatter = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm a");
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("MM/dd/yyyy");

        DateBox startdateDB = new DateBox();
        DateBox enddateDB = new DateBox();
        DateBox.DefaultFormat defaultFormat = new DateBox.DefaultFormat(dateTimeFormat);
        startdateDB.setFormat(defaultFormat);
        enddateDB.setFormat(defaultFormat);

        //Start date time
        ListBox lb = new ListBox();
        ListBox lb1 = new ListBox();
        ListBox lb2 = new ListBox();

        //End date time
        ListBox eb = new ListBox();
        ListBox eb1 = new ListBox();
        ListBox eb2 = new ListBox();

        for(int i = 1; i < 13; ++i){
            lb.addItem(String.valueOf(i));
        }
        for(int i = 0; i < 60; ++i){
            lb1.addItem(String.valueOf(i));
        }
        lb2.addItem("am");
        lb2.addItem("pm");
        for(int i = 1; i < 13; ++i){
            eb.addItem(String.valueOf(i));
        }
        for(int i = 0; i < 60; ++i){
            eb1.addItem(String.valueOf(i));
        }
        eb2.addItem("am");
        eb2.addItem("pm");
        Button CancelButton;
        Button search;
        FlexTable layout = new FlexTable();
        layout.setCellSpacing(6);
        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
        if (phoneBillService == null) {
            phoneBillService = GWT.create(PhoneBillService.class);
        }
        // Set up the callback object.
        AsyncCallback<PhoneBill> callback = new AsyncCallback<PhoneBill>() {
            public void onFailure(Throwable caught) {
                Window.alert(caught.getMessage());
                // TODO: Do something with errors.
            }
            public void onSuccess(PhoneBill result) {
                if(result.getPhoneCalls().size() ==0)
                {
                    Window.alert("Hello "+ result.getCustomer().toUpperCase() +"! You have made no calls between the given date/time!!!");
                    return;
                }

                updatePhoneBill(result);
                if(callBackPhonebill!=null) {
                    ArrayList<PhoneCall> tempPhoneC = callBackPhonebill.getPhoneCalls();  //holds teh phone call back from the server
                    if (tempPhoneC != null) {
                        long duration =0;
                        for (PhoneCall call : tempPhoneC) {
                            // Check Date is not null to calculate duration
                            if(call.getEndTime()!=null && call.getStartTime()!=null)
                            {
                                duration = TimeUnit.MILLISECONDS.toMinutes(call.getEndTime().getTime() - call.getStartTime().getTime());
                            }

                            phoneCallDetails.add(new PhoneCallDetails(call.getCaller(),
                                    call.getCallee(), call.getStartTime(), call.getEndTime(), duration));
                        }

                        if(!callBackPhonebill.getCustomer().contains("Customer Not found")){
                            DialogBox abc=ShowTableDialog(phoneCallDetails ,callBackPhonebill.getCustomer());
                            abc.center();
                            abc.show();
                            phoneCallDetails.clear();
                            //customerName.setText("");
                        }
                        else
                        {
                            Window.alert("Customer Not found!!");
                        }
                    }

                }
            }
        };
        //on clicking serch validation and display happens
        search=new Button("Search");
        search.setFocus(true);
        search.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent clickEvent) {

                if(customerName.getText().isEmpty())
                {
                    Window.alert("Please provide all the Inputs!!");
                    return ;
                }
                Date startDate = startdateDB.getValue();
                String startDateString = null;
                try {
                    startDateString =  dateTimeFormat.format(startDate)
                            + " "
                            + lb.getSelectedValue()
                            + ":"
                            + lb1.getSelectedValue()
                            + " "
                            + lb2.getSelectedValue();

                    startFinal = formatterFull.parse(startDateString);
                } catch (Exception e) {
                    Window.alert("Please select a start date from the date picker");
                    return;

                }

                Date endDate = enddateDB.getValue();
                String endDateString = null;
                try {
                    endDateString = dateTimeFormat.format(endDate)
                            + " "
                            + eb.getSelectedValue()
                            + ":"
                            + eb1.getSelectedValue()
                            + " "
                            + eb2.getSelectedValue();

                    endFinal = formatterFull.parse(endDateString);
                } catch (Exception e) {
                    Window.alert("Please select an end date from the date picker");
                    return;
                }

                if(startFinal.after(endFinal)) {
                    Window.alert("Start should be less then end date!!");
                    return ;
                }

                 //Window.alert(startFinal.toString()+"###"+endFinal.toString());
                phoneBillService.getPhoneBill(customerName.getText(), startFinal, endFinal, callback);


            }
        });
        //setting up teh format for table
        layout.setHTML(0, 0, "Enter Customer  Name");
        layout.setWidget(0, 1, customerName);

        layout.setHTML(1, 0, "Start Date Time:");
        layout.setWidget(1, 1, startdateDB);

        layout.setWidget(1, 2, lb);
        layout.setWidget(1,3, lb1);
        layout.setWidget(1, 4, lb2 );
        layout.setHTML(2, 0, "End Date Time:");
        layout.setWidget(2, 1, enddateDB);
        layout.setWidget(2, 2, eb);
        layout.setWidget(2,3, eb1);
        layout.setWidget(2, 4, eb2 );
        layout.setWidget(3, 0, search);
        cellFormatter.setColSpan(3, 0, 2);
        cellFormatter.setHorizontalAlignment(
                3, 0, HasHorizontalAlignment.ALIGN_CENTER);

        CancelButton = new Button(
                "Cancel", new ClickHandler() {
            public void onClick(ClickEvent event) {
                dialogBox.hide();
            }
        });

        layout.setWidget(3, 2,CancelButton);
        cellFormatter.setColSpan(3, 2, 2);
        cellFormatter.setHorizontalAlignment( 3, 2, HasHorizontalAlignment.ALIGN_CENTER);
        dialogBox.setWidget(layout);


        return dialogBox;
    }

    /**
     * This method <code>updatePhoneBill</code> get the phone bill from the server
     * @param tem
     */
    private void updatePhoneBill( PhoneBill tem)
   {
       callBackPhonebill=tem;
   }

    /**
     * This method <code>addWidgets</code> lays the user interface layouts
     * vertical and horizontal, menu bar , dialogue boxes have been added
     * @param panel
     */
  private void addWidgets(VerticalPanel panel) {

      final DialogBox dialogBox = createAddCallDialogBox();
      dialogBox.setGlassEnabled(true);
      dialogBox.setAnimationEnabled(true);

      final DialogBox showPhoneBillTableByDatedialogBox = showPhoneBillTableByDate();
      showPhoneBillTableByDatedialogBox.setGlassEnabled(true);
      showPhoneBillTableByDatedialogBox.setAnimationEnabled(true);

      final DialogBox dialogBoxShowPhone= showPhoneBillTable();
      dialogBoxShowPhone.setGlassEnabled(true);
      dialogBoxShowPhone.setAnimationEnabled(true);
      HorizontalPanel hPanel=new HorizontalPanel();
      hPanel.setSpacing(5);

      Command cmd = new Command() {
          public void execute() {
              String str="This interface provides the phone bill to the customer" +
                      "Customr actions have three options:\n" +
                      "1) Add new phone bill: It allows user to add new call by giving following details.\n" +
                      "*************************************\n"+
                      "Customer Name: Name of the customer to whom phobe bill belongs"+
                      "callerNumber: Phone number of caller\n" +
                      "calleeNumber: Phone number of person who was called\n" +
                      "startTime Date and time call began\n" +
                      "endTime Date and time call ended\n" +
                      "*************************************\n"+
                      "2) Show phone bill : It allows to customer to see their phone bill by giving following\n"+
                      "*************************************\n"+
                      "Customer Name : Name of the customer to whom phobe bill belongs\n"+
                      "*************************************\n"+
                      "3) Search phone bill by date: It allowa to search calls between the given date:\n"+
                      "*************************************\n"+
                      "Customer Name: Name of the customer to whom phobe bill belongs"+
                      "startTime Date and time call began\n" +
                      "endTime Date and time call ended\n" +
                      "*************************************\n"+
                      "Help has one option README that gives the information about the interface\n";

              Window.alert(str);
          }
      };
      Command cmdAddCall = new Command() {
          public void execute() {
              dialogBox.center();
              dialogBox.show();
          }
      };
      Command cmdShowCustomer = new Command() {
          public void execute() {
              dialogBoxShowPhone.center();
              dialogBoxShowPhone.show();

          }
      };

      Command cmdsearchCustomer = new Command() {
          public void execute() {
              showPhoneBillTableByDatedialogBox.center();
              showPhoneBillTableByDatedialogBox.show();
          }
      };
      MenuBar helMenu = new MenuBar();
      helMenu.addItem("README", cmd);
      MenuBar actions = new MenuBar();
      actions.addItem("Add Phone Call", cmdAddCall);
      actions.addItem("Show Customer Phone Bill", cmdShowCustomer);
      actions.addItem("Search Phone Bill By Date", cmdsearchCustomer);

      // Make a new menu bar, adding a few cascading menus to it.
      MenuBar menu = new MenuBar();
      menu.addItem("Customer Actions", actions);
      menu.addItem("Help", helMenu);
      hPanel.add(menu);
      panel.add(hPanel);


  }
  private void throwClientSideException() {
    logger.info("About to throw a client-side exception");
    throw new IllegalStateException("Expected exception on the client side");
  }
  private void showUndeclaredException() {
    logger.info("Calling throwUndeclaredException");
    phoneBillService.throwUndeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }
  private void showDeclaredException() {
    logger.info("Calling throwDeclaredException");
    phoneBillService.throwDeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }
  @Override
  public void onModuleLoad() {
    setUpUncaughtExceptionHandler();

    // The UncaughtExceptionHandler won't catch exceptions during module load
    // So, you have to set up the UI after module load...
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        setupUI();
      }
    });
  }
  private void setupUI() {
    RootPanel rootPanel = RootPanel.get();
    VerticalPanel panel = new VerticalPanel();
    rootPanel.add(panel);
    addWidgets(panel);
  }

  private void setUpUncaughtExceptionHandler() {
      GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable throwable) {
        alertOnException(throwable);
      }
    });
  }
  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }

}
