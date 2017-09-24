/**
 * Performs the RSA encryption algorithm on the provided input string of ASCII
 * characters using the given public key <e,n> where n=pq
 * @author Joseph George
 */
public class rsaEncrypt {

	public static void main(String[] args) {
		
		// Check to make sure there are 3 command line arguments.
		if(args.length == 3){
			String cmdLineArg = args[0];
			int exponentE = Integer.parseInt(args[1]);
			long n = Integer.parseInt(args[2]);
			
			// Breaking string into characters
			Character[] chars = rsaEncrypt.characterArray(cmdLineArg);
			// Translate characters into string containing ASCII translations
			String ascii = rsaEncrypt.asciiCreator(chars);
			// Make the chunks that will be "M"
			int[] asciiChunks = rsaEncrypt.asciiSeparation(ascii);
			
			// Integer array result of calculating C
			String[] cHolder = new String[asciiChunks.length];
			for(int i = 0; i < cHolder.length; i++){
				cHolder[i] = rsaEncrypt.modExp(asciiChunks[i], exponentE, n);
			}
			
			String finalValue = "";
			// Convert the C calues into one big string
			for(int i = 0; i < cHolder.length; i++){
				finalValue = finalValue + String.valueOf(cHolder[i]);
			}
			
			System.out.println(finalValue);

		}
		else{ // Print error message and exit if there are not.
			System.out.println("ERROR: There is not an adequate amount of command line arguments.  The program takes in "
					+ "an input string that is delimited by quotation marks, a public key encryption exponent 'e', and "
					+ "a large number 'n', amounting to three total command line arguments.  Please rerun the program"
					+ "with correct specifications so you can perform the RSA encryption.");
			System.out.println("System now exiting...");
			System.out.println("Bye!");
			System.exit(1);
		}
		
	}
	
	/**
	 * Responsible for breaking the string argument into individual characters to be translated into ASCII.
	 * @param cmdString command line argument containing the specified string
	 * @return a character array of the command line string broken into characters
	 */
	public static Character[] characterArray(String cmdString){
		int length = cmdString.length();
		
		Character[] charArray = new Character[length];
		
		for(int i = 0; i < length; i++){
			charArray[i] = new Character(cmdString.charAt(i));
			
		}
		
		return charArray;
	}
	
	/**
	 * Takes a character type array and creates a string that is representative of the ASCII translation
	 * of the string in the command line argument.
	 * @param characterArr an array of characters from the command line string argument
	 * @return a string of the ASCII conversion of the characters to be encrypted
	 */
    public static String asciiCreator(Character[] characterArr){
        String asciiResult = "";
       
        for(int i = 0; i < characterArr.length; i++){
            char letter = characterArr[i];
            int ascii = (int)letter;
            String asciiString = Integer.toString(ascii); // Change ascii into a string
            int lengthAscii = asciiString.length(); // Used to check and see if the length of the ascii is less than required 3
           
            //Checks if the length is less than 3 of the ascii
            if(lengthAscii < 3){
            	
                // While it is less than 3, add zeros.
                while(lengthAscii < 3){
                    asciiString = 0 + asciiString;
                    lengthAscii++;
                    
                }
            }
            asciiResult = asciiResult + asciiString;
            
        }
        
        // Fill it with zeroes until it can be broken down into even chunks of 8!!
        while(asciiResult.length() % 8 != 0){
        	asciiResult = asciiResult + 0;
        	
        }
        return asciiResult; // Mod 8 of this should be zero to ensure optimal breaking up of digits
        
    }
	
    /**
     * Returns a string type array, each index containing a chunk of 8 ASCII integers.  If there are less than
     * 8 integers available, pads to the left with zeros!  
     * @param asciiChunk a long string of integers representative of ASCII characters
     * @return an integer array in which each indexs contains a chunk of 8 ASCII integers
     */
	public static int[] asciiSeparation(String asciiChunk){
		// Go about this creating substrings of large ascii integer conversion by 8
		String[] asciiArr = new String[asciiChunk.length()/8];

		int chunkLength = 8; // For last index to put in
		int indexTracker = 0; // For first index to put in
		for(int i = 0; i < asciiArr.length; i++){ 
			asciiArr[i] = asciiChunk.substring(indexTracker, chunkLength);
			chunkLength += 8;
			indexTracker +=8;
		} 
		
		// Now change the contents of the string array to integers.  Their true form is revealed. 
		int[] asciiArrInts = new int[asciiArr.length];
		for(int i =0; i < asciiArrInts.length; i++){
			int integer = Integer.parseInt(asciiArr[i]);
			asciiArrInts[i] = integer;
		}
		
		return asciiArrInts; 
	
	}
	
	/**
	 * Responsible for performing the actual encryption algorithm on string.  Produces the ciphertext from C = (M^e)mod(n) operation
	 * @param m the chunks of ASCII digits to encrypt with algorithm
	 * @param e public key encryption exponent
	 * @param n a large number n such that n = pq
	 * @return the ciphertext
	 */
	public static String modExp(int m, int e, long n){
		long c = 1;
		long power = m % n;
		String binaryRep = Integer.toBinaryString(e);
		
		// Start at the least significant index of the binary and work your way backwards.
		for(int i = (binaryRep.length() - 1); i >= 0; i--){ 
			char binaryChar = binaryRep.charAt(i); // Take the character at i'th value in the binary representation of e.
			
			if(binaryChar == '1'){ // If i'th least sig bit is 1
				c = (c*power) % n;
				
			}
			
			power = (power*power)% n;
		}
		
		
		String cString = String.valueOf(c);
		
		//Account for if the result is less than 9 digits.  Pad to left w/ zeros
		while(cString.length() < 9){
			cString = "0" + cString;
			
		}
		return cString;
		
	}
	
}




