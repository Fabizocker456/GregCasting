package eu.seahousen.gregcasting.casting;

import at.petrak.hexcasting.api.casting.math.HexDir;
import at.petrak.hexcasting.api.casting.math.HexPattern;

public class ExamplePatternGenerator {
    static int cur = 0;

    public static HexPattern makeExamplePattern() {
        HexPattern ret = getExamplePattern(cur);
        cur++;
        return ret;
    }

    static String wPad(String x, int ws) {
        String wstr = "w".repeat(ws);
        StringBuilder ret = new StringBuilder(wstr);
        for(char i : x.toCharArray()) {
            ret.append(i).append(wstr);
        }
        return ret.toString();
    }

    static HexPattern getExamplePattern(int cur) {
        int mod = cur % 2;
        int size = (cur / 2);
        if(mod == 0) {
            return HexPattern.fromAngles(wPad("deeee", size), HexDir.EAST);
        } else if (mod == 1) {
            return HexPattern.fromAngles(wPad("qqqqa", size), HexDir.EAST);
        }
        return null;
    }
}
