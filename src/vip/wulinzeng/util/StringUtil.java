package vip.wulinzeng.util;

import java.util.Date;
import java.util.List;

/**
 * 字符串工具类
 * @author 2230
 *
 */
public class StringUtil {
	
	/**
	 * 将给定的list按照指定的分隔符分割成字符串返回
	 * @param list
	 * @param split 分割符
	 * @return
	 */
	public static String joinString(List<Long> list,String split){
		String ret = "";
		for(Long l:list){
			ret += l + split;
		}
		if(!"".equals(ret)){
			ret = ret.substring(0,ret.length() - split.length());
		}
		return ret;
	}
	
	/**
	 * 生成学号
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static String generateSn(String prefix,String suffix){
		return prefix + new Date().getTime() + suffix;
	}
}

