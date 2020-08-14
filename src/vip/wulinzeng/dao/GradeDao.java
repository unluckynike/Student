package vip.wulinzeng.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import vip.wulinzeng.entity.Grade;

@Repository
public interface GradeDao {

	public int add(Grade grade);
	
	public List<Grade> findList(Map<String,Object> queryMap);
	
	public int getTotal(Map<String,Object> queryMap);
	
	public int edit(Grade grade);
	
	public int delete(String ids);
}
