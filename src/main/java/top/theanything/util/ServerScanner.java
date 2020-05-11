package top.theanything.util;

import org.junit.Test;
import top.theanything.anno.Controller;
import top.theanything.config.BasicConfig;
import top.theanything.core.action.AbstractAction;

import java.io.File;
import java.util.*;

/**
 * @author zhou
 * @Description
 * @createTime 2020-04-13
 */
public class ServerScanner {
    public static String suffixPath = BasicConfig.path;
    public static String packagePath = BasicConfig.packagePath;
    public static List<Class> classes = new ArrayList<>();          //保存指定路径下的Controller
    public static List<String> classesName = new ArrayList<>();  // 只有class的名字，Class.forName 不要.class
    private static ServerScanner scanner = new ServerScanner();

    /**
     * 初始化处理器映射器 {@link top.theanything.core.base.Trie}
     */
    public static void scaner(){
        scanner.findFile();
        scanner.scanController();
        ActionUtil.refresh(classes);

    }

    /**
     * 将指定路径下的Controller保存到 {@link #classes}
     * 需要有
     * 1、是{@link Controller}注解
     * 2、继承了{@link AbstractAction}
     */
    private void scanController() {
        classesName.forEach(cn->{
            try {
                Class clazz = Class.forName(packagePath + cn);
                if (clazz.getAnnotation(Controller.class) != null
                        && clazz.getClass().isInstance(AbstractAction.class)){
                    classes.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 递归拿到目录下所有class 及 子包下的class。保存在className中
     */
    private void findFile(){

        String path = ServerScanner.class.getResource("/").getPath()+suffixPath;
        scanClassPath(path,"");
//      System.out.println(classeName);
    }

    /**
     * 拿到所有的class
     * @param path   文件路径
     * @param son  子包
     */
    private void scanClassPath(String path,String son){
        File file = new File(path);
        if(file.isDirectory()){
            for(File f : file.listFiles()){
                if(f.isDirectory()){
                    scanClassPath(f.getPath(),son+f.getName()+".");
                }else{
                    String name = f.getName();
                    classesName.add( son+name.substring(0,name.indexOf(".")) ); //反射创建对象记得加 '.'
                }
            }
        }else{
            String name = file.getName();
            classesName.add( son+name.substring( 0 , file.getName().indexOf(".") ) );
        }

    }

}