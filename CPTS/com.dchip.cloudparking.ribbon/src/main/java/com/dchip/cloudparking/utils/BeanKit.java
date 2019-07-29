package com.dchip.cloudparking.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;


public class BeanKit {
	//拷贝bean列表
		public static <T> List<T> copyBeanList(List<?> sourceList,Class<T> targetObj){
			List<T> rs = new ArrayList<T>();
			if(sourceList == null || sourceList.size() ==0) {
				return rs;
			}
			for (Object object : sourceList) {
				if(object != null) {
					T target;
					try {
						target = targetObj.newInstance();
						BeanUtils.copyProperties(object, target);
						rs.add(target);
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			return rs;
		}
		
		//将集合中的Map转换成bean
		
		public static <T> List<T> changeToListBean(List<Map<String,Object>> list,Class<T> bean) throws IllegalArgumentException, IllegalAccessException, InstantiationException{
			List<T> newList = new ArrayList<T>();
			for (Map<String,Object> map : list) {
				//生成对象实例
				T obj = bean.newInstance();
				String[] properties =  map.keySet().toArray(new String[ map.keySet().size()]);
				Field[] fields = bean.getDeclaredFields();
				//给属性赋值
				for (String property : properties) {
					for (Field field : fields) {
						if(property.equals(field.getName())) {
							field.setAccessible(true);
							field.set(obj, map.get(property));
						}
					}
				}
				newList.add(obj);
			}
			return newList;
		}
		
		//将record转成bean
		public static <T> T changeRecordToBean(Map<String,Object> record,Class<T> bean) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
			String[] columNames = record.keySet().toArray(new String[record.keySet().size()]);
			T obj = bean.newInstance();
			for (String name : columNames) {
				for (Field field : bean.getDeclaredFields()) {
					if(name.equals(field.getName())) {
						field.setAccessible(true);
						field.set(obj, record.get(name));
					}
				}
			}
			return obj;
		}
}
