package top.theanything.core.anno;

import top.theanything.core.filter.AbstractFilter;

import java.lang.annotation.*;

/**
 * @author zhou
 * @Description
 * @createTime 2020-05-15
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter {
	Class<? extends AbstractFilter>[] value();
}
