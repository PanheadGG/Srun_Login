package github.PanheadGG.SrunLogin.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataMatcher {
    private String data;
    public DataMatcher(){}
    public DataMatcher(String data){
        this.data = data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public String getString(String paramName) {
        String regex = "\"" + paramName + "\":\\s*(?:\"([^\"]*)\"|([^,}]+))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            // 优先取带引号的值
            if (matcher.group(1) != null) {
                return matcher.group(1);
            } else {
                return matcher.group(2);
            }
        }
        return null;
    }
    public int getInt(String paramName) {
        String regex = "\"" + paramName + "\":\\s*(?:\"([^\"]*)\"|([^,}]+))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
            // 优先取带引号的值
            if (matcher.group(1) != null) {
                return Integer.parseInt(matcher.group(1));
            } else {
                return Integer.parseInt(matcher.group(2));
            }
        }
        return 0;
    }
    public static String getString(String input, String paramName) {
        String regex = "\"" + paramName + "\":\\s*(?:\"([^\"]*)\"|([^,}]+))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            // 优先取带引号的值
            if (matcher.group(1) != null) {
                return matcher.group(1);
            } else {
                return matcher.group(2);
            }
        }
        return null;
    }
}
