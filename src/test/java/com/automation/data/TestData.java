package com.automation.data;

import java.io.IOException;

import org.testng.annotations.DataProvider;

import com.automation.excel.ExcelUtils;

public class TestData {

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {

        ExcelUtils excel = new ExcelUtils();

        int rows = excel.getRowCount();

        Object[][] data = new Object[rows][2];

        for (int i = 1; i <= rows; i++) {

            data[i - 1][0] = excel.getCellData(i, 0);

            data[i - 1][1] = excel.getCellData(i, 1);
        }

        return data;
    }
}