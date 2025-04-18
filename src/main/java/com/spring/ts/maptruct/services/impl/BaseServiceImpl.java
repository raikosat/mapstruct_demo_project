package com.spring.ts.maptruct.services.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.ts.maptruct.dtos.BaseNonAuditDTO;
import com.spring.ts.maptruct.entities.BaseNonAuditEntity;
import com.spring.ts.maptruct.mappers.BaseMapper;
import com.spring.ts.maptruct.services.BaseService;

public class BaseServiceImpl<R extends JpaRepository, M extends BaseMapper, D extends BaseNonAuditDTO, E extends BaseNonAuditEntity>
    implements BaseService<D, E> {

  protected R repository;
  protected M mapper;
  protected D dto;
  protected E entity;

  public M getMapper() {
    return mapper;
  }

  public void initRepository(R repository) {
    this.repository = repository;
  }

  public void initMapper(M mapper) {
    this.mapper = mapper;
  }
  
  public void setDTO(D dto) {
    this.dto = dto;
  }

  public void setEntity(E entity) {
    this.entity = entity;
  }
}
