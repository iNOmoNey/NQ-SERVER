package top.theanything.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @param
 * @author zhou
 * @Description
 * @createTime 2020-03-20
 */
public class CommonUtils {
    private final static String BANNER_SUFFIX = "../../src/main/resources/banner.txt";
    private final static String PROPERTIES_PATH = "config.properties";
    private static Properties properties = new Properties();
    //对外使用的工具类
    private static  CommonUtils commonUtils = new CommonUtils();

    private CommonUtils(){
        this(PROPERTIES_PATH);
    }
    private CommonUtils(String path){
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printSuccess() throws IOException {

        String path = this.getClass().getClassLoader().getResource("").getPath();

        FileInputStream fis = new FileInputStream(path+BANNER_SUFFIX);
        byte[] b = new byte[1024];
        int len=0;
        while( (len = fis.read(b)) != -1){
            System.out.println(new String(b,0,len));
        }
        fis.close();
        System.out.println("启动成功！");
        System.out.println("POST请求测试:http://localhost/login");
        System.out.println("挂载的网站:http://localhost/shortUrl");
    }

    public static CommonUtils getInstance(){
        return commonUtils;
    }

    public Object getValue(String key) {
        return properties.getProperty(key.trim());
    }
}
