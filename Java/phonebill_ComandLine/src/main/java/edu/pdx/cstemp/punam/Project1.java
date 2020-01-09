package edu.pdx.cstemp.punam;

import java.text.ParseException;

/**
 * The main class for the CS410J PhoneBill Project <code>Project1</code>
 * @author Punam Rani Pal
 */
public class Project1 {
  /**
   * This method is the main method where the execution for the program begins
   * its parse the command line arguments and results in the desired output
   * <code>main</code>
   */
  public static void main(String[] args) throws ParseException {

    boolean isPrint = false; //refers to -print option presence/absence
    boolean isReadMe = false; //refers to -Readme option presence/absence
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    int i = 0; //keeps track of the number of argument
    int flag = 0;//keeps tack of the position of the -print option
    if (args.length == 0) {
      System.out.println("No arguments provided!!");
      Validate.printUsage();
      System.exit(1);
    }
    if (args.length == 10){
      System.out.println("Invalid number of arguments provided!!");
      Validate.printUsage();
      System.exit(1);
    }
    if (args.length == 1) {
      if (Validate.isOptionCorrect(args[0])) {
        if (Validate.isOptionReadME(args[0]))
          Validate.printReadMe();
        else {
          Validate.validateArgs(args);
          Validate.printUsage();
        }
      }else
        System.out.println("Invalid option!");
      System.exit(1);
    }
    if (args.length < 8) {
     // if (Validate.isOptionCorrect(args)) {
        if (Validate.isOptionReadME(args[0]) || Validate.isOptionPrint(args[1])) {
          if (!Validate.isOptionReadME(args[0]))
            System.out.println("Inavlid option for README");
          else {
            Validate.printReadMe();
            System.exit(1);
          }
          if(!Validate.isOptionPrint(args[1]))
             System.out.println("Invalid option for print");
          else{
            if (!Validate.validateArgs(args))
              Validate.printUsage();
          }
        } else if(Validate.isOptionReadME(args[1]) || Validate.isOptionPrint(args[0])) {
          if (!Validate.isOptionPrint(args[0]))
            System.out.println("Invalid option input for print");
          else {
            if (!Validate.validateArgs(args))
              Validate.printUsage();
          }
          if (Validate.isOptionReadME(args[1]))
            Validate.printReadMe();
          else {
                 System.out.println("Invalid option for README");
          }
        } else{
            if (!args[0].startsWith("-") && !args[1].startsWith("-"))
                Validate.validateArgs(args);
            else
              System.out.println("Invalid options!!");
        }

        System.exit(1);
    }

    if (args.length == 8) {
      //i += 1; //refers to the first position(-print/README)
      if (Validate.isOptionPrint(args[0])) {
        isPrint = true;
        i += 1;
        //Validate.validateArgs(args);
      } else {
        if (Validate.isOptionPrint(args[1])) {
          isPrint = true;
          flag = 1;
          i += 2;
          Validate.validateArgs(args);
        }
      }
      if (Validate.isOptionReadME(args[0])) { //if readme option is ON
        isReadMe = true;
        Validate.printReadMe();
        System.exit(1);
      } else {
        if (Validate.isOptionReadME(args[1])) {
          isReadMe = true;
        }
      }
      if(!Validate.isOptionCorrect(args[0]) && (!Validate.isOptionCorrect(args[1])))
        System.out.println("Invalid option");
    }

    if (args.length == 9) {  //when all the arguments are entered (9)
      if (Validate.isOptionPrint(args[0])) { //if -print option is the first argument
        isPrint = true;
      } else {
        if (Validate.isOptionPrint(args[1])) { //if -print option is the 2nd argument
          flag = 1;
          isPrint = true;
        }
      }
      if (Validate.isOptionReadME(args[0]) || Validate.isOptionReadME(args[1])) { //if readme option is ON
        isReadMe = true;
      }else{
        if( !isPrint && !isReadMe)
          System.out.println("Invalid options for print and README!!");
      }
      i += 2; //sets the first two arguments positions for the options
    }

      //if there are 9 arguments
    if (isPrint && flag == 0) {  //if print option is the first argument then validate arguments and switch to readme
      if (!Validate.validateArgs(args)) {
          Validate.printUsage();
          if (isReadMe) {
            Validate.printReadMe();
          }
          System.exit(1);
      }
        //sets the argument based on the position od the 9 arguments
        call.setCaller(args[1 + i]);
        call.setCallee(args[2 + i]);
        call.setStartDateTime(args[3 + i] + " " + args[4 + i]);
        call.setEndDateTime(args[5 + i] + " " + args[6 + i]);
        PhoneBill bill = new PhoneBill(); //creates a new phonebill for the customer
        bill.setCustomer(args[0 + i]); //sets the customer name from the argument on the basis of number of arguments presents.
        bill.addPhoneCall(call); //add the new call information to the phonebill collection
        //if all the arguments are correct and if print is the first one then -print prints out first
        //then swtch to readme else just stwitch to README.
        if (isPrint) {
          for (PhoneCall pc : bill.getPhoneCalls()) {
            System.out.println(pc);
          }
          System.out.println(bill);
          if (isReadMe) {
            Validate.printReadMe();
            System.exit(1);
          }else
            System.out.println("Invalid -README option!!");
        }

      }
      if (isReadMe && (flag == 1 || flag == 0)) {
        if(flag == 0){
          if(!Validate.isOptionPrint(args[0]))
             System.out.println("Inavlid print option");
        }
        Validate.printReadMe();
      }

      System.exit(1);
    }
  }

