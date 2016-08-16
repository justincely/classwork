/**LZW compression
  *
  * Justin Ely
  */



import java.util.ArrayList;
import java.util.Hashtable;



public class LZW {

  /** Compression algorithm
    */
  public static ArrayList<Integer> compress (String plaintext) {
    System.out.println("Starting LZW compression");

    // Create dictionary to hold each string/integer combo
    // initialize to all ascii characters
    Hashtable <String, Integer> table = new Hashtable<String, Integer>();
    for (int i = 0; i < 256; i++)
      table.put(String.valueOf((char) i), i);


    String last = "";
    String next = "";
    ArrayList output = new ArrayList<Integer>();
    Integer nextSpot = 256;


    for (int i=0; i<plaintext.length(); i++) {
      next = last + plaintext.substring(i, i+1);

      if (table.containsKey(next)) {
        last = next;
      } else {
        output.add(table.get(last));
        table.put(next, nextSpot);
        nextSpot++;
        last = plaintext.substring(i, i+1);
      }

    }

    if (!last.equals(""))
        output.add(table.get(last));

    return output;
  }

  /** Decompression algorithm
    */
  public static String decompress(ArrayList<Integer> compressed) {

    System.out.println("Starting LZW decompression");
    Integer nextSpot = 256;

    // Create dictionary to hold each string/integer combo
    // initialize to all ascii characters
    Hashtable <Integer, String> table = new Hashtable<Integer, String>();
    for (int i = 0; i < 256; i++)
      table.put(i, String.valueOf((char) i));

    String w = "" + (char)(int)compressed.remove(0);
    StringBuffer result = new StringBuffer(w);
    for (int k : compressed) {
      String entry = "";
      if (table.containsKey(k))
        entry = table.get(k);
      else if (k == nextSpot)
        entry = w + w.charAt(0);
      else
        throw new IllegalArgumentException("Bad compressed k: " + k);

      result.append(entry);

      table.put(nextSpot++, w + entry.charAt(0));

      w = entry;
    }
    return result.toString();
  }

}
