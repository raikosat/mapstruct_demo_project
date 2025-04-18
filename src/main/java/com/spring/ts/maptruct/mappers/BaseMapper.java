package com.spring.ts.maptruct.mappers;

import com.spring.ts.maptruct.dtos.BaseNonAuditDTO;
import com.spring.ts.maptruct.entities.BaseNonAuditEntity;

public interface BaseMapper<D extends BaseNonAuditDTO, E extends BaseNonAuditEntity> {

  E toEntity(D dto);

  D toDto(E entity);
}
