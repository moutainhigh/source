package com.dchip.cloudparking.web.utils;

import java.util.LinkedList;
import java.util.List;

import com.dchip.cloudparking.web.utils.Excel.ExcelFile;
import com.dchip.cloudparking.web.utils.Excel.ExcelStyle;

public class ExcelImportUtils {
	
	// @描述：是否是2003的excel，返回true是2003
	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	// @描述：是否是2007的excel，返回true是2007
	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	/**
	 * 验证EXCEL文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean validateExcel(String filePath) {
		if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
			return false;
		}
		return true;
	}

	public static Excel excelExport(String[] headers, List<LinkedList<Object>> dataList, String sheetName, String fileName) {

		Excel excel = new Excel(new ExcelFile() {

			@Override
			public String sheetName() {
				return sheetName;
			}

			@Override
			public String pattern() {
				return null;
			}

			@Override
			public String[] headers() {
				return headers;
			}

			@Override
			public String fileName() {
				return fileName;
			}

			@Override
			public ExcelStyle styleOfHead() {
				return ExcelStyle.BOLD_12;
			}

			@Override
			public ExcelStyle styleOfBody() {
				return null;
			}

			@Override
			public List<? extends Object> data() {
				return dataList;
			}
		});
		return excel;
	}
}
