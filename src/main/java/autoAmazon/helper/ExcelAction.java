package autoAmazon.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import autoAmazon.pages.ProductPage;

public class ExcelAction {
	
    File fi= new File("D:\\Softwares\\eclipse-workspace\\automateTasks_POM\\testdata.xlsx");
    File fo= new File("D:\\Softwares\\eclipse-workspace\\automateTasks_POM\\testdata.xlsx");
    FileInputStream inputStream;
    FileOutputStream outputStream;
    XSSFWorkbook wb;
    XSSFSheet sheet;
	
	public String readSheet(String sheetName, int row, int column) throws IOException {
        //Excel File Related
        inputStream = new FileInputStream(fi);
        wb=new XSSFWorkbook(inputStream);
        sheet= wb.getSheet("TestData");
        String value = sheet.getRow(row).getCell(column).getStringCellValue();
        return value;
	}
	
	public void writeSheet(String sheetName, int row, int column, String value) throws IOException {
        //Excel File Related
		sheet.createRow(row).createCell(column).setCellValue(value);
		//inputStream.close();
        outputStream = new FileOutputStream(fo);
        sheet= wb.getSheet("Status");
		wb.write(outputStream);
		//outputStream.close();
	}
	
}
