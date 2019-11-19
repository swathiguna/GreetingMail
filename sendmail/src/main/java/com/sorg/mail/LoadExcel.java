package com.sorg.mail;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LoadExcel {
	final static Logger logger = Logger.getLogger( LoadExcel.class);
	public static List<User> readFromExcel() {
		List<User> users=new ArrayList<>();
		XSSFWorkbook wb;
		try {
			wb = new XSSFWorkbook(new FileInputStream("config/images/Masterdata.xlsx"));
			XSSFSheet myExcelSheet = wb.getSheet("Sheet1");
			logger.info("sheet loaded");
			Calendar now = Calendar.getInstance();
			int currMonth=now.get(Calendar.MONTH) + 1;
			int currDate=now.get(Calendar.DATE);
			
			for (int i = 1; i < myExcelSheet.getLastRowNum() + 1; i++) {
				XSSFRow row = myExcelSheet.getRow(i);
				for (int j = 0; j < row.getLastCellNum(); j++) {
					System.out.print(row.getCell(j) + "\t");
				}
				System.out.println();
			}
			for (int i = 1; i < myExcelSheet.getLastRowNum() + 1; i++) {
				XSSFRow row = myExcelSheet.getRow(i);
				var var = row.getCell(2).getDateCellValue();
				Calendar cal = Calendar.getInstance();
				cal.setTime(var);
				
				if(currMonth==(cal.get(Calendar.MONTH) + 1) && currDate==cal.get(Calendar.DATE)){
					
					User user=new User();
					user.setName(row.getCell(1).getStringCellValue());
					user.setMailId(row.getCell(3).getStringCellValue());
					user.setEvent(row.getCell(4).getStringCellValue());
					user.setMessage(row.getCell(5).getStringCellValue());
					users.add(user);
					logger.info("user:"+user.toString());
				}
			}
			wb.close();
		} catch (Exception e) {
			logger.error("error in reading excel file", e);
		}
		return users;
	}

}
