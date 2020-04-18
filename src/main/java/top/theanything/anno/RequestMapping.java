package top.theanything.anno;





import top.theanything.core.enums.HttpMethod;

import java.lang.annotation.*;

/**
 * @author zhou
 * @Description
 * @createTime 2020-04-12
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestMapping {
	/**
	 * 请求路径
	 */
	String value() default "/";

	/**
	 * 请求方法  实现restful风格
	 * @return
	 */
	HttpMethod method() default HttpMethod.GET;
}
