package top.theanything.utils;

import top.theanything.core.anno.Filter;
import top.theanything.core.filter.AbstractFilter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @Description 对过滤器进行扫描
 * 第一次获取过滤器会对过滤器进行缓存
 * @createTime 2020-05-15
 */
public class FilterUtil {

	public static Map<Method, AbstractFilter> filterMap = new HashMap<>();

	/**
	 * 构造Filter执行链
	 * @param methods
	 */
	public static void refresh(List<Method> methods){
		System.err.println("=====开始扫描过滤器=====");
		methods.forEach( method ->{
			AbstractFilter abstractFilter = null;
			Filter filters = method.getAnnotation(Filter.class);
			Class<? extends AbstractFilter>[] filter = null;
			if(filters != null){
				filter = filters.value();
				for(Class f : filter){
					try {
						if(abstractFilter == null){
							abstractFilter = (AbstractFilter) f.newInstance();
							filterMap.put(method,abstractFilter);   //method 和 Filter执行链对应
							System.out.println(method.getName()+"()"+" 添加过滤器 "+f.getName());
						}else{
							abstractFilter.setNext((AbstractFilter) f.newInstance());
							abstractFilter = abstractFilter.getNext();
							System.out.println(method.getName()+"()"+" 添加过滤器 "+f.getName());
						}
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public static AbstractFilter getFilterChain(Method method){
		return filterMap.get(method);
	}
}
