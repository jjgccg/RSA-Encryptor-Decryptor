import java.util.ArrayList;

/**
 * Performs Euclidean algorithm on two provided integers
 * 
 * @author Joseph George
 *
 */

public class gcd {
	
	public static void main(String[] args){
		if(args.length == 2){ //Check to make sure there are two command line arguments
			int x = Integer.parseInt(args[0]);
			int y = Integer.parseInt(args[1]);
			
			//Get u and v parameters
			int[] xRes = gcd.euclideanAlgorithm(x, y);
			
			int u = xRes[0];
			int v = xRes[1];
			
			//Return the gcd statement
			System.out.println("gcd(" + x + "," + y + ")" + " = " + (v*x + u*y) + " = " + v+"("+x+") " + "+ " +u+"("+y+")");
			
			//Get the "d" AKA private key decryption exponent
			int d = v;
			if(v < 0){
				v = v+y;
				d = v;
			}

			System.out.println("Your private key decryption exponent is " + d);
		}
		else{ //Print an error statement if there are not two command line arguments
			System.out.println("ERROR: There is not an adequate amount of command line arguments.  The program takes in "
					+ "two input integers, the first being a public key encryption exponent e and the second being "
					+ "a number n such that n = (e)mod(p-1)(q-1) where p and q are provided prime numbers.  These numbers"
					+ "are necessary to perform the Euclidean Algorithm and get your private key decryption exponent.");
			System.out.println("System now exiting...");
			System.out.println("Bye!");
			System.exit(1);
		}
		
	}
	
	
	public static int[] euclideanAlgorithm(int m, int n){
		// These two variables are simply used to hold the original m and n values
		int mIn = m;
		int nIn = n;
		
		int r;
		if(m == 0 || n == 0){
			r = -1;
		}
		
		//Make array lists to hold values of u and v
		ArrayList<Integer> uHolder = new ArrayList<Integer>(); 
		ArrayList<Integer> vHolder = new ArrayList<Integer>(); 
		
		int k = 0;
		int u = 0;
		int v = 0;
		
		int q;
		r = n%m;
		
		// These values are the initial values discussed in class.
		int uNeg1 = 1;
		int vNeg1 = 0;
		int u0 = 0;
		int v0 = 1;
		
		//Before beginning the while loop, add the initial values of u and v to the array list.
		uHolder.add(uNeg1);
		uHolder.add(u0);
		vHolder.add(vNeg1);
		vHolder.add(v0);
		
		while(r != 0){ //Execution will end when r = 0.
			
			q = (n-n%m)/m;
			
			//For each k'th step, you will remove the previous u value, and add in the next one based off of the formulaic calculation
			u = uHolder.get(0)-q*uHolder.get(1);
			uHolder.remove(0);
			uHolder.add(u);
			
			v = vHolder.get(0)-q*vHolder.get(1);
			vHolder.remove(0);
			vHolder.add(v);
			
			//Update n, m, and r so your loop doesn't go on infinitely
			n=m;
			m=r;
			r = n%m;
			
			k++;
			
		}

		// These will be your "u" and "v" parameters
		int uFinal = uHolder.get(1);
		int vFinal = vHolder.get(1);
		
		int[] uvArr = new int[2];
		uvArr[0] = uFinal;
		uvArr[1] = vFinal;
				
		return uvArr;
		
	}
	
	
	
}
