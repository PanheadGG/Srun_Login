package github.PanheadGG.SrunLogin.SrunAPI;

import github.PanheadGG.SrunLogin.Exception.NetworkException;
import github.PanheadGG.SrunLogin.Utils.DataMatcher;
import github.PanheadGG.SrunLogin.Utils.HmacUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Srun {
    static String enc = "s" + "run" + "_bx1";
    private static final String BASE64_CHARS = "LVoJPiCN2R8G90yg+hmFHuacZ1OWMnrsSTXkYpUq/3dlbfKwv6xztjI7DeBE45QA";

    public static String base64Encode(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.ISO_8859_1);
        StringBuilder output = new StringBuilder();
        int i = 0;
        while (i < bytes.length) {
            int char1 = bytes[i++] & 0xFF;
            int char2 = (i < bytes.length) ? bytes[i++] & 0xFF : -1;
            int char3 = (i < bytes.length) ? bytes[i++] & 0xFF : -1;

            int enc1 = char1 >> 2;
            int enc2 = ((char1 & 3) << 4) | ((char2 != -1) ? (char2 >> 4) : 0);
            int enc3, enc4;

            if (char2 == -1) {
                output.append(BASE64_CHARS.charAt(enc1));
                output.append(BASE64_CHARS.charAt(enc2));
                output.append("==");
                break;
            } else if (char3 == -1) {
                enc3 = ((char2 & 0x0F) << 2);
                output.append(BASE64_CHARS.charAt(enc1));
                output.append(BASE64_CHARS.charAt(enc2));
                output.append(BASE64_CHARS.charAt(enc3));
                output.append("=");
                break;
            } else {
                enc3 = ((char2 & 0x0F) << 2) | (char3 >> 6);
                enc4 = char3 & 0x3F;
                output.append(BASE64_CHARS.charAt(enc1));
                output.append(BASE64_CHARS.charAt(enc2));
                output.append(BASE64_CHARS.charAt(enc3));
                output.append(BASE64_CHARS.charAt(enc4));
            }
        }
        return output.toString();
    }

    public static String xEncode(String str, String key) {
        if (str.isEmpty()) {
            return "";
        }
        int[] v = s(str, true);
        int[] k = s(key, false);
        if (k.length < 4) {
            k = Arrays.copyOf(k, 4);
        }
        int n = v.length - 1;
        int z = v[n];
        int y = v[0];
        int c = 0x9E3779B9;
        int q = 6 + 52 / (n + 1);
        int d = 0;

        while (q-- > 0) {
            d += c;
            int e = (d >>> 2) & 3;
            int p;
            for (p = 0; p < n; p++) {
                y = v[p + 1];
                int m = (z >>> 5 ^ y << 2) + ((y >>> 3 ^ z << 4) ^ (d ^ y)) + (k[(p & 3) ^ e] ^ z);
                v[p] = v[p] + m;
                z = v[p];
            }
            y = v[0];
            int m = (z >>> 5 ^ y << 2) + ((y >>> 3 ^ z << 4) ^ (d ^ y)) + (k[(p & 3) ^ e] ^ z);
            v[n] = v[n] + m;
            z = v[n];
        }
        return l(v, false);
    }

    private static int[] s(String a, boolean b) {
        byte[] bytes = a.getBytes(StandardCharsets.ISO_8859_1);
        int[] v = new int[(bytes.length + 3) / 4];
        for (int i = 0; i < bytes.length; i++) {
            v[i >> 2] |= (bytes[i] & 0xFF) << ((i & 3) * 8);
        }
        if (b) {
            int[] newV = Arrays.copyOf(v, v.length + 1);
            newV[v.length] = bytes.length;
            return newV;
        }
        return v;
    }

    private static String l(int[] a, boolean b) {
        StringBuilder sb = new StringBuilder();
        int c = (a.length - 1) << 2;
        if (b) {
            int m = a[a.length - 1];
            if (m > c) {
                return null;
            }
            c = m;
        }
        for (int value : a) {
            sb.append((char) (value & 0xFF));
            sb.append((char) ((value >> 8) & 0xFF));
            sb.append((char) ((value >> 16) & 0xFF));
            sb.append((char) ((value >> 24) & 0xFF));
        }
        return b ? sb.substring(0, c) : sb.toString();
    }

    // 示例JSON序列化方法，需根据实际情况实现
    public static String json(Object d) {
        // 实际应用中应使用JSON库如Jackson/Gson
        return "{\"key\":\"value\"}"; // 示例
    }

    public static String chksum(String d) {
        return DigestUtils.sha1Hex(d);
    }

    public static String pwd(String d, String k) {
        return HmacUtil.encrypt(d, k, HmacUtil.HMAC_MD5);
    }

    public static String info(String d, String k) {
        return "{SRBX1}"+base64Encode(xEncode(d, k));
    }

    public static String getConnectInfo() throws NetworkException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(RadUserInfoUrl.getUrl()).get().build();
        try(Response response = client.newCall(request).execute()){
            if(response.body() == null) throw new NetworkException("响应体为空");
            return response.body().string();
        } catch (IOException e) {
            throw new NetworkException("网络请求出错");
        }
    }

    public static boolean login(String username,String password,String ip) throws NetworkException{
        OkHttpClient client = new OkHttpClient();
        String token = "";
        GetChallengeUrl getChallengeUrl = new GetChallengeUrl(username,ip);

        Request request = new Request.Builder()
                .url(getChallengeUrl.toString())
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.body() == null) {
                throw new NetworkException("响应体为空");
            }
            String responseBody = response.body().string();
            System.out.println(responseBody);
            DataMatcher dataMatcher = new DataMatcher(responseBody);
            token = dataMatcher.getString("challenge");
        } catch (IOException e) {
            throw new NetworkException("网络请求错误");
        }

        SrunPortalUrl srunPortalUrl = new SrunPortalUrl(username,password,ip,token);
        request = new Request.Builder()
                .url(srunPortalUrl.toString())
                .get()
                .build();
        try(Response response = client.newCall(request).execute()){
            if(response.body() == null) {
                throw new NetworkException("响应体为空");
            }
            System.out.println(response.body().string());
        }catch (IOException e){
            throw new NetworkException("网络请求错误");
        }

        try {
            DataMatcher matcher = new DataMatcher(getConnectInfo());
            String errorStr = matcher.getString("error");
            if(errorStr!=null&&errorStr.equalsIgnoreCase("ok")) return true;
        } catch (NetworkException e) {
            throw new NetworkException("网络请求错误");
        }
        return false;
    }

    public static boolean autoLogin(String username,String password){
        try{
            String info = Srun.getConnectInfo();
            System.out.println(info);
            DataMatcher matcher = new DataMatcher(info);
            String ip = matcher.getString("online_ip");
            if(Srun.login(username,password,ip)){
                System.out.println("登录成功");
                System.out.println(Srun.getConnectInfo());
                return true;
            }
        } catch (NetworkException e) {
            System.out.printf("出现错误，错误为: %s",e.getMessage());
        }
        return false;
    }

    public static boolean isLogon(){
        try {
            DataMatcher matcher = new DataMatcher(getConnectInfo());
            String errorStr = matcher.getString("error");
            if(errorStr!=null&&errorStr.equalsIgnoreCase("ok")) return true;
        } catch (NetworkException e) {
            return false;
        }
        return false;
    }

}

