package com.aoto.systemutil.domain.repository.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.aoto.systemutil.domain.repository.pojo.Domain;
@Mapper
public interface IDomainIbaitsDao{
	    @Select("select *  from sys_domains where businesstype=#{type}")
		public List<Domain> queryDomainByBusinessType(@Param("type") String type);
}
