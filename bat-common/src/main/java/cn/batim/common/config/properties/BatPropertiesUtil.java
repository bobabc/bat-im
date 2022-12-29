package cn.batim.common.config.properties;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bob
 */
@Slf4j
public class BatPropertiesUtil {
    private static BatProperties batConfig = null;
    private static final ConcurrentHashMap<String, BatProperties> HASH_MAP = new ConcurrentHashMap<>(16);

    private BatPropertiesUtil() {
    }

    public static BatProperties initDefault() {
        return init(BatProperties.CONFIG_FILE, "UTF-8");
    }
    public static BatProperties init(String fileName) {
        return init(fileName, "UTF-8");
    }

    public static BatProperties init(String fileName, String encoding) {
        BatProperties result = HASH_MAP.get(fileName);
        if (result == null) {
            result = new BatProperties(fileName, encoding);
            HASH_MAP.put(fileName, result);
            if (batConfig == null) {
                batConfig = result;
            }
        }

        return result;
    }

    public static BatProperties init(File file) {
        return init(file, "UTF-8");
    }

    public static BatProperties init(File file, String encoding) {
        BatProperties result = (BatProperties) HASH_MAP.get(file.getName());
        if (result == null) {
            result = new BatProperties(file, encoding);
            HASH_MAP.put(file.getName(), result);
            if (batConfig == null) {
                batConfig = result;
            }
        }

        return result;
    }

    public static BatProperties useless(String fileName) {
        BatProperties previous = HASH_MAP.remove(fileName);
        if (batConfig == previous) {
            batConfig = null;
        }

        return previous;
    }

    public static void clear() {
        batConfig = null;
        HASH_MAP.clear();
    }

    public static BatProperties getBatConfig() {
        if (batConfig == null) {
            throw new IllegalStateException("请先加载配置文件！");
        } else {
            return batConfig;
        }
    }

    public static BatProperties getBatConfig(String fileName) {
        return (BatProperties) HASH_MAP.get(fileName);
    }

    public static String get(String key) {
        return getBatConfig().get(key);
    }

    public static String get(String key, String defaultValue) {
        return getBatConfig().get(key, defaultValue);
    }

    public static Integer getInt(String key) {
        return getBatConfig().getInt(key);
    }

    public static Integer getInt(String key, Integer defaultValue) {
        return getBatConfig().getInt(key, defaultValue);
    }

    public static Long getLong(String key) {
        return getBatConfig().getLong(key);
    }

    public static Long getLong(String key, Long defaultValue) {
        return getBatConfig().getLong(key, defaultValue);
    }

    public static Boolean getBoolean(String key) {
        return getBatConfig().getBoolean(key);
    }

    public static Boolean getBoolean(String key, Boolean defaultValue) {
        return getBatConfig().getBoolean(key, defaultValue);
    }

    public static boolean containsKey(String key) {
        return getBatConfig().containsKey(key);
    }
}
