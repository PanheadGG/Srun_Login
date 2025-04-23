package github.PanheadGG.SrunLogin.SrunAPI;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static github.PanheadGG.SrunLogin.SrunAPI.Srun.*;

public class SrunPortalUrl {
    private final String domain = "http://210.43.112.9/cgi-bin/srun_portal";
    private String username;
    private String password;
    private String hmd5;
    private String ip;
    private String chksum;
    private String i;
    public SrunPortalUrl(String username, String password, String ip, String token){
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.hmd5 = pwd(password,token);
        Info info = new Info(username,password,ip,"5","srun_bx1");
        String i = info(info.toString(),token);
        String chkstr = token + username;
        chkstr += token + hmd5;
        chkstr += token + "5";
        chkstr += token + ip;
        chkstr += token + "200";
        chkstr += token + "1";
        chkstr += token + i;
        this.chksum = chksum(chkstr);
        this.i = i;
    }
    public String toString() {
        try {
            return String.format("%s?callback=function&action=login&username=%s&password=%s&ac_id=5&ip=%s&chksum=%s&info=%s&n=200&type=1&os=Windows+10&name=Windows&double_stack=0&_=%d",
                    domain,
                    URLEncoder.encode(username,"UTF-8"),
                    URLEncoder.encode("{MD5}"+hmd5,"UTF-8"),
                    URLEncoder.encode(ip,"UTF-8"),
                    URLEncoder.encode(chksum,"UTF-8"),
                    URLEncoder.encode(i,"UTF-8"),
                    System.currentTimeMillis());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
