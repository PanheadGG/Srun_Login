package github.PanheadGG.SrunLogin.SrunAPI;

public class GetChallengeUrl {
    private final String domain = "http://210.43.112.9/cgi-bin/get_challenge";
    private String username;
    private String ip;
    public GetChallengeUrl(String username, String ip){
        this.username = username;
        this.ip = ip;
    }

    public String toString() {
        return String.format("%s?callback=function&username=%s&ip=%s&_=%d",
                domain, username, ip, System.currentTimeMillis());
    }
}

