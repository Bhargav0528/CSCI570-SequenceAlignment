
import model.InputString;

import java.util.*;
import java.io.IOException;

public class Main {
  static int delta = 30;
  public static void main(String[] args) throws IOException {
    InputGeneration inputGeneration = new InputGeneration();

    InputString inputString = inputGeneration.readInputs("resources/input.txt");
    System.out.println(sequenceAlignment(inputString.getS1(), inputString.getS2()));
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
    topDownPass(opt, s1, s2, s1.length(), s2.length());
    return opt[s1.length()][s2.length()];
  }


  public static void topDownPass(int[][] opt, String s1, String s2, int s1Length, int s2Length) {
    int i = s1Length;
    int j = s2Length;

    int combinedLength = s1Length + s2Length;

    char[] finalS1String = new char[combinedLength];
    char[] finalS2String = new char[combinedLength];

    int s1ToBuildFromIndex = combinedLength-1;
    int s2ToBuildFromIndex = combinedLength-1;

    while (i!=0 && j!=0) {
      if (s1.charAt(i-1) == s2.charAt(j-1)
          || opt[i-1][j-1] + alphaValues.get(s1.charAt(i-1)).get(s2.charAt(j-1)) == opt[i][j]) {
        finalS1String[s1ToBuildFromIndex--] = s1.charAt(i-1 );
        finalS2String[s2ToBuildFromIndex--] = s2.charAt(j-1);
        i--;
        j--;
      } else if (opt[i-1][j] + delta == opt[i][j]) {
        finalS1String[s1ToBuildFromIndex--] = s1.charAt(i-1);
        finalS2String[s2ToBuildFromIndex--] = '_';
        i--;
      } else if (opt[i][j-1] + delta == opt[i][j]) {
        finalS1String[s1ToBuildFromIndex--] = '_';
        finalS2String[s2ToBuildFromIndex--] = s2.charAt(j-1);
        j--;
      }
    }

    while (s1ToBuildFromIndex > 0) {
      if (i>0) {
        finalS1String[s1ToBuildFromIndex--] = s1.charAt(--i);
      } else {
        finalS1String[s1ToBuildFromIndex--] = '_';
      }
    }

    while (s2ToBuildFromIndex > 0) {
      if (j>0) {
        finalS2String[s2ToBuildFromIndex--] = s2.charAt(--j);
      } else {
        finalS2String[s2ToBuildFromIndex--] = '_';
      }
    }

    int id = 1;
    for (i = combinedLength-1; i >= 1; i--)
    {
      if (finalS1String[i] == '_' && finalS2String[i] == '_')
      {
        id = i + 1;
        break;
      }
    }

    System.out.println(new String(finalS1String).substring(id));
    System.out.println(new String(finalS2String).substring(id));

  }

}