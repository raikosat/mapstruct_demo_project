package com.spring.ts.maptruct.aop;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import com.spring.ts.maptruct.dtos.BaseDTO;
import com.spring.ts.maptruct.helper.ServiceHelper;
import com.spring.ts.maptruct.helper.ServiceHelperConstant;
import com.spring.ts.maptruct.util.FunctionUtil;

@Component
@Aspect
public class ServiceInitialize {
  private static final Logger logger = LoggerFactory.getLogger(ServiceInitialize.class);

  @Autowired
  private ApplicationContext context;
  
  @Before("within(com.spring.ts.maptruct.services.impl.*)")
  public void logBeforeAllMethods(JoinPoint joinPoint) {
    try {
      var objTarget = joinPoint.getTarget();
      var serviceHelper = objTarget.getClass().getAnnotation(ServiceHelper.class);

      if (serviceHelper == null) {
        return;
      }

      if (!serviceHelper.value().equals(ServiceHelperConstant.NONE)
          && !serviceHelper.value().equals(ServiceHelperConstant.MANUAL)) {
        if (serviceHelper.value().equals(ServiceHelperConstant.ALL)
            || serviceHelper.value().equals(ServiceHelperConstant.REPOSITORY)) {
          var beanRepositoryName = FunctionUtil.getBeanRepository(objTarget.getClass());
          var beanRepository = context.getBean(beanRepositoryName);
          if (beanRepository instanceof JpaRepository) {
            objTarget.getClass().getMethod(FunctionUtil.INIT_REPOSTIORY, JpaRepository.class)
                .invoke(objTarget, beanRepository);
          }
        }

        if (serviceHelper.value().equals(ServiceHelperConstant.ALL)
            || serviceHelper.value().equals(ServiceHelperConstant.MAPPER)) {
          var mapperInstanceName = FunctionUtil.getBeanMapper(objTarget.getClass());
          Class<?> clazz = Class.forName(mapperInstanceName);
          var objMapper = clazz.getDeclaredField(FunctionUtil.INSTANCE_MAPPER).get(clazz);
          if (objMapper != null) {
            objTarget.getClass().getMethod(FunctionUtil.INIT_MAPPER, clazz.getInterfaces())
                .invoke(objTarget, objMapper);
          }
        }
      } else if (!serviceHelper.value().equals(ServiceHelperConstant.MANUAL)) {
        objTarget.getClass().getMethod(FunctionUtil.INIT_MAPPER).invoke(objTarget);
        objTarget.getClass().getMethod(FunctionUtil.INIT_REPOSTIORY).invoke(objTarget);
      }

      Class<?> specificDTO = FunctionUtil.getTypeDTO(objTarget.getClass());
      Arrays.asList(joinPoint.getArgs()).forEach(arg -> {
        if (arg instanceof BaseDTO && arg.getClass().isAssignableFrom(specificDTO)) {
          try {
            objTarget.getClass().getMethod(FunctionUtil.SETTER_DTO, BaseDTO.class)
                .invoke(objTarget, arg);
          } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
              | NoSuchMethodException | SecurityException e) {
            logger.debug(e.getMessage());
          }
        }
      });
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException | ClassNotFoundException
        | NoSuchFieldException e) {
      logger.debug(e.getMessage());
    }
  }
}
