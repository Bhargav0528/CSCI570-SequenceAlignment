import constants.ProgramConstants;
import model.SequenceAlignmentOutput;
import model.StringPair;

import java.io.IOException;

public class Efficient {
    public static void main(String[] args) throws IOException {

        for (int i = 1;i<16;i++) {
            dcAlignmentCost = 0;
            InputGeneration inputGeneration = new InputGeneration();

            StringPair stringPair = inputGeneration.readInputs("resources/in"+i+".txt");
            SequenceAlignmentOutput output = new SequenceAlignmentOutput();
            divideAndConquer(stringPair.getS1(), stringPair.getS2(), output);

            System.out.println(output.getCostOfAlignment());
        }
    }

    static int dcAlignmentCost;

    public static void divideAndConquer(String x, String y, SequenceAlignmentOutput output) {
        StringPair outputStrings = new StringPair();

        Runtime currentMemory = Runtime.getRuntime();
        float startMemory = currentMemory.totalMemory() - currentMemory.freeMemory();
        float startTime = Double.valueOf(System.nanoTime()/10e6).floatValue();

        divideAndConquerRecur(x, y, outputStrings);

        float endTime = Double.valueOf(System.nanoTime()/10e6).floatValue();
        float endMemory = currentMemory.totalMemory() - currentMemory.freeMemory();

        output.setMemoryInKB(Double.valueOf(endMemory/10e3 - startMemory/10e3).floatValue());
        output.setTimeInMilliSeconds(endTime-startTime);

        output.setCostOfAlignment(dcAlignmentCost);

        output.setFirstAlignmentString(outputStrings.getS1());
        output.setSecondAlignmentString(outputStrings.getS2());
    }

    public static void divideAndConquerRecur(String x, String y, StringPair outputStrings) {
        if(x.length() <=2 || y.length() <= 2) {
            StringPair baseOutput = new StringPair();

//            System.out.println(outputStrings.getS1() + "                 " + outputStrings.getS2());

            int baseCost = Basic.sequenceAlignmentDpUtil(x, y, baseOutput);
            outputStrings.setS1(outputStrings.getS1() + baseOutput.getS1());
            outputStrings.setS2(outputStrings.getS2() + baseOutput.getS2());

//            System.out.println(outputStrings.getS1() + "                 " + outputStrings.getS2());
            dcAlignmentCost = dcAlignmentCost + baseCost;
            return;
        }
        int mid = x.length()/2;
        String xLeft = x.substring(0, mid);
        String xRight = x.substring(mid);
        int ySplit = getYSplitPoint(xLeft, xRight, y);
        divideAndConquerRecur(xLeft, y.substring(0, ySplit), outputStrings);
        divideAndConquerRecur(xRight, y.substring(ySplit), outputStrings);

    }

    public static int getYSplitPoint(String xLeft, String xRight, String y) {
        int[] yLeftArray = new int[y.length() + 1];
        int[] yRightArray = new int[y.length() + 1];

        StringBuilder xRightReverse = new StringBuilder(xRight);
        xRightReverse.reverse();
        StringBuilder yReverse = new StringBuilder(y);
        yReverse.reverse();

            yLeftArray = sequenceAlignmentDivide(xLeft, y);
            yRightArray = sequenceAlignmentDivide(xRightReverse.toString(), yReverse.toString());
//            System.out.print(yRightArray[i] + ", ");

//        for(int k=0;k<yRightArray.length;i++) {
//            System.out.print(yRightArray[i] + ", ");
//        }
        int minPoint = -1, minValue = Integer.MAX_VALUE;
        for(int i=0; i<=y.length(); i++) {
//            System.out.print(yLeftArray[i] + yRightArray[y.length() - i] + ", ");
            if(minValue > yLeftArray[i] + yRightArray[y.length() - i]) {
                minValue = yLeftArray[i] + yRightArray[y.length() - i];
                minPoint = i;
            }
        }


//        System.out.println();
//        System.out.println();
//        System.out.println(minPoint);
        return minPoint;
    }


    public static int[] sequenceAlignmentDivide(String s1, String s2) {
        int[][] opt = new int[2][s2.length()+1];

//        if(s2.length() == 0) {
//            return ProgramConstants.DELTA * s1.length();
//        }
        for(int i=0; i<=s2.length(); i++) {
            opt[0][i] = ProgramConstants.DELTA *i;
        }

        for(int j=1; j<=s1.length(); j++) {
            opt[1][0] = opt[0][0] + ProgramConstants.DELTA;
            for (int i = 1; i <= s2.length(); i++) {
                opt[1][i] = Math.min(ProgramConstants.ALPHA_VALUES.get(s1.charAt(j - 1)).get(s2.charAt(i - 1)) + opt[0][i-1],
                        Math.min(ProgramConstants.DELTA + opt[1][i - 1], ProgramConstants.DELTA + opt[0][i]));
            }
            for(int k=0; k<=s2.length();k++) {
                opt[0][k] = opt[1][k];
            }
        }

        return opt[1];
    }

}
