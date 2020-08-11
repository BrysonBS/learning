package cn.tacos.tacocloud.demo;

import org.junit.Test;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.*;

public class ExcelRW {
    @Test
    public void textExcel() {
        File file = new File("C:\\Users\\NBL\\Desktop");
        File[] files = file.listFiles();
        for(File f : files){
            //System.out.println(f.getName());
            if(f.getName().contains("开发小组工作内容-")){
                System.out.println(f.getName());
                //读取excel
                try (InputStream input = new FileInputStream(f)) {
                    XSSFWorkbook workbook = new XSSFWorkbook(input);
                    Iterator<Sheet> iter = workbook.iterator();
                    while (iter.hasNext()) {
                        Sheet sheet = iter.next();
                        int rows = sheet.getPhysicalNumberOfRows();
                        sheet.getRow(0).getCell(0).setCellValue(LocalDate.now());
                        for (int i = 2; i < rows; ++i) {
                            Row row = sheet.getRow(i);
                            row.getCell(3).setCellValue(LocalDate.now());
                            row.getCell(7).setCellValue(LocalDate.now());
                        }
                    }
                    OutputStream output = new FileOutputStream(f);
                    workbook.write(output);
                    output.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                //文件名更新
                LocalDate now = LocalDate.now();
                String str = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                str = f.getName().replaceAll("\\d+", str);
                System.out.println(str);
                System.out.println(f.renameTo(new File(file, str)));
            }
        }
    }
}
