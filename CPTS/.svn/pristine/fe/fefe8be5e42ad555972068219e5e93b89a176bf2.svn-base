package com.dchip.cloudparking.wechat.utils;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
 
/**
 * 这个类是基于POI3.15的封装，可以更加方便地创建一个可导出到指定设备的excel
 * @author twd
 */
public class Excel
{	
	
	/**
	 * Excel文件接口
	 * @author twd
	 *
	 */
	public static interface ExcelFile{
		
		/**
		 * 
		 * @return 文件名
		 */
		public String fileName() ;

		/**
		 * 
		 * @return 表单名
		 */
		public String sheetName() ;		

		/**
		 * 列的名称(表头)
		 * @return
		 */
		public String[] headers() ;
		
		/**
		 * 数据源
		 * @return 建议List&ltjavabean>,List&ltLinkedHashMap>,List&ltLinkedList>,List&ltArrayList>等有序集合，已确保导出的数据的顺序与存入的顺序一致
		 */
		public List<? extends Object> data();
		
		/**
		 * 表头的单元格样式 
		 * @return  枚举ExcelStyle
		 */
		public ExcelStyle styleOfHead();
		/**
		 * 数据单元格的样式
		 * @return 枚举ExcelStyle
		 */
		public ExcelStyle styleOfBody();

		/**
		 * 时间格式，设定输出格式。格式出错或不填则默认为yyyy-MM-dd HH:mm:ss"
		 * @return
		 */
		public String pattern();

	}
	
	/**
	 *  文件名
	 */
	private String fileName;
	/**
	 *  表单名称
	 */
	private String sheetName;
	/**
	 * 列的名称(表头)
	 */
	private String[] headers;
	/**
	 * 数据源
	 */
	private List<? extends Object> data ;	
	/**
	 * 表头的单元格样式，枚举
	 */
	private ExcelStyle headStyleEnum;
	/**
	 * 数据单元格的样式，枚举
	 */
	private ExcelStyle bodyStyleEnum;
	/**
	 * 表头的单元格样式
	 */
	private HSSFCellStyle headerStyle;	
	/**
	 * 数据单元格的样式
	 */
	private HSSFCellStyle bodyStyle;	
	/**
	 * 时间格式，设定输出格式。格式出错或不填则默认为yyyy-MM-dd HH:mm:ss"
	 */
	private String pattern;

	/**
	 * 
	 * @param excelfile 
	 */
	/**
	 * 创建一个Excel
	 * @param excelfile excelfile接口
	 */
	
	public Excel(ExcelFile excelfile) {	
		if(excelfile!=null) {
			if(excelfile.fileName()!=null) {
				fileName=excelfile.fileName();
			}
			if(excelfile.sheetName()!=null) {
				sheetName=excelfile.sheetName();
			}
			if(excelfile.headers()!=null) {
				headers=excelfile.headers();
			}
			if(excelfile.data()!=null) {
				data=excelfile.data();
			}	
			if(excelfile.styleOfHead()!=null) {
				headStyleEnum=excelfile.styleOfHead();
			}	
			if(excelfile.styleOfBody()!=null) {
				bodyStyleEnum=excelfile.styleOfBody();
			}
			if(excelfile.pattern()!=null) {
				pattern=excelfile.pattern();
			}		
		}
	}
	

	
	/**
	 * 导出excel到客户端
	 * @param request 客户端的请求
	 * @param response 给客户端的回应
	 * @return 是否导出成功
	 */
	public boolean  export(HttpServletRequest request,HttpServletResponse response){
		if(fileName==null||fileName==null) {
			//默认文件名
			fileName="Excel";
		}		
		if(request!=null && response!=null) {
			response.setContentType("application/octet-stream");
			try {
				response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xls\"");
				return export(response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace(); 
				return false;
			}								
		}
		return false;
	}	
	
	/**
	 * 导出excel
	 * @param out 关联某个设备的输出流
	 * @return 是否导出成功
	 */
	public boolean export(OutputStream out) {
		HSSFWorkbook workbook =getWorkbook();
		try {
			workbook.write(out);
			out.flush();
			out.close();
			workbook.close();
		} catch(NullPointerException ne) {
			return false;
		} catch (FileNotFoundException fe) {
			return false;
			// TODO: handle exception
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * 设置表头样式，这个方法会覆盖接口实现的样式
	 * @param headStyleEnum
	 */
	public void setHeaderStyle(HSSFCellStyle hssfCellStyle) {
		headerStyle = hssfCellStyle;
	}

	/**
	 * 设置数据体样式，这个方法会覆盖接口实现的样式
	 * @param bodyStyleEnum
	 */
	public void setBodyStyle(HSSFCellStyle hssfCellStyle) {
		bodyStyle = hssfCellStyle;
	}



	/**
	 * 返回一个填充好数据的HSSFWorkbook
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HSSFWorkbook getWorkbook() {
		// 创建一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建一个表单
		HSSFSheet sheet;
		if(sheetName!=null && sheetName!="") {
			sheet = workbook.createSheet(sheetName);
		}else {
			sheet=workbook.createSheet("sheet");
		}
		
		// 设置表单默认列宽度为20个字节
		sheet.setDefaultColumnWidth((short) 20);	
		
		//如果没有设置样式，则使用接口传入的样式
		if(headerStyle == null) {
			headerStyle=getStyle(workbook, headStyleEnum);
		}
		if(bodyStyle == null) {
			bodyStyle=getStyle(workbook, bodyStyleEnum);	
		}		
		
 
		/**
		 *  画图的顶级管理器
		 */
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();

		/**
		 * 表单的一行
		 */
		HSSFRow row;
		/**
		 * 表格行的索引
		 */
		int index = 0;
		
		if(headers!=null) {
			// 创建表头
			row = sheet.createRow(index++);
			//填充表头
			for (short i = 0; i < headers.length; i++){
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(headerStyle);
				HSSFRichTextString text = new HSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
		}		
		if(data!=null) {
			Iterator<? extends Object> it = data.iterator();
			//遍历数据集合
			while (it.hasNext()){
				row = sheet.createRow(index++);
				Object object =  it.next();
				Class<? extends Object> classOfObject = object.getClass();
				//一行数据的集合
				Collection<Object> rowDatas =new ArrayList<>();
				//判断数据集合存储的对象的类型
				if(classOfObject.getSimpleName().toLowerCase().contains("map")) {
					try {
						rowDatas = (Collection<Object>) classOfObject.getMethod("values", new Class[]{}).invoke(object, new Object[] {});
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
				}else if(classOfObject.getSimpleName().toLowerCase().contains("list")) {
					rowDatas=(Collection<Object>)object;
				}else if(classOfObject.getSimpleName().toLowerCase().contains("object[]")) {
					try {
						Object[] objects=(Object[])object;
						for (int i=0;i<objects.length;i++) {
							if(objects[i]!=null) {
								rowDatas.add(objects[i]);
							}							
						}
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					Field[] fields = object.getClass().getDeclaredFields();
					//遍历一个javabean的所有属性，动态调用getXxx()方法得到属性值，并填充到指定行的某一单元格
					for (short i = 0; i < fields.length; i++){
						Field field = fields[i];
						String fieldName = field.getName();
						String getMethodName = "get"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
						try{	
							//将从javabean中get到的数据放入行数据集合中
							rowDatas.add(classOfObject.getMethod(getMethodName,new Class[]{}).invoke(object, new Object[]{}));
						}catch (SecurityException e){
							e.printStackTrace();
						}catch (NoSuchMethodException e){
							e.printStackTrace();
						}catch (IllegalArgumentException e){
							e.printStackTrace();
						}catch (IllegalAccessException e){
							e.printStackTrace();
						}catch (InvocationTargetException e){
							e.printStackTrace();
						}
					}//for					
				}//else
				//遍历一行的数据，并写进单元格
				int i=0;
				for (Object o : rowDatas) {
					//创建一个单元格
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(bodyStyle);
					// 处理特定类型
					String textValue = null;
					if (o instanceof Date){
						Date date = (Date) o;
						SimpleDateFormat simpleDateFormat;
						try {
						 simpleDateFormat= new SimpleDateFormat(pattern);
						}catch (Exception e) {
							simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						}
						textValue = simpleDateFormat.format(date);
					}else if (o instanceof byte[]){
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,这里有单位换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) o;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					}else{
						// 其它数据类型都当作字符串简单处理
						if(o!=null) {
							textValue = o.toString();
						}
						
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null){
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()){
							// 数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						}else{
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							cell.setCellValue(richString);
						}
					}
					i++;
				}
				
			}//while
		}//if data
		return workbook;
	}
	

	/**
	 * 预设了几个Excel的样式,当这里的样式不满足需求时，
	 * 可以通过setHeaderStyle(HSSFCellStyle hssfCellStyle)与 setBodyStyle(HSSFCellStyle hssfCellStyle)方法自定义样式
	 * @author twd
	 */
	public enum ExcelStyle{
		/**
		 * 12号黑体
		 */
		BOLD_12,	
		/**
		 * 10号黑体
		 */
		BOLD_10,
		/**
		 * 12号蓝色字体
		 */
		BLUE_Font_12,
		/**
		 * 10号蓝色字体
		 */
		BLUE_Font_10,
		/**
		 * 没有样式
		 */
		NONE
	};
	/**
	 * 根据传入的枚举，返回指定的样式
	 * @param workbook
	 * @param excelStyle
	 * @return
	 */
	private HSSFCellStyle getStyle(HSSFWorkbook workbook,ExcelStyle excelStyle) {
		// 创建一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 创建一个字体
		HSSFFont font = workbook.createFont();
		if(workbook==null || excelStyle==null) {
			return style;
		}
		switch (excelStyle) {
			case BOLD_12:					
				font.setFontHeightInPoints((short) 12);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	
				break;
			case BOLD_10:		
				font.setFontHeightInPoints((short) 10);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	
				break;
			case BLUE_Font_12:
				font.setFontHeightInPoints((short) 12);
				font.setColor(HSSFColor.BLUE.index);
				break;
			case BLUE_Font_10:
				font.setFontHeightInPoints((short) 10);
				font.setColor(HSSFColor.BLUE.index);
				break;
			default:
				break;
		}
		//设置字体样式
		style.setFont(font);
		return style;
	}

}
