package com.framework.utils;

import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;

public class ExcelUtil {

    private ExcelUtil() {}

    public static Object[][] getData(
            String filePath,
            String sheetName) {

        try {

            FileInputStream fis =
                    new FileInputStream(filePath);

            Workbook workbook =
                    WorkbookFactory.create(fis);

            Sheet sheet =
                    workbook.getSheet(sheetName);

            int rows =
                    sheet.getPhysicalNumberOfRows();

            int cols =
                    sheet.getRow(0)
                            .getPhysicalNumberOfCells();

            Object[][] data =
                    new Object[rows - 1][cols];

            for (int i = 1; i < rows; i++) {

                for (int j = 0; j < cols; j++) {

                    data[i - 1][j] =
                            sheet
                                    .getRow(i)
                                    .getCell(j)
                                    .toString();
                }
            }

            workbook.close();

            return data;

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}