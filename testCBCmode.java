//Alaa Shaheen 1200049
import java.util.Scanner;
import java.io.*;

public class testCBCmode{
	
	public static void main(String[] args) throws IOException{
		//int[] key = {10,12,13,14};					//instantiating a key (128 bits)
		Scanner scanner = new Scanner(System.in);
		// Ask the user to input the Key (ex: 10 12 13 14)
        System.out.println("Enter the key for TEA encryption (4 integers separated by space): ");
        int[] key = new int[4];
        for (int i = 0; i < 4; i++) {
            key[i] = scanner.nextInt();
        }

		CBCmode cbc = new CBCmode(key);					// instantiating a TEA class
		int[] img = new int[2];

		//Ask the user to input the IV (ex: 100 200) 64 bits same as block size
        System.out.println("Enter the initialization vector (IV) for TEA encryption (2 integers separated by space): "); //100 200
        int[] IV = new int[2];
        for (int i = 0; i < 2; i++) {
            IV[i] = scanner.nextInt();
        }

		FileInputStream imgIn = new FileInputStream("image\\Aqsa.bmp");
		FileOutputStream imgOut = new FileOutputStream("image\\CBCencrypt.bmp");
		
		DataInputStream dataIn = new DataInputStream(imgIn);
		DataOutputStream dataOut = new DataOutputStream(imgOut);
		
		// skip the first 10 blocks
		// ReadInt() return 32 bits so it called 2 times to read 64 bits = block size
		for(int i=0;i<10;i++){
			if(dataIn.available() > 0){
				img[0] = dataIn.readInt();
				img[1] = dataIn.readInt();
				dataOut.writeInt(img[0]);
				dataOut.writeInt(img[1]);
			}
		}
		
		boolean firstTime = true;		// to know when to apply IV or the previous encrypted block
		int cipher[] = new int[2];
		boolean check = true;			// to catch where the reading from the file is stopped
		while(dataIn.available() > 0){
			try{
				img[0] = dataIn.readInt();
				check = true;
				img[1] = dataIn.readInt();
				if(firstTime){		// if true, the block is passed with IV to be encrypted by TEA algorithm
					cipher = cbc.encrypt(img, IV);
					firstTime = false;		// set firstTime to false sense IV is only encrypted in the first block
				}
				else
					cipher = cbc.encrypt(img, cipher);		// pass the block with the previous encrypted block
				
				dataOut.writeInt(cipher[0]);
				dataOut.writeInt(cipher[1]);
				check = false;
			}catch(EOFException e){				// excetion is thrown if the file ends and dataIn.readInt() is executed 
				if(!check){						// if false, it means last block were not encrypted
					dataOut.writeInt(img[0]);
					dataOut.writeInt(img[1]);
				}else							// if true, it means only last half a block is not encrypted
					dataOut.writeInt(img[0]);
			}
			
		}
		dataIn.close();
		dataOut.close();
		
		/********************    Decrypting the Aqsa image    ********************/
		DataInputStream dataIn1 = new DataInputStream(new FileInputStream("image\\CBCencrypt.bmp"));
		DataOutputStream dataOut1 = new DataOutputStream(new FileOutputStream("image\\CBCdecrypt.bmp"));
		
		for(int i=0;i<10;i++){
			if(dataIn1.available() > 0){
				img[0] = dataIn1.readInt();
				img[1] = dataIn1.readInt();
				dataOut1.writeInt(img[0]);
				dataOut1.writeInt(img[1]);
			}
		}
		
		int[] copyCipher = new int[2];
		firstTime = true;
		int plain[] = new int[2];
		check = true;
		
		while(dataIn1.available() > 0){
			try{
				img[0] = dataIn1.readInt();
				check = true;
				img[1] = dataIn1.readInt();
				
				if(firstTime){							// if true, the first block is passed with IV to be decrytped
					plain = cbc.decrypt(img,IV);
					firstTime = false;					// set first time to false
				}else									// if false, the block is passed with the previously encrypted block
					plain = cbc.decrypt(img,copyCipher);		
				
				dataOut1.writeInt(plain[0]);
				dataOut1.writeInt(plain[1]);
				
				copyCipher[0] = img[0];				// Save the previously encryted block in copyCipher to use it
				copyCipher[1] = img[1];
				
				check = false;
			}catch(EOFException e){
				if(!check){
					dataOut1.writeInt(img[0]);
					dataOut1.writeInt(img[1]);
				}else
					dataOut1.writeInt(img[0]);;
			}
			
		}
		dataIn1.close();
		dataOut1.close();
		
		imgOut.close();
		imgIn.close();

	}
}
