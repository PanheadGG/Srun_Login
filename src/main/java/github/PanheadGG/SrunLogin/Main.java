package github.PanheadGG.SrunLogin;

import github.PanheadGG.SrunLogin.SrunAPI.Srun;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Main {// 获取jar包所在目录的绝对路径
    static String basePath;

    static {
        try {
            String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            jarPath = java.net.URLDecoder.decode(jarPath, "UTF-8");
            // 处理IDEA调试路径
            if (jarPath.contains("target/classes")) {
                jarPath = jarPath.substring(0, jarPath.indexOf("target/classes"));
            } else if (jarPath.endsWith(".jar")) {
                jarPath = jarPath.substring(0, jarPath.lastIndexOf('/'));
            }
            basePath = new File(jarPath).getAbsolutePath();
        } catch (Exception e) {
            basePath = System.getProperty("user.dir");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Properties prop = new Properties();

        File config = new File(basePath, "config.properties");

        String username = "";
        String password = "";
        try {
            if (!config.exists()) {
                // 修改属性值
                prop.setProperty("username", "UsernameToEdit");
                prop.setProperty("password", "PasswordToEdit");

                // 保存属性到文件
                prop.store(Files.newOutputStream(config.toPath()), null);
                System.exit(0);
            }
            // 读取属性文件
            prop.load(Files.newInputStream(config.toPath()));
            // 获取属性值
            if (prop.getProperty("username")!=null) {
                username = prop.getProperty("username");
            } else System.exit(0);
            if (prop.getProperty("password")!=null) {
                password = prop.getProperty("password");
            } else System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        System.out.println("username: " + username);
        System.out.println("password: " + password);

        while (true) {
            try {
                if (Srun.isLogon()) {
                    System.out.println("网络连接在线,每五分钟检测一次");
                    Thread.sleep(1000 * 60 * 5);
                } else {
                    System.out.println("网络连接断开,正在重连...");
                    int failCount = 0;
                    while (!Srun.autoLogin(username, password)) {
                        failCount++;
                        System.out.printf("第%d次重连失败...\n", failCount);
                        if (failCount % 5 == 0) {
                            System.out.println("连续五次连接失败,十分钟后重连");
                            Thread.sleep(1000 * 60 * 10);
                            break;
                        } else {
                            Thread.sleep(1000 * 10);
                        }
                    }

                }
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }
}
