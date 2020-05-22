package top.theanything.web.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import top.theanything.config.BasicConfig;
import top.theanything.core.action.AbstractAction;
import top.theanything.web.utils.SwitchNumTo62GarbleUtil;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zhou
 * @Description
 * @createTime 2020-05-21
 */
public class ShortUrlService {
	private static AtomicLong integer = new AtomicLong(100);
	public static Cache<String, String> urlCache = CacheBuilder.newBuilder()
																.expireAfterAccess(7, TimeUnit.DAYS)
																.build();

	/**
	 * 生成短链
	 *
	 * @param orgUrl
	 * @return
	 */
	public String genShortUrl(String orgUrl) {
		String shortUrl;
		//判断缓存中是否已经有了
		Set<Map.Entry<String, String>> entries = urlCache.asMap().entrySet();
		for (Map.Entry<String, String> map : entries){
			if( map.getValue().equals(orgUrl) ){
				return BasicConfig.doMain+map.getKey();
			}
		}
		//获取短链域名
		String shortUrlDomain = BasicConfig.doMain;
		//生成自增随机序列
		Long entityId = integer.getAndAdd(1);
		//生成对应的keyid
		String keyid = SwitchNumTo62GarbleUtil.convert10to62(entityId, 4);
		shortUrl = shortUrlDomain +"?p="+ keyid;
		/**
		 * shortUrl : ASFc
		 * orgURL : http://www.theanything.top
		 */
		urlCache.put(keyid,orgUrl);
		return shortUrl;
	}

	public String getRealPath(String keyid) {
		String realPath = urlCache.getIfPresent(keyid);
		return realPath;
	}
}
