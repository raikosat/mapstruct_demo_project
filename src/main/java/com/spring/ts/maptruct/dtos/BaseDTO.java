package com.spring.ts.maptruct.dtos;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDTO extends BaseNonAuditDTO {

  private Date createdAt;
  private Date updatedAt;
}
