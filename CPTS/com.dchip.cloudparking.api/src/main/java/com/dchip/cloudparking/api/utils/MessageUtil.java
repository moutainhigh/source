package com.dchip.cloudparking.api.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ResourceUtils;

public  class  MessageUtil {
	
	private static final Logger log = LoggerFactory.getLogger(MessageUtil.class);

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
			  //String dir =  ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX+"messages").getPath();
			  log.info("*****************************");
			  log.info(System.getProperty("java.class.path"));
			  ///String dir =  ResourceUtils.getURL(ResourceUtils.CLASSPATH_URL_PREFIX+"messages").getPath();
			  //File[] files =  new File(dir).listFiles();
			  ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			  org.springframework.core.io.Resource[] resources = resolver.getResources(ResourceUtils.CLASSPATH_URL_PREFIX+"messages/*.properties");
			  if(resources == null || resources.length ==0) {
				  return "";
			  }
			  List<String> propertyNames = new ArrayList<String>();
			  for (Resource resourceFile : resources) {
				  int pos = resourceFile.getFilename().lastIndexOf(".");
				  String name = resourceFile.getFilename().substring(0, pos);
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
			  log.info("常量key:"+msgCode);
			  ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
			  ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			  org.springframework.core.io.Resource[] resources = resolver.getResources(ResourceUtils.CLASSPATH_URL_PREFIX+"messages/*.properties");
			  if(resources == null || resources.length ==0) {
				  return "";
			  }
			  List<String> propertyNames = new ArrayList<String>();
			  for (Resource resourceFile : resources) {
				  //lastIndexOf():返回指定字符在此字符串中最后一次出现处的索引，如果此字符串中没有这样的字符，则返回 -1
				  int pos = resourceFile.getFilename().lastIndexOf(".");
				  String name = resourceFile.getFilename().substring(0, pos);
				  propertyNames.add("messages/"+name);
			}
			  messageSource.setDefaultEncoding("UTF-8");
			  messageSource.setBasenames(propertyNames.toArray(new String[propertyNames.size()]));
			  messageSource.setUseCodeAsDefaultMessage(false);
			  log.info("指定key的常量："+messageSource.getMessage(msgCode, null, Locale.ROOT));
			  log.info("语言种类："+Locale.ROOT);
			  return messageSource.getMessage(msgCode, null, Locale.ROOT);
		} catch (Exception e) {
			log.info("读取常量文件失败:"+e);
			e.printStackTrace();
			return "";
		}
	  }
	  
	  public static Map<String,String> loadAllPropertiesAsMap(){
		  try {
			  Map<String,String> properties = new HashMap<String,String>();
			  ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			  org.springframework.core.io.Resource[] resources = resolver.getResources(ResourceUtils.CLASSPATH_URL_PREFIX+"messages/*.properties");
			  if(resources == null || resources.length ==0) {
				  return properties;
			  }
			  for (Resource resourceFile : resources) {
				  Properties props = new Properties();
					FileInputStream  in = new FileInputStream(resourceFile.getFile());
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
			return new HashMap<String,String>();
		}
	  }
}
