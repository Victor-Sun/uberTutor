package com.gnomon.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * POI 工具类
 * 
 * @author Frank
 *
 */
public class POIUtils {

	public static Workbook createWorkbook(File excelFile) throws IOException {
		Workbook wb = null;
		String fileName = excelFile.getName();
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		InputStream is = new FileInputStream(excelFile);
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook(is);
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook(is);
		}

		return wb;

	}
	
	public static Workbook createWorkbook(String fileName,InputStream is) throws IOException {
		Workbook wb = null;
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook(is);
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook(is);
		}

		return wb;

	}
	public static Workbook createWorkbook(InputStream is) throws IOException {
		Workbook wb = null;
		try{
			wb = new HSSFWorkbook(is);
		}catch(Exception e){
			try {
				wb = new XSSFWorkbook(is);
			} catch (Exception e1) {
				throw new RuntimeException(e);
			}
		}
		
		return wb;
		
	}

	public static Boolean getBooleanCellValue(Cell cell){
		Boolean returnValue = false;
		String value = getStringCellValue(cell);
		if(null != value){
			value = value.trim().toUpperCase();
			if(value.equals("Y") || value.equals("是")){
				returnValue = true;
			}
		}
		return returnValue;
	}
	
	public static String getStringCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			if(Cell.CELL_TYPE_FORMULA == cell.getCellType()){
				value = String.valueOf(cell.getRichStringCellValue());
			}else
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				value = String.valueOf(cell.getNumericCellValue());
				if(value.endsWith(".0")){
					value = value.substring(0,value.length()-2);
				}
			} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
				if (cell.getStringCellValue() == null) {
					value = "";
				} else {
					value = String.valueOf(cell.getStringCellValue());
				}
			}
		}

		return value.trim();

	}
	
	public static Integer getIntCellValue(Cell cell) {
		String value = "";
		Integer intValue = null;
		if (cell != null) {
			if(Cell.CELL_TYPE_FORMULA == cell.getCellType()){
				value = String.valueOf(cell.getNumericCellValue());
			}else
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				value = String.valueOf(cell.getNumericCellValue());
			} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
				if (cell.getStringCellValue() == null) {
					value = "";
				} else {
					value = String.valueOf(cell.getStringCellValue());
				}
			}
			if(StringUtils.isNotEmpty(value)){
				intValue = Double.valueOf(value).intValue();
			}
		}

		return intValue;

	}

	public static Date getDateCellValue(Cell cell) {
		Date value = null;
		if (cell != null) {
			if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
				value = cell.getDateCellValue();
			} else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
				if (cell.getStringCellValue() == null) {
					value = null;
				} else {
					value = DateUtils.strToDate(cell.getStringCellValue());
				}
			}
		}
		return value;
	}

	public static boolean isEmpty(Row row) {
		boolean empty = true;
		if(row == null){
			empty = true;
		}else
		for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			String cellValue = POIUtils.getStringCellValue(cell);
			if(!StringUtils.isEmpty(cellValue)){
				empty = false;
				break;
			}
		}
		return empty;
	}
	
	public static void pringRow(Row row){
		for (int i = row.getFirstCellNum(); i <= row.getLastCellNum(); i++) {
			Cell cell = row.getCell(i);
			String cellValue = POIUtils.getStringCellValue(cell);
			System.out.print(cellValue + "\t");
		}
		System.out.println();
	}

}
