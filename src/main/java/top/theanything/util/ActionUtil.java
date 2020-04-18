package top.theanything.util;

import top.theanything.anno.Controller;
import top.theanything.anno.RequestMapping;
import top.theanything.core.base.Trie;
import top.theanything.core.enums.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @Description
 * 充当处理器映射器的功能 {@link #ActionMap}
 * 利用字典树保存路由信息 {@link Trie}
 * @createTime 2020-04-12
 */
public class ActionUtil {

	public static Map<HttpMethod, Trie> ActionMap = new HashMap<>();

	/**
	 * 使用{@link #ActionMap} 保存映射信息
	 * @param classes controller包下的所有class
	 */
	public static void refresh(List<Class> classes){
		classes.forEach(e -> {
			System.out.println("扫描" + e.getName());
			Method[] methods = e.getMethods();
			for (Method method : methods) {
				RequestMapping mapping = method.getAnnotation(RequestMapping.class);
				if (mapping != null) {
					if (ActionMap.get(mapping.method()) == null) {
						ActionMap.put(mapping.method(), new Trie());
					}
					Trie trie = ActionMap.get(mapping.method());
					System.out.println("加载   "+mapping.method()+"  "+mapping.value()+"  "+method);
					trie.insert(mapping.value(), method);
				}
			}
		});
		printSuccess();
	}

	private static void printSuccess(){
		if(ActionMap.get(HttpMethod.GET) != null)
			System.out.println("成功初始化GET请求"+ActionMap.get(HttpMethod.GET).getCount()+"个");
		if(ActionMap.get(HttpMethod.POST) != null)
			System.out.println("成功初始化POST请求"+ActionMap.get(HttpMethod.POST).getCount()+"个");
	}
}
