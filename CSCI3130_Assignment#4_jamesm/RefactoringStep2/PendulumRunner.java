import java.text.NumberFormat;

/**
 * compares the values of a simple pendulum using the harmonic motion equation
 * versus the Euler algorithm approximation
 */
public class PendulumRunner {
   public static void main (String [] args) {
	     NumberFormat nf = NumberFormat.getInstance ();
	     nf.setMaximumFractionDigits (3);
        GravityModel g = new GravityConstant(9.80655);
        double delta = (args.length == 0) ? .1 : Double.parseDouble (args[0]);
	     double sLen = 10, pMass = 10, theta0 = Math.PI/30;
	     RegularPendulum rp = new RegularPendulum (sLen, pMass, theta0, delta, g);
	     SimplePendulum sp = new SimplePendulum (sLen, pMass, theta0, g);
	     RegularPendulum rpCoarse = new RegularPendulum (sLen, pMass, theta0, .1, g);

	     // print out difference in displacement in 1 second intervals
	     // for 20 seconds
	     int iterations = (int) (1/delta);
	     System.out.println ("analytical vs. numerical displacement (fine, coarse)");
	     for (int second = 1; second <= 20; second++) {
	          for (int i = 0; i < iterations; i++) rp.step ();
	          for (int i = 0; i < 10; i++) rpCoarse.step ();
	          System.out.println ("t=" + second + "s: \t" +
				        nf.format (Math.toDegrees (sp.getTheta (second))) + "\t" +
				        nf.format (Math.toDegrees (rp.getLastTheta ()))+ "\t" +
				        nf.format (Math.toDegrees (rpCoarse.getLastTheta ())));
        }
        //Added gravity change
        //**********
        //I didn't know if I was supposed to change the GravityConstant mid-swinging or do to different swings with two different constants
        //I choose to do the latter as seen below.
        System.out.println("\nChanging Gravity Constant Now.");
        GravityModel g2 = new GravityConstant(25);
        sp.setGravitationalField(g2);
        rp.setGravitationalField(g2);
        rpCoarse.setGravitationalField(g2);
        for (int second = 1; second <= 20; second++) {
	          for (int i = 0; i < iterations; i++) rp.step ();
	          for (int i = 0; i < 10; i++) rpCoarse.step ();
	          System.out.println ("t=" + second + "s: \t" +
				        nf.format (Math.toDegrees (sp.getTheta (second))) + "\t" +
				        nf.format (Math.toDegrees (rp.getLastTheta ()))+ "\t" +
				        nf.format (Math.toDegrees (rpCoarse.getLastTheta ())));
        }
    }
}