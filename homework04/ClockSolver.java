/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  File name     :  ClockSolver.java
 *  Purpose       :  The main program for the ClockSolver class
 *  @see
 *  @author       :  B.J. Johnson
 *  Date written  :  2017-02-28
 *  Description   :  This class provides a bunch of methods which may be useful for the ClockSolver class
 *                   for Homework 4, part 1.  Includes the following:
  *
 *  Notes         :  None right now.  I'll add some as they occur.
 *  Warnings      :  None
 *  Exceptions    :  IllegalArgumentException when the input arguments are "hinky"
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *  Revision Histor
 *  ---------------
 *            Rev      Date     Modified by:  Reason for change/modification
 *           -----  ----------  ------------  -----------------------------------------------------------
 *  @version 1.0.0  2017-02-28  B.J. Johnson  Initial writing and release
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

import java.io.*;

public class ClockSolver {
  /**
   *  Class field definintions go here
   */
   private final double MAX_TIME_SLICE_IN_SECONDS  = 1800.00;
   private final double DEFAULT_TIME_SLICE_SECONDS = 60.0;
   private final double EPSILON_VALUE              = 0.1;      // small value for double-precision comparisons

   private static double inputtedAngle;
   private static double timeSlice;

  /**
   *  Constructor
   *  This just calls the superclass constructor, which is "Object"
   */
   public ClockSolver() {
      super();
   }

  /**
   *  Method to handle all the input arguments from the command line
   *   this sets up the variables for the simulation
   */
   public void handleInitialArguments( String args[] ) {
     // args[0] specifies the angle for which you are looking
     //  your simulation will find all the angles in the 12-hour day at which those angles occur
     // args[1] if present will specify a time slice value; if not present, defaults to 60 seconds
     // you may want to consider using args[2] for an "angle window"

      System.out.println( "Welcome to ClockSolver\n\n" ) ;
      if( 0 == args.length ) {
         System.out.println( "   Sorry you must enter at least one argument\n" +
                             "   Usage: java ClockSolver <angle> [timeSlice]\n" +
                             "   Please try again..........." );
         System.exit( 1 );
      }

      Clock clock = new Clock();

      try {
        inputtedAngle = clock.validateAngleArg(args[0]);
        timeSlice = clock.validateTimeSliceArg((args.length == 2) ? args[1] : Double.toString(DEFAULT_TIME_SLICE_SECONDS));
      } catch (Exception error) {
        System.out.println(String.format("handleInitialArguments error: %s", error.toString()));
        System.exit(1);
      }
   }

  /**
   *  The main program starts here
   *  remember the constraints from the project description
   *  @see  http://bjohnson.lmu.build/cmsi186web/homework04.html
   *  @param  args  String array of the arguments from the command line
   *                args[0] is the angle for which we are looking
   *                args[1] is the time slice; this is optional and defaults to 60 seconds
   */
   public static void main(String args[]) {
     ClockSolver cse = new ClockSolver();
     Clock clock = new Clock();
     double time = 0.0;
     cse.handleInitialArguments(args);

     try {
       FileWriter ostream = new FileWriter("output.txt");
       BufferedWriter out = new BufferedWriter(ostream);

       System.out.println( "Results\n\n" ) ;

       while(true) {
          if (clock.getTotalSeconds() >= 43200) break;

          time = clock.tick(timeSlice);

          if (clock.getHoursAngle() > clock.getMinutesAngle() || clock.getHoursAngle() < clock.getMinutesAngle()) {
            inputtedAngle = clock.getHoursAngle() - clock.getMinutesAngle();
          }

          System.out.println(clock.toString());
          out.write(clock.toString());
       }
       out.close();
       System.exit(1);
     } catch(Exception error) {
       System.out.println(String.format("output to new file error: %s", error.toString()));
     }

   }
}
