package vip.wulinzeng.util;

import java.util.Date;
import java.util.List;

/**
 * �ַ���������
 * @author 2230
 *
 */
public class StringUtil {
	
	/**
	 * ��������list����ָ���ķָ����ָ���ַ�������
	 * @param list
	 * @param split �ָ��
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
	 * ����ѧ��
	 * @param prefix
	 * @param suffix
	 * @return
	 */
	public static String generateSn(String prefix,String suffix){
		return prefix + new Date().getTime() + suffix;
	}
}

