package me.qiancheng.qianworks.meicai.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
 

public class StringHelper {

    public static String removeBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
}