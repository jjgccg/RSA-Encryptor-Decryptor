/**
 * Performs the RSA decryption algorithm on the provided input string of ASCII
 * characters using the given public key <d,n> where n=pq.  This program reverses the encryption process
 * from rsaEncypt.
 * @author Joseph George
 */
public class rsaDecrypt {

	public static void main(String[] args) {
		//Check to make sure there are an adequate amount of command line arguments.
		if(args.length == 3){
			//Separating the command line arguments
			String digitsToDecode = args[0]; //Output from rsaEncrypt
			int decryptionD = Integer.parseInt(args[1]);
			long n = Integer.parseInt(args[2]);
			
			//Break digitsToDecode into chunks of 9
			String[] chunks = nineBlocks(digitsToDecode);
			
			//Perform the decryption algorithm
			String[] decryptedM = new String[chunks.length];
			for(int i = 0; i < decryptedM.length; i++){
				decryptedM[i] = modDecrypt(chunks[i], decryptionD, n);
			} //Resulting array should contain
			
			//Concatenate all 8 digit decrypted numbers
			String concatenatedNums = "";
			for(int i = 0; i < decryptedM.length; i++){
				concatenatedNums += decryptedM[i]; 
			}
			
			//Get the ascii characters from the numbers
			char[] asciiArray = asciiConverter(concatenatedNums);
			
			//Concatenate all characters to final decrypted string message and print
			String finalMessage = "";
			for(int i = 0; i < asciiArray.length; i++){
				finalMessage += asciiArray[i];
			}
			
			System.out.println("<"+finalMessage+">");
			
			
			
		}
		else{ // Print error message and exit if there are not.
			System.out.println("ERROR: There is not an adequate amount of command line arguments.  The program takes in "
					+ "an input string of digits, a private key decryption exponent 'd', and "
					+ "a large number 'n', amounting to three total command line arguments.  Please rerun the program"
					+ "with correct specifications so you can perform the RSA decryption.");
			System.out.println("System now exiting...");
			System.out.println("Bye!");
			System.exit(1);
		}
		
	}
	
	/**
	 * Responsible for breaking the input string of digits into blocks of 9
	 * @param decodingDigits long string of digits
	 * @return string array containing indices of 9 digits each
	 */
	public static String[] nineBlocks(String decodingDigits){
		//Makes sure the length of array is enough for however many blocks of 9 are needed
		String[] blockArr = new String[(decodingDigits.length()/9)]; 
		
		int chunkLength = 9; //For last index to put in
		int indexTracker = 0; //For first index to put in
		
		for(int i = 0; i < blockArr.length; i++){
			blockArr[i] = decodingDigits.substring(indexTracker, chunkLength);
			chunkLength += 9;
			indexTracker += 9;
			
		}
		return blockArr; //Should contain separated digits by 9 in indices
	}
	
	/**
	 * Performs the decryption algorithm on the blocks of digits using M = (C^d)mod(n).
	 * Additionally, of the result of M is less than 8 digits, pads to the left with
	 * sufficient zeros.
	 * @param c blocks of encrypted digits
	 * @param d private key decryption exponent
	 * @param n private key long number from n=pq where p and q are prime
	 * @return decrypted string of digits to be changed into ASCII
	 */
	public static String modDecrypt(String c, int d, long n){
		int cInt = Integer.parseInt(c); //Change ciphertext to an integer first
		long m = 1;
		
		long power = cInt % n;
		
		String binaryRep = Integer.toBinaryString(d); //Change decryption exponent d to binary
		
		//Start at the least significant index of the binary and work your way backwards.
		for(int i = (binaryRep.length() - 1); i >= 0; i--){
			
			char binaryChar = binaryRep.charAt(i); //Take character at i'th value in binary representation
			
			if(binaryChar == '1'){
				m = (m*power) % n;
			}
			
			power = (power*power) % n;
		}
		
		String mString = String.valueOf(m);
		
		//Account for if the result is less than 8 digits.  Pad to left w/ zeros.
		while(mString.length() < 8){
			mString = "0" + mString;
		}
		
		return mString;
		
		
	}
	
	/**
	 * Breaks the string of numbers into chunks of three, converts these numbers to their ASCII equivalent character,
	 * and stores them inside of a character array whose contents will later be concatenated to to store the final decrypted string.
	 * @param numbers a string of numbers containing the decrypted digits to be put into ASCII
	 * @return character array containing ASCII translation of decoded digits
	 */
	public static char[] asciiConverter(String numbers){
		String[] chunkHolder = new String[numbers.length()/3]; //Will hold the chunks of 3 to convert.
		char[] asciiArr = new char[chunkHolder.length];

		int chunkSize = 3; 
		int indexBegin = 0;
		
		for(int i = 0; i < chunkHolder.length; i++){
			chunkHolder[i] = numbers.substring(indexBegin, chunkSize);
			chunkSize += 3;
			indexBegin += 3;
			
		} //chunkHolder should now contain blocks of 3 integers in the form of strings
		
		for(int i = 0; i < asciiArr.length; i++){
			int integer = Integer.parseInt(chunkHolder[i]);
			char character = (char)integer; //Get the ascii representation
			asciiArr[i] = character;
			
		}
		
		return asciiArr;
		
	}
	
	

}
