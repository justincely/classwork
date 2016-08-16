import java.util.ArrayList;
import java.lang.Math;
import java.math.BigInteger;

import javax.crypto.Cipher;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.KeyFactory;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
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

  private static int newAscii(int current, int shift) {
    return  mod(current + shift, 256);
  }

  private static int mod(int x, int y)
  {
      int result = x % y;
      return result < 0 ? result + y : result;
  }

  /** ElGamal encryption
  public static void ElGamalEncrypt(ArrayList<String> plaintext, int base, int secretKey, BigInteger publicKey, int modVal) {
    BigInteger bigBase = BigInteger.valueOf(base);
    //BigInteger bigSecretKey = BigInteger.valueOf(secretKey);


    Integer kMine = mod(bigBase.pow(secretKey), modVal);
    Integer code = mod(BigInteger.pow(kMine, publicKey), modVal);

    for (int i=0; i<plaintext.size(); i++) {
      String substring = plaintext.get(i);
      char[] characters = substring.toCharArray();

      for (int j=0; j<characters.length; j++){
        char newChar = (char) ((int) characters[j]) * code;
        //System.out.println(characters[j] + " --> " + newChar);
        characters[j] = newChar;
      }

      substring = String.valueOf(characters);
      plaintext.set(i, substring);
    }


  }
  */

  public static void RSAEncrypt(ArrayList<String> plaintext) {

    try {
      // Get an instance of the RSA key generator
      KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
      SecureRandom random = new SecureRandom();
      random.setSeed(random.generateSeed(51));
      kpg.initialize(2048, random);
      // Generate the keys — might take sometime on slow computers
      KeyPair myPair = kpg.generateKeyPair();

      // Get an instance of the Cipher for RSA encryption/decryption
      Cipher c = Cipher.getInstance("RSA");
      // Initiate the Cipher, telling it that it is going to Encrypt, giving it the public key
      c.init(Cipher.ENCRYPT_MODE, myPair.getPublic());

      //System.out.println("Public key: " + myPair.getPublic());

      // dump key to file
      RSAKeyDump("publicKey", myPair.getPublic().getEncoded());
      RSAKeyDump("privateKey", myPair.getPrivate().getEncoded());


      Cipher d = Cipher.getInstance("RSA");
      d.init(Cipher.DECRYPT_MODE, myPair.getPrivate());
      System.out.println("Private key: " + myPair.getPrivate());

      for (int i=0; i<plaintext.size(); i++) {
        String word = plaintext.get(i);
      }


      for (int i=0; i<plaintext.size(); i++) {
        String word = plaintext.get(i);
        String outWord = "";

        System.out.println(word);
        System.out.println("-------------------");

        byte[] text = word.getBytes();
        byte[] textEncrypted = c.doFinal(text);
        plaintext.set(i, new String(textEncrypted));
        System.out.println(textEncrypted);

        /**
        for (int j=0; j<word.length(); j++) {
          byte[] text = word.substring(j, j+1).getBytes();
          System.out.println(new String(text));

          //Do encryption
          byte[] textEncrypted = c.doFinal(text);
          System.out.println("Text Encrypted " + textEncrypted);

          outWord = outWord + new String(textEncrypted);

          //byte[] textDecrypted = d.doFinal(textEncrypted);
          //System.out.println("Text Decrypted " + new String(textDecrypted));
        }

        plaintext.set(i, new String(outWord));
        */
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

          String allText = "";
          for (int i=0; i<codedtext.size(); i++) {
            allText = allText + codedtext.get(i);
          }

          byte[] ntext = allText.getBytes();
          //byte[] textDecrypted = d.doFinal(text);
          System.out.println(d.doFinal(ntext));


          for (int i=0; i<codedtext.size(); i++) {
            String word = codedtext.get(i);
            System.out.println(word);
            System.out.println("-------------------");

            String outWord = "";

            byte[] text = word.getBytes();
            byte[] textDecrypted = d.doFinal(text);
            //codedtext.set(i, new String(textDecrypted));

            /**
            for (int j=0; j<word.length(); j++) {
              byte[] text = word.substring(j, j+1).getBytes();
              System.out.println("Text original " + new String(text));

              byte[] textDecrypted = d.doFinal(text);
              System.out.println("Text Decrypted " + new String(textDecrypted));

              outWord = outWord + new String(textDecrypted);

              //byte[] textDecrypted = d.doFinal(textEncrypted);
              //System.out.println("Text Decrypted " + new String(textDecrypted));
            }

            codedtext.set(i, outWord);
            */
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

  public static void RSAKeyDump(String outName, byte[] keyBytes) {
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

  public static Key RSAKeyFromFile(String fileName) throws Exception {
      Key pk = null;
      File f = new File(fileName);
      FileInputStream fis = new FileInputStream(f);
      DataInputStream dis = new DataInputStream(fis);
      byte[] keyBytes = new byte[(int)f.length()];
      dis.readFully(keyBytes);
      dis.close();

      PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
      KeyFactory kpg = KeyFactory.getInstance("RSA");
      //kpg.initialize(2048);
      pk = kpg.generatePrivate(spec);

      return pk;
  }

}
