package com.dchip.cloudparking.wechat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.ResourceUtils;

public  class  MessageUtil {
	 /**
	   * 读取国际化文件里面的变量值
	   * 
	   * @param msgCode 变量名称
	   * @param args 参数
	   * @return
	   */
	  public static String loadMessage(String msgCode, Object... args) {
		  try {
			  ResourceBundleMessageSource messageSource  = new ResourceBundleMessageSource();
			  String dir =  ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX+"messages").getPath();
			  File[] files =  new File(dir).listFiles();
			  if(files == null || files.length == 0) {
				  return "";
			  }
			  List<String> propertyNames = new ArrayList<String>();
			  for (File file : files) {
				  String name = file.getName().substring(0, file.getName().indexOf("."));
				  propertyNames.add("messages/"+name);
			}
			messageSource.setDefaultEncoding("UTF-8");
			messageSource.setBasenames(propertyNames.toArray(new String[propertyNames.size()]));
		    messageSource.setUseCodeAsDefaultMessage(false);
		    return messageSource.getMessage(msgCode, args, Locale.ROOT);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	  }
	  
	  public static String loadMessage(String msgCode) {
		  try {
			  ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
			  String dir =  ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX+"messages").getPath();
			  File[] files =  new File(dir).listFiles();
			  if(files == null || files.length == 0) {
				  return "";
			  }
			  List<String> propertyNames = new ArrayList<String>();
			  for (File file : files) {
				  String name = file.getName().substring(0, file.getName().indexOf("."));
				  propertyNames.add("messages/"+name);
			}
			  messageSource.setDefaultEncoding("UTF-8");
			  messageSource.setBasenames(propertyNames.toArray(new String[propertyNames.size()]));
			  messageSource.setUseCodeAsDefaultMessage(false);
			  return messageSource.getMessage(msgCode, null, Locale.ROOT);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "";
		}
	  }
	  
	  public static Map<String,Object> loadAllPropertiesAsMap(){
		  try {
			  Map<String,Object> properties = new HashMap<String,Object>();
			  String dir = MessageUtil.class.getResource("/").getPath()+"messages";
			  File[] files =  new File(dir).listFiles();
			  if(files == null || files.length == 0) { 
				  return properties;
			  }
			  for (File file : files) {
				  Properties props = new Properties();
					FileInputStream  in = new FileInputStream(file);
					props.load(in);
					@SuppressWarnings("rawtypes")
					Enumeration en=props.propertyNames();
					while (en.hasMoreElements()) {
			            String key=(String) en.nextElement();
			            String property=props.getProperty(key);
			            properties.put(key, property);
			        }
			}
			  return properties;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new HashMap<String,Object>();
		}
	  }
}
