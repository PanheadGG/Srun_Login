package github.PanheadGG.SrunLogin.SrunAPI;

import static github.PanheadGG.SrunLogin.Main.GLOBAL_DOMAIN_DOMAIN;

public class RadUserInfoUrl {
    private static final String domain = "http://210.43.112.9";
    public RadUserInfoUrl(){}
    public String toString(){
        return String.format(GLOBAL_DOMAIN_DOMAIN +"/cgi-bin/rad_user_info?callback=function&_=%d",System.currentTimeMillis());
    }
    public static String getUrl(){
        return String.format(GLOBAL_DOMAIN_DOMAIN +"/cgi-bin/rad_user_info?callback=function&_=%d",System.currentTimeMillis());
    }
}
