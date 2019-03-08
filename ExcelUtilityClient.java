package com.automation.api.properties;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Class contains the excel related methods and variables.
 */
public class ExcelUtilityClient {

    private static String separator = File.separator;

    private static String returnRow(XSSFRow r, int i) {
        if (r.getCell(i) == null) {
            return null;
        } else if (r.getCell(i).getCellType() == CellType.NUMERIC) {
            return removeSpaces(Integer.toString((int) r.getCell(i).getNumericCellValue()));
        } else if (r.getCell(i).getCellType() == CellType.STRING) {
            return removeSpaces(r.getCell(i).getStringCellValue());
        } else {
            return null;
        }
    }

    private static String removeSpaces(String s) {
        s = s.replace("\u00a0", "");
        s = s.trim();
        return s;
    }

    public static void writeToExcel(String rowHeaderName, String columnHeaderName, String cellValue) throws Exception {
        int rowIndex = -1, columnIndex = -1;
        FileInputStream fis = new FileInputStream("/home/suryavenkatadu/Documents/TestData.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet worksheet = workbook.getSheetAt(0);
        int rowCount = worksheet.getPhysicalNumberOfRows();
        for (int i = 0; i < rowCount; i++) {
            XSSFRow row = worksheet.getRow(i);
            String name = returnRow(row, 0);
            if (name.equalsIgnoreCase(rowHeaderName)) {
                rowIndex = i;
                break;
            }
        }
        XSSFRow columnHeaderRow = worksheet.getRow(0);
        int columnCount = columnHeaderRow.getPhysicalNumberOfCells();
        for (int i = 0; i < columnCount; i++) {
            String columnName = returnRow(columnHeaderRow, i);
            if (columnName.equalsIgnoreCase(columnHeaderName)) {
                columnIndex = i;
                break;
            }
        }
        XSSFRow dataRow = worksheet.getRow(rowIndex);
        XSSFCell dataCell = dataRow.getCell(columnIndex);
        if (dataCell == null) {
            dataCell = dataRow.createCell(columnIndex);
        }
        dataCell.setCellValue(cellValue);
        FileOutputStream fos = new FileOutputStream("/home/suryavenkatadu/Documents/TestData.xlsx");
        workbook.write(fos);
    }

    public static void main(String[] args) throws Exception {
        ExcelUtilityClient.writeToExcel("ApplicationType","VW Application Details","SampleTest");
    }
}
