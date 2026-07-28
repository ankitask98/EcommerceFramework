package com.automation.excel;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    Workbook workbook;
    Sheet sheet;

    public ExcelUtils() throws IOException {

        FileInputStream file = new FileInputStream(
                "src/test/resources/LoginData.xlsx");

        workbook = new XSSFWorkbook(file);

        sheet = workbook.getSheetAt(0);
    }

    public String getCellData(int row, int column) {

        return sheet.getRow(row)
                .getCell(column)
                .getStringCellValue();
    }

    public int getRowCount() {

        return sheet.getLastRowNum();
    }
}