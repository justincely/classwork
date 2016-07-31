import java.util.ArrayList;

public class Encryption {

  public static void ceasarShift(ArrayList<String> plaintext, int shift) {
    for (int i=0; i<plaintext.size(); i++) {
      String substring = plaintext.get(i);
      char[] characters = substring.toCharArray();

      for (int j=0; j<characters.length; j++){
        char newChar = (char) newAscii((int) characters[j], shift);
        System.out.println(characters[j] + " --> " + newChar);
        characters[j] = newChar;
      }

      substring = String.valueOf(characters);
      plaintext.set(i, substring);
    }
  }

  private static int newAscii(int current, int shift) {
    return  32 + mod(current-32 + shift, 95);
  }

  private static int mod(int x, int y)
  {
      int result = x % y;
      return result < 0 ? result + y : result;
  }

}
