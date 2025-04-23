package github.PanheadGG.SrunLogin.SrunAPI;

public class RadUserInfoUrl {
    private static final String domain = "http://210.43.112.9/cgi-bin/rad_user_info";
    public RadUserInfoUrl(){}
    public String toString(){
        return String.format(domain+"?callback=function&_=%d",System.currentTimeMillis());
    }
    public static String getUrl(){
        return String.format(domain+"?callback=function&_=%d",System.currentTimeMillis());
    }
}
