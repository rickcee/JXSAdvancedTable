/**
 * 
 * Copyright 2009,2010,2011,2012 RickCeeNet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package net.rickcee.swingxs.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.List;

import javax.swing.SwingConstants;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rickcee.swingxs.config.BeanAttributeContainer;
import net.rickcee.swingxs.config.BeanManager;

/**
 * @author RickCeeNet
 * 
 */
public class ExportUtilities {
	private static final Logger logger = LoggerFactory.getLogger(ExportUtilities.class);

	public static final int EXCEL_2000 = 1;
	public static final int EXCEL_2007 = 2;

	/**
	 * @param file
	 *            The file
	 * @param objectsToExport
	 *            List of objects to export.
	 * @throws Exception
	 *             In case of any error.
	 */
	public static synchronized void exportToExcel(File file, Class<?> clazz, List<?> objectsToExport, int excelFormat)
			throws Exception {

		Workbook workBook = getExcelWorkbook(clazz, objectsToExport, excelFormat);

		/* WRITE DATA TO THE OUTPUT FILE */
		FileOutputStream fos = new FileOutputStream(file);
		workBook.write(fos);
		fos.close();
	}

	/**
	 * @param objectsToExport
	 *            List of objects to export.
	 * @throws Exception
	 *             In case of any error.
	 */
	public static synchronized Workbook getExcelWorkbook(Class<?> clazz, List<?> objectsToExport, int excelFormat) throws Exception {

		Workbook workBook;

		if (EXCEL_2007 == excelFormat) {
			workBook = new XSSFWorkbook();
		} else {
			workBook = new HSSFWorkbook();
		}

		Sheet sheet = workBook.createSheet();

		/*
		 * Create Fonts
		 */
		Font defaultFont = workBook.createFont();
		defaultFont.setFontHeightInPoints((short) 10);

		Font boldFont = workBook.createFont();
		boldFont.setFontHeightInPoints((short) 10);
		boldFont.setBoldweight(Font.BOLDWEIGHT_BOLD);

		CellStyle styleNormal = workBook.createCellStyle();
		styleNormal.setFont(defaultFont);

		CellStyle styleNormalBold = workBook.createCellStyle();
		styleNormalBold.setFont(boldFont);

		if (objectsToExport.size() == 0) {
			logger.warn("No objects to export.");
			// If there are no object, return the empty workbook
			return workBook;
		}

		int _row = 0;
		int _col = 0;
		int totalCols = 0;
		boolean firstTime = true;

		Row excelRow;
		Cell cell;

		/* HEADER */
		excelRow = sheet.createRow(_row++);
		BeanAttributeContainer container = BeanManager.get(clazz);
		for (Integer position:container.getFieldPosition().keySet()) {
			cell = excelRow.createCell(_col++);
			String key = container.getFieldPosition().get(position);
			
			// Ignore non visible columns
			if(container.getFieldVisibility().get(key)==Boolean.FALSE) {
				continue;
			}
			
			String colName = container.getFieldLabel().get(key);
			cell.setCellValue(colName);
			cell.setCellStyle(styleNormalBold);
			if (firstTime) {
				totalCols++;
				// firstTime = false;
			}
		}

		CellStyle csLeft = workBook.createCellStyle();
		csLeft.setAlignment(CellStyle.ALIGN_LEFT);
		CellStyle csRight = workBook.createCellStyle();
		csRight.setAlignment(CellStyle.ALIGN_RIGHT);
		CellStyle csCenter = workBook.createCellStyle();
		csCenter.setAlignment(CellStyle.ALIGN_CENTER);
		
		/* BODY */
		_col = 0;
		for (Object o : objectsToExport) {
			excelRow = sheet.createRow(_row++);

			for (Integer position:container.getFieldPosition().keySet()) {
				String key = container.getFieldPosition().get(position);
				
				// Ignore non visible columns
				if(container.getFieldVisibility().get(key)==Boolean.FALSE) {
					continue;
				}
				
				Method getter = container.getFieldGetters().get(key);
				
				cell = excelRow.createCell(_col++);
				Object value = getter.invoke(o);
				if (value instanceof Number) {
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cell.setCellValue(((Number) value).doubleValue());
				} else if (value instanceof Boolean) {
					cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
					cell.setCellValue((Boolean) value);
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(value == null ? "" : value.toString());
				}
				cell.setCellStyle(styleNormal);
				
				Integer align = container.getFieldAlignment().get(key);
				switch (align) {
				case SwingConstants.LEFT:
					cell.setCellStyle(csLeft);
					break;
				case SwingConstants.RIGHT:
					cell.setCellStyle(csRight);
					break;
				case SwingConstants.CENTER:
					cell.setCellStyle(csCenter);
					break;
				}
			}
			_col = 0;
		}

		for (int i = 0; i <= totalCols; i++) {
			sheet.autoSizeColumn(i, true);
		}
		return workBook;
	}

}
