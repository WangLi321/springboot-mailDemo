package com.example.springbootmaildemo.util.excel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.File;

/**
 * JXL解析Excel文件
 */
public class JxlReadExcel {

    public static void main(String[] args) {

        try {
            //创建workbook
            Workbook workbook = Workbook.getWorkbook(new File("test.xls"));
            //获取第一个工作表sheet
            Sheet sheet = workbook.getSheet(0);
            //获取数据
            for (int i = 0; i < sheet.getRows(); i++) {
                for (int j = 0; j < sheet.getColumns(); j++) {
                    Cell cell = sheet.getCell(j, i);
                    System.out.print(cell.getContents() + "  ");
                }
                System.out.println();
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}