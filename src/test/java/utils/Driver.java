package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Driver {
    public static Variables variables = Variables.getVariables();

    public static String validateVariables(String var) {

        if (var.contains("${")) {
            List<String> list = new ArrayList<>();
            Pattern p = Pattern.compile("(?<=\\$\\{)([\\s\\S]+?)(?=\\})");
            Matcher m = p.matcher(var);
            while (m.find()) {
                list.add(m.group());
            }
            for (String a : list) {
                var = var.replaceAll("\\$\\{" + a + "}", variables.getMap().get(a));
            }

            return var;
        }
        return var;
    }
}
