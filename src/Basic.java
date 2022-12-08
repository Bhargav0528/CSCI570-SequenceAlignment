
import constants.ProgramConstants;
import model.SequenceAlignmentOutput;
import model.StringPair;

import java.util.*;
import java.io.IOException;


public class Basic {

  public static void main(String[] args) throws IOException, InterruptedException {
    float[] cpuTimes = new float[15];
    int[] problemSize = new int[15];
    float[] memories = new float[15];
    for (int i = 1; i<2;i++) {
      InputGeneration inputGeneration = new InputGeneration();

      StringPair stringPair = inputGeneration.readInputs("resources/in"+i+".txt");

//      System.out.println(stringPair.getS1().length() + stringPair.getS2().length());
      SequenceAlignmentOutput output = new SequenceAlignmentOutput();

      sequenceAlignmentDp(stringPair.getS1(), stringPair.getS2(), output);

      cpuTimes[i-1] = output.getTimeInMilliSeconds();
      problemSize[i-1] = stringPair.getS1().length() + stringPair.getS2().length();
      memories[i-1] = output.getMemoryInKB();
      System.out.println(output);

//      Thread.sleep(1000);
    }

//    for (int i =0;i<15;i++) {
//      System.out.println(cpuTimes[i]);
//    }
//    for (int i =0;i<15;i++) {
//      System.out.println(memories[i]);
//    }
//
//      for (int i =0;i<15;i++) {
//      System.out.println(problemSize[i]);
//    }
  }

  public static void sequenceAlignmentDp(String s1, String s2, SequenceAlignmentOutput output) {
    StringPair outputStrings = new StringPair();
    Runtime currentMemory = Runtime.getRuntime();
    float startMemory = currentMemory.totalMemory() - currentMemory.freeMemory();
    float startTime = Double.valueOf(System.nanoTime()/10e6).floatValue();

    output.setCostOfAlignment(sequenceAlignmentDpUtil(s1, s2, outputStrings));

    float endTime = Double.valueOf(System.nanoTime()/10e6).floatValue();
    currentMemory = Runtime.getRuntime();
    float endMemory = currentMemory.totalMemory() - currentMemory.freeMemory();

    output.setMemoryInKB(Double.valueOf((endMemory - startMemory)/10e3).floatValue());
    output.setTimeInMilliSeconds(endTime-startTime);

//    System.out.println(currentMemory.totalMemory()-currentMemory.freeMemory());
    output.setFirstAlignmentString(outputStrings.getS1());
    output.setSecondAlignmentString(outputStrings.getS2());
  }

  public static int sequenceAlignmentDpUtil(String s1, String s2, StringPair output) {
    int[][] opt = new int[s1.length()+1][s2.length()+1];

    for(int i=0; i<=s1.length(); i++) {
      opt[i][0] = ProgramConstants.DELTA *i;
    }
    for(int j=0; j<=s2.length(); j++) {
      opt[0][j] = ProgramConstants.DELTA*j;
    }

    for(int i=1; i<=s1.length(); i++) {
      for(int j=1; j<=s2.length(); j++) {
        opt[i][j] = Math.min(ProgramConstants.ALPHA_VALUES.get(s1.charAt(i-1)).get(s2.charAt(j-1)) + opt[i-1][j-1],
                            Math.min(ProgramConstants.DELTA + opt[i-1][j], ProgramConstants.DELTA + opt[i][j-1]));

      }
    }
    topDownPass(opt, s1, s2, s1.length(), s2.length(), output);
    return opt[s1.length()][s2.length()];
  }


  public static void topDownPass(int[][] opt, String s1, String s2, int s1Length, int s2Length, StringPair output) {
    int i = s1Length;
    int j = s2Length;

    int combinedLength = s1Length + s2Length;

    char[] finalS1String = new char[combinedLength];
    char[] finalS2String = new char[combinedLength];

    int s1ToBuildFromIndex = combinedLength-1;
    int s2ToBuildFromIndex = combinedLength-1;

    while (i!=0 && j!=0) {
      if (s1.charAt(i-1) == s2.charAt(j-1)
          || opt[i-1][j-1] + ProgramConstants.ALPHA_VALUES.get(s1.charAt(i-1)).get(s2.charAt(j-1)) == opt[i][j]) {
        finalS1String[s1ToBuildFromIndex--] = s1.charAt(i-1 );
        finalS2String[s2ToBuildFromIndex--] = s2.charAt(j-1);
        i--;
        j--;
      } else if (opt[i-1][j] + ProgramConstants.DELTA == opt[i][j]) {
        finalS1String[s1ToBuildFromIndex--] = s1.charAt(i-1);
        finalS2String[s2ToBuildFromIndex--] = '_';
        i--;
      } else if (opt[i][j-1] + ProgramConstants.DELTA == opt[i][j]) {
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

    output.setS1(new String(finalS1String).substring(id));
    output.setS2(new String(finalS2String).substring(id));

  }

}