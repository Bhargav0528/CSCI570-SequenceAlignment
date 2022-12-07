import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputGeneration {
  final String regex = "^\\d+$";
  final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);

  public void readInputs(String inputFile) throws IOException {
    File file = new File(inputFile);
    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

    String baseString1 = bufferedReader.readLine();
    List<Integer> baseString1Modifiers = new ArrayList<>();

    String indices;
    while ((indices = bufferedReader.readLine()) != null) {
      Matcher matcher = pattern.matcher(indices);
      if (matcher.find()) {
        baseString1Modifiers.add(Integer.parseInt(indices));
      } else {
        break;
      }
    }

    String baseString2 = indices;
    List<Integer> baseString2Modifiers = new ArrayList<>();
    while ((indices = bufferedReader.readLine()) != null) {
      baseString2Modifiers.add(Integer.parseInt(indices));
    }

    modifyString(baseString1, baseString1Modifiers);
    modifyString(baseString2, baseString2Modifiers);
  }


  public void modifyString(String baseString, List<Integer> modifiers) {
    StringBuilder result = new StringBuilder(baseString);
    for (Integer index: modifiers) {
      result = new StringBuilder(result.substring(0, index + 1) + result + result.substring(index + 1));
    }
    System.out.println(result);
  }

}
