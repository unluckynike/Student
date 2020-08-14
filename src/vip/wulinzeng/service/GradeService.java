package vip.wulinzeng.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import vip.wulinzeng.entity.Grade;

@Service
public interface GradeService {
	
	public int add(Grade grade);
	
	public List<Grade> findList(Map<String,Object> queryMap);
	
	public int getTotal(Map<String,Object> queryMap);
	
	public int edit(Grade grade);
	
	public int delete(String ids);
}
