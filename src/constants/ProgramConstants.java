package constants;

import java.util.HashMap;

public final class ProgramConstants {

    public static int DELTA = 30;

    public static HashMap<Character, HashMap<Character, Integer>> ALPHA_VALUES = new HashMap<>();
    static HashMap<Character, Integer> temp = new HashMap<>();

    static {
        temp.put('A', 0);
        temp.put('C', 110);
        temp.put('G', 48);
        temp.put('T', 94);
        ALPHA_VALUES.put('A', temp);

        temp = new HashMap<>();
        temp.put('A', 110);
        temp.put('C', 0);
        temp.put('G', 118);
        temp.put('T', 48);
        ALPHA_VALUES.put('C', temp);

        temp = new HashMap<>();
        temp.put('A', 48);
        temp.put('C', 118);
        temp.put('G', 0);
        temp.put('T', 110);
        ALPHA_VALUES.put('G', temp);

        temp = new HashMap<>();
        temp.put('A', 94);
        temp.put('C', 48);
        temp.put('G', 110);
        temp.put('T', 0);
        ALPHA_VALUES.put('T', temp);
    }

}
