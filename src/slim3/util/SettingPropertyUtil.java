package slim3.util;

import java.io.IOException;
import java.util.Properties;

public class SettingPropertyUtil {

    private static Properties props = new Properties();

    static {
        try {
            props.load(AuthPropertyUtil.class
                .getClassLoader()
                .getResourceAsStream("settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException("settings.propertiesの読み込みに失敗しました。");
        }
    }

    private SettingPropertyUtil() {
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }
}
