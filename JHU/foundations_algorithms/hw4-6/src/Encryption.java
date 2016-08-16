import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;
import java.math.BigInteger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.KeyPairGenerator;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyFactory;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidAlgorithmParameterException;
import java.io.UnsupportedEncodingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.SecureRandom;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.IOException;

import javax.xml.bind.DatatypeConverter;

public class Encryption {


  /** Simple ceasar shift cypher
    */
  public static void ceasarShift(ArrayList<String> plaintext, int shift) {
    for (int i=0; i<plaintext.size(); i++) {
      String substring = plaintext.get(i);
      char[] characters = substring.toCharArray();

      for (int j=0; j<characters.length; j++){
        char newChar = (char) newAscii((int) characters[j], shift);
        //System.out.println(characters[j] + " --> " + newChar);
        characters[j] = newChar;
      }

      substring = String.valueOf(characters);
      plaintext.set(i, substring);
    }
  }

  /** Provide new, shifted ascii character mod 256
    */
  private static int newAscii(int current, int shift) {
    return  mod(current + shift, 256);
  }

  /** Modulo function
    */
  private static int mod(int x, int y)
  {
      int result = x % y;
      return result < 0 ? result + y : result;
  }

  /** RSA encryption function
    */
  public static void RSAEncrypt(ArrayList<String> plaintext) {

    try {
      System.out.println("Running RSA Encryption");

      // Get an instance of the RSA key generator
      KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
      SecureRandom random = new SecureRandom();
      // Set seed for rebeatable results - not to be used in a production system.
      random.setSeed(random.generateSeed(51));
      kpg.initialize(2048, random);
      KeyPair myPair = kpg.generateKeyPair();

      // Get an instance of the Cipher for RSA encryption/decryption
      Cipher c = Cipher.getInstance("RSA");
      c.init(Cipher.ENCRYPT_MODE, myPair.getPublic());

      System.out.println("Public key generated: ");
      System.out.println(myPair.getPublic().getEncoded());
      System.out.println("Private key generated: ");
      System.out.println(myPair.getPrivate().getEncoded());

      // dump keys to file for later use
      KeyDump("RSApublicKey", myPair.getPublic().getEncoded());
      KeyDump("RSAprivateKey", myPair.getPrivate().getEncoded());

      for (int i=0; i<plaintext.size(); i++) {
        String word = plaintext.get(i);

        byte[] text = word.getBytes();
        byte[] textEncrypted = c.doFinal(text);

        // output as hex to avoid String removal of characters.
        plaintext.set(i, DatatypeConverter.printHexBinary(textEncrypted));
      }
    } catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		} catch(NoSuchPaddingException e){
			e.printStackTrace();
		} catch(InvalidKeyException e){
			e.printStackTrace();
		} catch(IllegalBlockSizeException e){
			e.printStackTrace();
		} catch(BadPaddingException e){
			e.printStackTrace();
		}

  }

  public static void RSADecrypt(ArrayList<String> codedtext, String keyfile) throws Exception {
    System.out.println("#  Starting RSA Decryption using key from filname: ." + keyfile);
    //Key privKey = RSAKeyFromFile(keyfile);

    // Get an instance of the RSA key generator
    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
    SecureRandom random = new SecureRandom();
    random.setSeed(random.generateSeed(51));
    kpg.initialize(2048, random);
    // Generate the keys — might take sometime on slow computers
    KeyPair myPair = kpg.generateKeyPair();
    Key privKey = myPair.getPrivate();


    System.out.println(privKey);

        try {

          Cipher d = Cipher.getInstance("RSA");
          d.init(Cipher.DECRYPT_MODE, privKey);
          System.out.println("Private key: " + privKey);

          for (int i=0; i<codedtext.size(); i++) {
            String word = codedtext.get(i);

            byte[] text = DatatypeConverter.parseHexBinary(word);
            byte[] textDecrypted = d.doFinal(text);
            codedtext.set(i, new String(textDecrypted));
          }
        } catch(NoSuchAlgorithmException e){
    			e.printStackTrace();
    		} catch(NoSuchPaddingException e){
    			e.printStackTrace();
    		} catch(InvalidKeyException e){
    			e.printStackTrace();
    		} catch(IllegalBlockSizeException e){
    			e.printStackTrace();
    		} catch(BadPaddingException e){
    			e.printStackTrace();
    		}

  }

  public static void KeyDump(String outName, byte[] keyBytes) {
    OutputStream output = null;

    try {
      File file = new File(outName);
      // dump key to file

      output = new FileOutputStream(outName);
      output.write(keyBytes);
      output.close();

    } catch (IOException e) {
       e.printStackTrace();
    }  finally {
        try{
          if (output != null) {
             output.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
      }
    }
  }

  public static Key KeyFromFile(String fileName, String algo) throws Exception {
      File f = new File(fileName);
      FileInputStream fis = new FileInputStream(f);
      DataInputStream dis = new DataInputStream(fis);
      byte[] keyBytes = new byte[(int)f.length()];
      dis.readFully(keyBytes);
      dis.close();

      SecretKey pk = new SecretKeySpec(keyBytes, algo);

      return pk;
  }

  /** AES encryption function
    */
  public static void AESEncrypt(ArrayList<String> plaintext) {

    try {
      System.out.println("Running AES Encryption");

      // Get an instance of the RSAkey generator
      KeyGenerator kpg = KeyGenerator.getInstance("AES");
      kpg.init(128);

      SecretKey SecKey = kpg.generateKey();

      KeyDump("AESKey", SecKey.getEncoded());

      // Get cipher instance for encryption
      Cipher c = Cipher.getInstance("AES");
      c.init(Cipher.ENCRYPT_MODE,SecKey);

      System.out.println("Private key generated: ");
      System.out.println(SecKey);

      for (int i=0; i<plaintext.size(); i++) {
        String word = plaintext.get(i);

        byte[] text = word.getBytes();
        byte[] textEncrypted = c.doFinal(text);

        // output as hex to avoid String removal of characters.
        plaintext.set(i, DatatypeConverter.printHexBinary(textEncrypted));
      }
    } catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		} catch(NoSuchPaddingException e){
			e.printStackTrace();
		} catch(InvalidKeyException e){
			e.printStackTrace();
		} catch(IllegalBlockSizeException e){
			e.printStackTrace();
		} catch(BadPaddingException e){
			e.printStackTrace();
		}

  }

  public static void AESDecrypt(ArrayList<String> codedtext, String keyfile) throws Exception {
    System.out.println("Running AES Decryption");

    Key SecKey = KeyFromFile(keyfile, "AES");

    System.out.println("Private key used: ");
    System.out.println(SecKey);

    // Get cipher instance for encryption
    Cipher d = Cipher.getInstance("AES");
    d.init(Cipher.DECRYPT_MODE,SecKey);


        try {

          for (int i=0; i<codedtext.size(); i++) {
            String word = codedtext.get(i);

            byte[] text = DatatypeConverter.parseHexBinary(word);
            byte[] textDecrypted = d.doFinal(text);
            codedtext.set(i, new String(textDecrypted));
          }
        } catch(IllegalBlockSizeException e){
    			e.printStackTrace();
    		} catch(BadPaddingException e){
    			e.printStackTrace();
    		}

  }


}
