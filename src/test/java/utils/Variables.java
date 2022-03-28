package utils;

import java.util.HashMap;
import java.util.Map;

public class Variables {

    private static Variables variables;

    private Variables() {
    }

    public static Variables getVariables() {
        if (variables == null) {
            variables = new Variables();
        }
        return variables;
    }

    private Map<String, String> map = new HashMap<>();

    public Map<String, String> getMap() {
        return map;
    }

}
