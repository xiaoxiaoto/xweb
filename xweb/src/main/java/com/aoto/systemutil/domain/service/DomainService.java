package com.aoto.systemutil.domain.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aoto.systemutil.date.DateUtil;
import com.aoto.systemutil.domain.repository.dao.IDomainIbaitsDao;
import com.aoto.systemutil.domain.repository.dao.IDomainJpaDao;
import com.aoto.systemutil.domain.repository.pojo.Domain;




/**  
* @ClassName: IReligiousPlaceService  
* @Description: 值域管理    
* @date 2017年3月15日  
*    
**/ 
@Service
@Transactional
public class DomainService{
	@Autowired
	private IDomainIbaitsDao domainIbaitsDao;
	@Autowired
	private IDomainJpaDao domainJpaDao;
	
	 @Autowired  
	 private  JavaMailSender mailSender; 
	 
	 @Value("${spring.mail.username}")
	 private String sender;
	
	/**  
	* @Title: queryAllDmain  
	* @Description: 查询所有值域
	* @return  List<Domain>
	* @throws  
	*/ 
	//@Cacheable(value="cache:Domainms", key="'cache:Domainms:queryAllDomain'",unless="#result==null")//创建缓存
	public List<Domain> queryAllDmain() {
		return domainJpaDao.findAll();
	}
	
	
	/**  
	* @Title: queryAllDomainForTable  
	* @Description: 查询所有值域信息(前台数据展示) 
	* @return  Map<String,List<Domain>>
	* @throws  
	*/ 
	public Map<String, List<Domain>> queryAllDomainForTable() {
		Map<String, List<Domain>> result= new HashMap<>();
		result.put("data", domainJpaDao.findAll());		
		return result;
	}
	
	/**  
	* @Title: queryDomainPk  
	* @Description: 根据主键查询值域
	* @param id
	* @return  Domain
	* @throws  
	*/ 
	public Domain queryDomainPk(Serializable id) {
		if(id!=null){
			return domainJpaDao.findOne(id);
		}
		return null;
	}
	
	/**  
	* @Title: queryDomainByType  
	* @Description: 根据值域类型查询值域 
	* @param type
	* @return  List<Domain>
	* @throws  
	*/ 
	public List<Domain> queryDomainByDomainType(String type) {
		if(type!=null){
			return domainIbaitsDao.queryDomainByBusinessType(type);
		}
		return null;
	}

	/**  
	* @Title: saveDomain  
	* @Description: 增加值域
	* @param domain
	* @return  Map<String,Object>
	* @throws  
	*/ 
	//@CacheEvict(value="cache:Domainms", key="'cache:Domainms:queryAllDomain'")//设置缓存过期
	public Map<String, Object> saveDomain(Domain domain) {
		Map<String, Object> result=new HashMap<>();
		if(domain!=null){
			domain.setCreateDate(DateUtil.getCurrentDate("yyyy-MM-dd"));
			domain.setCreateUser(1);
			domain.setFlag("1");
			Domain save = domainJpaDao.save(domain);
			if(save!=null){
				result.put("result", 1);
				result.put("data", save);
			}else{
				result.put("result", -1);
			}
		}else{
			result.put("result", -1);
		}
		return result;
	}

	/**  
	* @Title: deleteDomain  
	* @Description: 删除值域
	* @param id
	* @return  Map<String,Object>
	* @throws  
	*/ 
	//@CacheEvict(value="cache:Domainms", key="'cache:Domainms:queryAllDomain'")//设置缓存过期
	public Map<String, Object> deleteDomain(Serializable id) {
		Map<String, Object> result=new HashMap<>();
		if(id!=null){
			Domain one = domainJpaDao.findOne(id);
			if(one!=null){
				try{
					domainJpaDao.delete(id);
					result.put("result", 1);
					result.put("data", one);
				}catch (Exception e) {
					result.put("result", -1);
				}
			}else{
				result.put("result", -1);
			}
		}else{
			result.put("result", -1);
		}
		return result;
	}

	/**  
	* @Title: modifyDomain  
	* @Description: 修改值域
	* @param domain
	* @return  Map<String,Object>
	* @throws  
	*/ 
	//@CacheEvict(value="cache:Domainms", key="'cache:Domainms:queryAllDomain'")//设置缓存过期
	public Map<String, Object> modifyDomain(Domain domain) {
		Map<String, Object> result=new HashMap<>();
		if(domain!=null){
			domain.setCreateDate(DateUtil.getCurrentDate("yyyy-MM-dd"));
			domain.setCreateUser(1);
			domain.setFlag("1");
			Domain save = domainJpaDao.save(domain);
			if(save!=null){
				result.put("result", 1);
				result.put("data", save);
			}else{
				result.put("result", -1);
			}
		}else{
			result.put("result", -1);
		}
		return result;
	}
	
}
