package vip.wulinzeng.entity;

import org.springframework.stereotype.Component;

/**
 * �༶
 * @author 22304
 *
 */
@Component
public class Clazz {

	private long id;
	private long gradeId;//�꼶id
	private String name;
	private String remark;//��ע
	
	public long getGradeId() {
		return gradeId;
	}
	public void setGradeID(long gradeId) {
		this.gradeId = gradeId;
	}
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
