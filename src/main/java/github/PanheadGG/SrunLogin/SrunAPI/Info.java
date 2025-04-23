package github.PanheadGG.SrunLogin.SrunAPI;

public class Info {
    private String username;
    private String password;
    private String ip;
    private String acid;
    private String enc_ver;

    public Info() {
    }

    public Info(String username, String password, String ip, String acid, String enc_ver) {
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.acid = acid;
        this.enc_ver = enc_ver;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setAcid(String acid) {
        this.acid = acid;
    }

    public void setEncVer(String enc_ver) {
        this.enc_ver = enc_ver;
    }

    public String toString() {
        return String.format("{\"username\":\"%s\",\"password\":\"%s\",\"ip\":\"%s\",\"acid\":\"%s\",\"enc_ver\":\"%s\"}",
                username, password, ip, acid, enc_ver);
    }

}
