package com.spring.ts.maptruct.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionUtil {
  private static final Logger logger = LoggerFactory.getLogger(FunctionUtil.class);
  
  private static final String PACKAGE_MAPPER = "com.spring.ts.maptruct.mappers.";
  private static final String SUFFIX_MAPPER = "Mapper";
  public static final String INSTANCE_MAPPER = "INSTANCE";
  public static final String INIT_MAPPER = "initMapper";


  public static final String INIT_REPOSTIORY = "initRepository";
  private static final String SUFFIX_REPOSTIORY = "Repository";
  private static final String PACKAGE_REPOSITORY = "com.spring.ts.maptruct.repositories.";
  private static final String NAME_REPOSITORY = "BaseRepository";

  private static final String PACKAGE_MODEL = "com.spring.ts.maptruct.entities.";
  private static final String PACKAGE_DTO = "com.spring.ts.maptruct.dtos.";
  private static final String NAME_DTO = "BaseDTO";
  public static final String SETTER_DTO = "setDTO";


  private static final String partern_date = "yyyy-MM-ddd";
  private static final String partern_date_time = "yyyy-MM-dd HH:mm:ss.S";
  
  private static String getCorrectMethodName(String strCLass) {
    if (strCLass != null && strCLass.length() > 1) {
      var firstChar = strCLass.subSequence(0, 1).toString().toLowerCase();
      var remainChar = strCLass.subSequence(1, strCLass.length());
      return firstChar + remainChar;
    }
    return strCLass;
  }

  private static String getCorrectClassName(String strCLass) {
    if (strCLass != null && strCLass.length() > 1) {
      var firstChar = strCLass.subSequence(0, 1).toString().toUpperCase();
      var remainChar = strCLass.subSequence(1, strCLass.length());
      return firstChar + remainChar;
    }
    return strCLass;
  }

  public static String getBeanName(Class clazz) {
    var packageName = clazz.getPackageName();
    var clazzName = clazz.getName();
    clazzName = clazzName.replace(packageName + ".", "");
    clazzName = clazzName.replace("ServiceImpl", "");
    return getCorrectMethodName(clazzName);
  }


  public static String getBeanRepository(Class clazz) {
    var beanName = getTypeRepo(clazz);
    return getCorrectMethodName(beanName.getSimpleName());
  }


  public static String getBeanMapper(Class clazz) {
    var beanName = getBeanNameWtihClass(clazz);
    return beanName + SUFFIX_MAPPER;
  }

  public static String getBeanNameWtihClass(Class clazz) {
    var packageName = clazz.getPackageName();
    var clazzName = clazz.getName();
    clazzName = clazzName.replace(packageName + ".", "");
    clazzName = clazzName.replace("ServiceImpl", "");
    return PACKAGE_MAPPER + getCorrectClassName(clazzName);
  }

  public static Type[] getParameterizedType(Class clazz) {
    return ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
  }

  public static Class getTypeDTO(Class clazz) {
    Class rsResult = null;
    try {
      var lstParameterizedType = getParameterizedType(clazz);
      Class abstractDTO = Class.forName(PACKAGE_DTO + NAME_DTO);
      for (Type type : lstParameterizedType) {
        Class c = (Class) type;
        Class p = (Class) c.getGenericSuperclass();
        if (p != null && p.equals(abstractDTO)) {
          rsResult = (Class) type;
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return rsResult;
  }

  public static Class getTypeRepo(Class clazz) {
    Class rsResult = null;
    try {
      var lstParameterizedType = getParameterizedType(clazz);
      Class abstractRepo = Class.forName(PACKAGE_REPOSITORY + NAME_REPOSITORY);
      for (Type type : lstParameterizedType) {
        Class c = (Class) type;
        Class[] p = c.getInterfaces();
        if (p != null && p.length > 0) {
          Class parent = p[0];
          if (parent.equals(abstractRepo)) {
            rsResult = (Class) type;
          }
        }
      }
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return rsResult;
  }

  public static Date toDate(String value) {
    if (value == null) {
      return null;
    }
    try {
      Date date = new SimpleDateFormat(partern_date).parse(value);
      return date;
    } catch (ParseException e) {
      return null;
    }
  }

  public static Date toDateTime(String value) {
    if (value == null) {
      return null;
    }
    try {
      Date date = new SimpleDateFormat(partern_date_time).parse(value);
      return date;
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * get getter method from fieldname.<br>
   *
   * @param fieldName
   * @return getterName
   * @throws Exception
   */
  public static String buildGetMethod(String fieldName, String fieldType) throws Exception {
    if (fieldName == null || fieldName.length() <= 0) {
      throw new IllegalArgumentException();
    } else {
      String firstChar = fieldName.substring(0, 1);
      String fileNameRemain = fieldName.substring(1, fieldName.length());
      if (fieldType.equals("boolean")) {
        return "is" + firstChar.toUpperCase() + fileNameRemain;
      }
      return "get" + firstChar.toUpperCase() + fileNameRemain;
    }
  }
  
  public static Class getModelClassFromRepo(final Type[] types) {
    try {
      var model = Stream.of(types.clone())
          .filter(type ->  type.getTypeName().indexOf("jp.sbpayment.bpr.bl.repository") >= 0)
          .map(type -> {
            return PACKAGE_MODEL + type.getTypeName().replaceAll(SUFFIX_REPOSTIORY, "").replaceAll(PACKAGE_REPOSITORY, "");
          }).findFirst().orElseThrow(() -> new InternalError("Can not found model entity"));
      return  Class.forName(model);
    } catch (ClassNotFoundException e) {
      logger.error("Can not found model entity", e);
      throw new InternalError("Can not found model entity");
    }
  }

  public static String removeTabString(String data) {
    try {
      if (data == null || data.isEmpty()) {
        return null;
      }
      data = data.replaceAll("[\t ]+", " ");
      return data;
    } catch (Exception e) {
      return null;
    }
  }
}
