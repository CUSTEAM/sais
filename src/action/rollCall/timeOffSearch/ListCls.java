package action.rollCall.timeOffSearch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * 報表1
 * @author John
 *
 */
public class ListCls {
	
	public void print(HttpServletResponse response, List<Map>list, String beginDate, String endDate, String school_year, String school_term) throws IOException{
		Date date=new Date();
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment;filename="+date.getTime()+".xls");
		PrintWriter out=response.getWriter();
		out.println ("<?xml version='1.0'?>");
		out.println ("<?mso-application progid='Excel.Sheet'?>");
		out.println ("<Workbook xmlns='urn:schemas-microsoft-com:office:spreadsheet'");
		out.println (" xmlns:o='urn:schemas-microsoft-com:office:office'");
		out.println (" xmlns:x='urn:schemas-microsoft-com:office:excel'");
		out.println (" xmlns:ss='urn:schemas-microsoft-com:office:spreadsheet'");
		out.println (" xmlns:html='http://www.w3.org/TR/REC-html40'>");
		out.println (" <DocumentProperties xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <Author>John</Author>");
		out.println ("  <LastAuthor>John</LastAuthor>");
		out.println ("  <LastPrinted>2013-03-20T08:11:53Z</LastPrinted>");
		out.println ("  <Created>2013-03-20T08:00:38Z</Created>");
		out.println ("  <LastSaved>2013-03-20T08:04:09Z</LastSaved>");
		out.println ("  <Version>14.00</Version>");
		out.println (" </DocumentProperties>");
		out.println (" <OfficeDocumentSettings xmlns='urn:schemas-microsoft-com:office:office'>");
		out.println ("  <AllowPNG/>");
		out.println (" </OfficeDocumentSettings>");
		out.println (" <ExcelWorkbook xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("  <WindowHeight>11880</WindowHeight>");
		out.println ("  <WindowWidth>28035</WindowWidth>");
		out.println ("  <WindowTopX>360</WindowTopX>");
		out.println ("  <WindowTopY>105</WindowTopY>");
		out.println ("  <ProtectStructure>False</ProtectStructure>");
		out.println ("  <ProtectWindows>False</ProtectWindows>");
		out.println (" </ExcelWorkbook>");
		out.println (" <Styles>");
		out.println ("  <Style ss:ID='Default' ss:Name='Normal'>");
		out.println ("   <Alignment ss:Vertical='Center'/>");
		out.println ("   <Borders/>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='12'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("   <Interior/>");
		out.println ("   <NumberFormat/>");
		out.println ("   <Protection/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s68'>");
		out.println ("   <Alignment ss:Horizontal='Right' ss:Vertical='Center'/>");
		out.println ("   <Borders/>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s69'>");
		out.println ("   <Alignment ss:Horizontal='Right' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");
		out.println (" <Worksheet ss:Name='工作表1'>");
		out.println ("  <Table ss:ExpandedColumnCount='11' ss:ExpandedRowCount='"+list.size()+1+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s68' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='14.25'>");
		out.println ("   <Column ss:StyleID='s69' ss:Width='78.75'/>");
		out.println ("   <Column ss:StyleID='s69' ss:Width='28.5'/>");
		out.println ("   <Column ss:StyleID='s69' ss:Width='48' ss:Span='8'/>");
		out.println ("   <Row>");
		out.println ("    <Cell><Data ss:Type='String'>班級</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>人數</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>全班曠課</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>每人曠課</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>全班事假</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>每人事假</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>全班病假</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>每人病假</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>全班公假</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>每人公假</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>全勤人數</Data></Cell>");
		out.println ("   </Row>");		
		int sts;
		int tmp;		
		for(int i=0; i<list.size(); i++){
			out.println ("   <Row>");
			out.println ("    <Cell><Data ss:Type='String'>"+((Map)list.get(i)).get("ClassName")+"</Data></Cell>");
			sts=Integer.parseInt(list.get(i).get("sts").toString());
			tmp=Integer.parseInt(list.get(i).get("abs2").toString());
			out.println ("    <Cell><Data ss:Type='Number'>"+sts+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+tmp+"</Data></Cell>");			
			if(tmp>0){out.println ("<Cell><Data ss:Type='Number'>"+tmp/sts+"</Data></Cell>");}else{out.println ("<Cell><Data ss:Type='Number'>0</Data></Cell>");}
			tmp=Integer.parseInt(list.get(i).get("abs4").toString());
			out.println ("    <Cell><Data ss:Type='Number'>"+tmp+"</Data></Cell>");
			if(tmp>0){out.println ("<Cell><Data ss:Type='Number'>"+tmp/sts+"</Data></Cell>");}else{out.println ("<Cell><Data ss:Type='Number'>0</Data></Cell>");}
			tmp=Integer.parseInt(list.get(i).get("abs3").toString());
			out.println ("    <Cell><Data ss:Type='Number'>"+tmp+"</Data></Cell>");
			if(tmp>0){out.println ("<Cell><Data ss:Type='Number'>"+tmp/sts+"</Data></Cell>");}else{out.println ("<Cell><Data ss:Type='Number'>0</Data></Cell>");}
			tmp=Integer.parseInt(list.get(i).get("abs6").toString());
			out.println ("    <Cell><Data ss:Type='Number'>"+tmp+"</Data></Cell>");
			if(tmp>0){out.println ("<Cell><Data ss:Type='Number'>"+tmp/sts+"</Data></Cell>");}else{out.println ("<Cell><Data ss:Type='Number'>0</Data></Cell>");}
			out.println ("    <Cell><Data ss:Type='Number'>"+((Map)list.get(i)).get("noabs")+"</Data></Cell>");
			out.println ("   </Row>");		
		}		
		
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.3' x:Data='&amp;C"+school_year+"學年第 "+school_term+"學期缺曠人次統計表&#10;"+beginDate+" 至 "+endDate+"&amp;R&amp;T&#10;第&amp;P頁'/>");
		out.println ("    <Footer x:Margin='0.3'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.7' x:Right='0.7' x:Top='0.75'/>");
		out.println ("   </PageSetup>");
		out.println ("   <FitToPage/>");
		out.println ("   <Print>");
		out.println ("    <FitHeight>0</FitHeight>");
		out.println ("    <ValidPrinterInfo/>");
		out.println ("    <PaperSizeIndex>9</PaperSizeIndex>");
		out.println ("    <Scale>96</Scale>");
		out.println ("    <HorizontalResolution>600</HorizontalResolution>");
		out.println ("    <VerticalResolution>600</VerticalResolution>");
		out.println ("   </Print>");
		out.println ("   <Selected/>");
		out.println ("   <Panes>");
		out.println ("    <Pane>");
		out.println ("     <Number>3</Number>");
		out.println ("     <ActiveRow>6</ActiveRow>");
		out.println ("     <ActiveCol>13</ActiveCol>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		out.println (" <Worksheet ss:Name='工作表2'>");
		out.println ("  <Table ss:ExpandedColumnCount='1' ss:ExpandedRowCount='1' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:DefaultColumnWidth='54' ss:DefaultRowHeight='16.5'>");
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.3'/>");
		out.println ("    <Footer x:Margin='0.3'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.7' x:Right='0.7' x:Top='0.75'/>");
		out.println ("   </PageSetup>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		out.println (" <Worksheet ss:Name='工作表3'>");
		out.println ("  <Table ss:ExpandedColumnCount='1' ss:ExpandedRowCount='1' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:DefaultColumnWidth='54' ss:DefaultRowHeight='16.5'>");
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.3'/>");
		out.println ("    <Footer x:Margin='0.3'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.7' x:Right='0.7' x:Top='0.75'/>");
		out.println ("   </PageSetup>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		out.println ("</Workbook>");
		out.println ("");		
		out.close();
	}
}