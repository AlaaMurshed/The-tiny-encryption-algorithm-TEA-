# TEA Encryption and Decryption Implementation.
## Description
This project implements the Tiny Encryption Algorithm (TEA) in ECB and CBC modes to encrypt and decrypt images. The program reads an image, encrypts it using TEA, converts the encrypted image to grayscale, and then decrypts the image back.

## Requirements
Java JDK (version 8 or higher)
Any other dependencies or libraries

## Execution
**ECB Mode**
The program will:

Read the image located at image\Aqsa.bmp.
Encrypt the image and save the encrypted image as image\ECBencrypt.bmp.
Convert the encrypted image to grayscale and save it as image\ECBencrypt_gray.bmp.
Decrypt the grayscale encrypted image and save the decrypted image as image\ECBdecrypt.bmp.

## Code Structure
testECBmode.java: The main class that handles user input, image processing, encryption, and decryption.
ECBmode.java: The class implementing the TEA algorithm in ECB mode.
TEA.java: The parent class for the TEA algorithm.

**ECB Mode**
The program will:

Read the image located at image\Aqsa.bmp.
Encrypt the image using a user-provided initialization vector (IV) and save the encrypted image as image\CBCencrypt.bmp.
Decrypt the encrypted image using the same IV and save the decrypted image as image\CBCdecrypt.bmp.

## Code Structure
testCBCmode.java: The main class that handles user input, image processing, encryption, and decryption.
CBCmode.java: The class implementing the TEA algorithm in CBC mode.
TEA.java: The parent class for the TEA algorithm.
---------------------------------------------------------------------
## Example Usage

**ECB Mode**
Input the Key:
Enter the key for TEA encryption (4 integers separated by space): 10 12 13 14
Output Files:
Encrypted Image: image\ECBencrypt.bmp
Grayscale Encrypted Image: image\ECBencrypt_gray.bmp
Decrypted Image: image\ECBdecrypt.bmp

**CBC Mode**
Input the Key and IV:
Enter the key for TEA encryption (4 integers separated by space): 10 12 13 14
Enter the initialization vector (IV) for TEA encryption (2 integers separated by space): 100 200
Output Files:
Encrypted Image: image\CBCencrypt.bmp
Decrypted Image: image\CBCdecrypt.bmp


