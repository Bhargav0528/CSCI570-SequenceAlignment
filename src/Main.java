import java.util.*;
public class Main {
  static int delta = 30;
  public static void main(String[] args) {
    System.out.println("Hello world!");
    System.out.println(sequenceAlignment("A", "C"));

  }


  static HashMap<Character, HashMap<Character, Integer>> alphaValues = new HashMap<>();
  static HashMap<Character, Integer> temp = new HashMap<>();

  static {
    temp.put('A', 0);
    temp.put('C', 110);
    temp.put('G', 48);
    temp.put('T', 94);
    alphaValues.put('A', temp);

    temp = new HashMap<>();
    temp.put('A', 110);
    temp.put('C', 0);
    temp.put('G', 118);
    temp.put('T', 48);
    alphaValues.put('C', temp);

    temp = new HashMap<>();
    temp.put('A', 48);
    temp.put('C', 118);
    temp.put('G', 0);
    temp.put('T', 110);
    alphaValues.put('G', temp);

    temp = new HashMap<>();
    temp.put('A', 94);
    temp.put('C', 48);
    temp.put('G', 110);
    temp.put('T', 0);
    alphaValues.put('T', temp);
  }



  //Given two strings
  public static void divideAndConquer(String x, String y) {

  }

  public static void getYSplitPoint(String xLeft, String xRight, String y) {

  }

  public static int sequenceAlignment(String s1, String s2) {
    int[][] opt = new int[s1.length()+1][s2.length()+1];

    for(int i=0; i<=s1.length(); i++) {
      opt[i][0] = delta*i;
    }
    for(int j=0; j<=s2.length(); j++) {
      opt[0][j] = delta*j;
    }

    for(int i=1; i<=s1.length(); i++) {
      for(int j=1; j<=s2.length(); j++) {
        opt[i][j] = Math.min(alphaValues.get(s1.charAt(i-1)).get(s2.charAt(j-1)) + opt[i-1][j-1],
                            Math.min(delta + opt[i-1][j], delta + opt[i][j-1]));

      }
    }

    for(int i=0; i<=s1.length(); i++) {
      for(int j=0; j<=s2.length(); j++) {
        System.out.print(opt[i][j] + " ");
      }
      System.out.println();
    }
    return opt[s1.length()][s2.length()];
  }


}