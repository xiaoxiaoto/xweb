package com.aoto.systemutil.domain.repository.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aoto.systemutil.domain.repository.pojo.Domain;
@Repository
public interface IDomainJpaDao extends JpaRepository<Domain, Serializable>{
}
