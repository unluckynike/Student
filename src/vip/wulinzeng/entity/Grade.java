package vip.wulinzeng.entity;

import org.springframework.stereotype.Component;

/**
 * �꼶
 * @author 22304
 *
 */
@Component
public class Grade {

	private long id;
	private String name;
	private String remark;//��ע
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
