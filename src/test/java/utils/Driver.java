package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver {
    public static Variables variables = Variables.getVariables();

    public static String validateVariables(String var) {
        for (Map.Entry<String, String> x : variables.getMap().entrySet()) {
            if (var.contains("${")) {
                List<String> list = new ArrayList<>();

                String b = "(?<=\\$\\{)([\\s\\S]+?)(?=})";
                Pattern p = Pattern.compile(b);
                Matcher m = p.matcher(var);
                if (m.find()) {
                    list.add(m.group());
                    int a;
                }
            }

            return variables.getMap().get(var);
        }
        return var;
    }
}
