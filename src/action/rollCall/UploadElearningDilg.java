package action.rollCall;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Message;
import model.Stmd;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import service.impl.DataFinder;
import action.BaseAction;

public class UploadElearningDilg extends BaseAction{
	
	public File upload;
	
	//DataFinder df = (DataFinder) get("DataFinder");
	SimpleDateFormat sf=new SimpleDateFormat("yyyyMMdd");
	
	Date birthday;
	Calendar c=Calendar.getInstance();
	public String execute(){
		
		return SUCCESS;
	}
	
	
	public String upload() throws IOException{
		
		FileInputStream fis = new FileInputStream(upload);
		//FileReader fr = new FileReader(fis.getFD());
		//BufferedReader br = new BufferedReader(fr);
		
		XSSFWorkbook xwb = new XSSFWorkbook(fis);
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row;
		
		
		//System.out.println(sheet.getPhysicalNumberOfRows());
		String Dtime_oid;
		String student_no;
		String dilg;
		
		List list=new ArrayList();
		Map map;
		int cnt=0,fin=0;
		Message msg=new Message();
		
		
		
		
		
		for (int i = sheet.getFirstRowNum(); i <= sheet.getPhysicalNumberOfRows(); i++) {				
			row = sheet.getRow(i);
			if(row!=null){	
				
				if(i==0){
					try{
						Integer.parseInt(readCellAsString(row.getCell(1)));
					}catch(Exception e){
						continue;
					}
				}
				
				cnt++;
				try{					
					student_no=readCellAsString(row.getCell(0));
					Dtime_oid=readCellAsString(row.getCell(1)).substring(4);
					dilg=readCellAsString(row.getCell(2));
					df.exSql("UPDATE Seld SET elearn_dilg="+dilg+" WHERE student_no='"+student_no+"' AND Dtime_oid="+Dtime_oid);
					
					map=df.sqlGetMap("SELECT d.Oid, c.chi_name, st.student_no, st.student_name, s.elearn_dilg FROM Seld s,"
					+ "stmd st, Dtime d, Csno c WHERE d.cscode=c.cscode AND d.Oid=s.Dtime_oid AND s.student_no=st.student_no AND "
					+ "s.student_no='"+student_no+"' AND s.Dtime_oid="+Dtime_oid);
					
					if(map==null){
						map=new HashMap();
						map.put("Oid", "資料有誤");
					}
					list.add(map);	
					fin++;
				}catch(Exception e){
					map=new HashMap();
					map.put("Oid", "資料有誤");
					list.add(map);
					continue;
				}							
			}			
		}
		
		fis.close();
		request.setAttribute("list", list);
		msg.setSuccess("匯入資料 "+cnt+"筆, 更新成功 "+fin+"筆");
		this.savMessage(msg);
		return SUCCESS;
	}
	
	
	private void saveTxtFile() throws IOException{
		
		FileInputStream fis = new FileInputStream(upload);
		FileReader fr = new FileReader(fis.getFD());
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine(); //讀第一行
		df.exSql("INSERT INTO LC_exam_stmds (student_no) VALUES ('"+line+"')ON DUPLICATE KEY UPDATE student_no=VALUES(student_no);");
		StringBuffer sb = new StringBuffer();
		while ((line=br.readLine())!=null){
			df.exSql("INSERT INTO LC_exam_stmds (student_no) VALUES ('"+line+"')ON DUPLICATE KEY UPDATE student_no=VALUES(student_no);");
		}	
		fis.close();
		fr.close();
		br.close();
		
		
		
		
		
		
		
		
		
	}
	
	/**
	 * excel欄位強制轉文字 2003-
	 * @param cell
	 * @return
	 */
	private String readCellAsString(HSSFCell cell) {
		if (cell == null) {
			return null;
		}
		final DecimalFormat df = new DecimalFormat("####################0.##########");

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return Boolean.valueOf(cell.getBooleanCellValue()).toString();
		case HSSFCell.CELL_TYPE_NUMERIC:
			return df.format(cell.getNumericCellValue());
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case HSSFCell.CELL_TYPE_ERROR:
			return Byte.toString(cell.getErrorCellValue());
		default:
			return "##POI## Unknown cell type";
		}
	}
	
	/**
	 * excel欄位強制轉文字 2003+
	 * @param cell
	 * @return
	 */
	private String readCellAsString(XSSFCell cell) {
		if (cell == null) {
			return null;
		}
		final DecimalFormat df = new DecimalFormat("####################0.##########");

		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_BLANK:
			return "";
		case HSSFCell.CELL_TYPE_BOOLEAN:
			return Boolean.valueOf(cell.getBooleanCellValue()).toString().trim();
		case HSSFCell.CELL_TYPE_NUMERIC:
			return df.format(cell.getNumericCellValue()).trim();
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue().trim();
		case HSSFCell.CELL_TYPE_FORMULA:
			return cell.getCellFormula().trim();
		case HSSFCell.CELL_TYPE_ERROR:
			return Byte.toString(cell.getErrorCellValue());
		default:
			return "##POI## Unknown cell type";
		}
	}
	
	

}
