//Alaa Shaheen 1200049
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.util.Scanner;

public class testECBmode {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
		// Ask the user to input the Key (ex: 10 12 13 14)
        System.out.println("Enter the key for TEA encryption (4 integers separated by space): ");
        int[] key = new int[4];
        for (int i = 0; i < 4; i++) {
            key[i] = scanner.nextInt();
        }

        // Instantiate the TEA class with the provided key
        ECBmode ecb = new ECBmode(key);

        int[] img = new int[2]; // img Variable will contain the block to be encrypted

        // Change the path if you install the image on a different path
        FileInputStream imgIn = new FileInputStream("image\\Aqsa.bmp");
        FileOutputStream imgOut = new FileOutputStream("image\\ECBencrypt.bmp");

        DataInputStream dataIn = new DataInputStream(imgIn);
        DataOutputStream dataOut = new DataOutputStream(imgOut);

        // Skipping the first 10 blocks (each block is 64 bits)
        for (int i = 0; i < 10; i++) {
            if (dataIn.available() > 0) {
                img[0] = dataIn.readInt();
                img[1] = dataIn.readInt();
                dataOut.writeInt(img[0]);
                dataOut.writeInt(img[1]);
            }
        }

        int[] cipher = new int[2];
        boolean check = true; // Check variable used to know where the file ends

        while (dataIn.available() > 0) {
            try {
                img[0] = dataIn.readInt(); // left sub block
                check = true;
                img[1] = dataIn.readInt(); // right sub block
                cipher = ecb.encrypt(img); // Passing the block to TEA algorithm to encrypt it
                dataOut.writeInt(cipher[0]); // writing back the encrypted block
                dataOut.writeInt(cipher[1]);
                check = false;
            } catch (EOFException e) { // exception is thrown if the file ends and dataIn.readInt() is executed
                if (!check) { // if false, it means last block were not encrypted
                    dataOut.writeInt(img[0]);
                    dataOut.writeInt(img[1]);
                } else // if true, it means only last half a block is not encrypted
                    dataOut.writeInt(img[0]);
            }
        }
        dataIn.close();
        dataOut.close();
        imgOut.close();
        imgIn.close();

        // Apply grayscale to the encrypted image
        applyGrayscale("image\\ECBencrypt.bmp", "image\\ECBencrypt_gray.bmp");

        // Decrypting the Image
        DataInputStream dataIn1 = new DataInputStream(new FileInputStream("image\\ECBencrypt_gray.bmp"));
        DataOutputStream dataOut1 = new DataOutputStream(new FileOutputStream("image\\ECBdecrypt.bmp"));

        for (int i = 0; i < 10; i++) {
            if (dataIn1.available() > 0) {
                img[0] = dataIn1.readInt();
                img[1] = dataIn1.readInt();
                dataOut1.writeInt(img[0]);
                dataOut1.writeInt(img[1]);
            }
        }

        int[] plain = new int[2];
        check = true;
        while (dataIn1.available() > 0) {
            try {
                img[0] = dataIn1.readInt();
                check = true;
                img[1] = dataIn1.readInt();
                plain = ecb.decrypt(img);
                dataOut1.writeInt(plain[0]);
                dataOut1.writeInt(plain[1]);
                check = false;
            } catch (EOFException e) {
                System.out.println("Check > " + check);
                if (!check) {
                    dataOut1.writeInt(img[0]);
                    dataOut1.writeInt(img[1]);
                } else
                    dataOut1.writeInt(img[0]);
            }
        }
        dataIn1.close();
        dataOut1.close();
    }

    // Method to apply grayscale filter
    private static void applyGrayscale(String inputImagePath, String outputImagePath) throws IOException {
        BufferedImage coloredImage = ImageIO.read(new File(inputImagePath));
        BufferedImage grayImage = new BufferedImage(coloredImage.getWidth(), coloredImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = grayImage.createGraphics();
        g.drawImage(coloredImage, 0, 0, null);
        g.dispose();
        ImageIO.write(grayImage, "bmp", new File(outputImagePath));
    }
}


