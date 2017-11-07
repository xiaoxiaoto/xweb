package com.aoto.systemutil.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanUtil {
	/**  
	* @Title: copyBean  
	* @Description: 复制bean 
	* @param bean
	* @return  T
	* @throws  
	*/ 
	@SuppressWarnings("unchecked")
	public  static <T> T copyBean(T bean) {
		T result = null;
		Class<? extends Object> clazz = bean.getClass();
		try {
			result = (T) clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				for (Field field : fields) {
					Class<?> fieldType = field.getType();
					String fieldName = field.toString().substring(field.toString().lastIndexOf(".") + 1);
					String capitalize = capitalize(fieldName);
					String setMethodName = "set" + capitalize;
					String getMethodName = "get" + capitalize;
					Method setMethod = clazz.getDeclaredMethod(setMethodName,fieldType);
					Method getMethod = clazz.getDeclaredMethod(getMethodName);
					if(setMethod!=null&&getMethod!=null){
						Object getInvoke = getMethod.invoke(bean);
						setMethod.invoke(result,getInvoke);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	 
	 /**
	     * 方法描述：将属性首字母大写
	     *
	     * @param fieldName
	     * @return String
	     */
	    public static String capitalize(String fieldName) {
	        String newfieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	        return newfieldName;
	    }
}
