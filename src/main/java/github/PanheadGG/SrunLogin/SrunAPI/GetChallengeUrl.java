package github.PanheadGG.SrunLogin.SrunAPI;

import static github.PanheadGG.SrunLogin.Main.GLOBAL_DOMAIN_DOMAIN;

public class GetChallengeUrl {
    private String username;
    private String ip;
    public GetChallengeUrl(String username, String ip){
        this.username = username;
        this.ip = ip;
    }

    public String toString() {
        return String.format("%s/cgi-bin/get_challenge?callback=function&username=%s&ip=%s&_=%d",
                GLOBAL_DOMAIN_DOMAIN, username, ip, System.currentTimeMillis());
    }
}

