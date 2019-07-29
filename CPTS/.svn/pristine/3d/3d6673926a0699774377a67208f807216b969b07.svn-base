package com.dchip.cloudparking.web.utils;


import java.lang.reflect.InvocationTargetException;

import com.querydsl.core.types.OrderSpecifier;

/**
 * 自定义排序类
 * @author twd
 *
 */
public class Sort {

	//排序的字段名
	private String sortName;
	//排序方向
	private Direction direction;
	//排序字段所属的对象
	private Object target;
	
	/**
	 * 
	 * @param sortName 排序的字段名
	 * @param direction 排序方向
	 */
	public Sort(String sortName, Direction direction){
		this.sortName=sortName;
		this.direction=direction;
	}		
	
	/**
	 * 
	 * @param sortName 排序的字段名
	 * @param direction 排序方向
	 * @param target 排序字段所属的对象
	 */
	public Sort(String sortName, Direction direction,Object target){
		this(sortName, direction);
		this.target = target;
	}	


	/**
	 * 
	 * @param sortName 排序的字段名
	 * @param direction 排序方向   DESC/ASC
	 */
	public Sort(String sortName, String direction){
		this.sortName=sortName;
		if(direction.toUpperCase().equals("ASC")) {
			this.direction=Direction.ASC;
		}else if(direction.toUpperCase().equals("DESC")) {
			this.direction=Direction.DESC;
		}
	}	

	/**
	 * 
	 * @param sortName 排序的字段名
	 * @param String 排序方向
	 * @param target 排序字段所属的对象
	 */
	public Sort(String sortName, String direction,Object target){
		this(sortName, direction);
		this.target = target;
	}

	

	/**	
	 * @return
	 */
	public OrderSpecifier<?> getOrderSpecifier() {
		if(target==null) {
			return null;
		}
		//排序的字段
		Object field = null;
		//需要排序的字段的类型
		Class<? extends Object> fieldClass = null;		
		OrderSpecifier<?> orderSpecifier = null; 
		String getFieldName = "get"+sortName.subSequence(0, 1).toString().toUpperCase()+sortName.substring(1);
		//获取需要排序的字段
		try {
			try {
				field=target.getClass().getMethod(getFieldName,new Class[]{}).invoke(target, new Object[] {});
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fieldClass = target.getClass().getField(sortName).getType();
		} catch (NoSuchFieldException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//调用相应字段的排序方法
		
		if(direction==Direction.DESC) {
			try {
				//通过反射直接读取属性不能得到我们想要的对象，通过方法调用可以
				orderSpecifier=(OrderSpecifier<?>)fieldClass.getMethod("desc",new Class[]{}).invoke(field, new Object[] {});
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			try {
				orderSpecifier=(OrderSpecifier<?>)fieldClass.getMethod("asc",new Class[]{}).invoke(field, new Object[]{});
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return orderSpecifier;
	}
	
	/**	
	 * @param target  排序字段所属的对象
	 * @return
	 */
	public OrderSpecifier<?> getOrderSpecifier(Object target) {
		this.target=target;
		return getOrderSpecifier();
	}
	
	/**
	 * 排序的方向
	 * @author twd
	 *
	 */
	public static enum Direction{
		DESC,
		ASC
	};
	
	public String getSortName() {
		return sortName;
	}


	public Direction getDirection() {
		return direction;
	}
	

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}


	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
