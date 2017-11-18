package action.rollCall.timeOffSearch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import action.BaseAction;

/**
 * 學生細節報表
 * @author John
 *
 */
public class ListStd extends BaseAction{
	
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
		out.println ("  <LastPrinted>2013-03-21T02:34:59Z</LastPrinted>");
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
		out.println ("  <Style ss:ID='s76'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s78'>");
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders/>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");
		
		out.println (" <Worksheet ss:Name='缺課統計列表'>");
		out.println ("  <Table ss:ExpandedColumnCount='14' ss:ExpandedRowCount='"+list.size()+1+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s78' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='25.5'>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='100'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='60' ss:Span='1'/>");
		out.println ("   <Column ss:Index='5' ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='100'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Row>");
		out.println ("    <Cell><Data ss:Type='String'>班級</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>學號</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>姓名</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>家長</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>電話</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>住院</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>曠課</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>病假</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>事假</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>遲到</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>公假</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>喪假</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>婚假</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>產假</Data></Cell>");
		out.println ("   </Row>");		
		
		for(int i=0; i<list.size(); i++){
			
			out.println ("   <Row>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("ClassName")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("student_no")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("student_name")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("parent_name")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("CellPhone")+", "+list.get(i).get("telephone")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("abs1")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("abs2")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("abs3")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("abs4")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("abs5")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("abs6")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("abs7")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("abs8")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+list.get(i).get("abs9")+"</Data></Cell>");
			out.println ("   </Row>");			
		}		
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.3'");
		out.println ("     x:Data='&amp;C&amp;20 "+school_year+"學年第 "+school_term+"學期學生缺曠&#10;"+beginDate+" 至"+endDate+"&amp;R&amp;D-&amp;T&#10;第&amp;P頁 共&amp;N頁'/>");
		out.println ("    <Footer x:Margin='0.3'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.7' x:Right='0.7' x:Top='0.75'/>");
		out.println ("   </PageSetup>");
		out.println ("   <FitToPage/>");
		out.println ("   <Print>");
		out.println ("    <FitHeight>0</FitHeight>");
		out.println ("    <ValidPrinterInfo/>");
		out.println ("    <PaperSizeIndex>9</PaperSizeIndex>");
		out.println ("    <Scale>52</Scale>");
		out.println ("    <HorizontalResolution>600</HorizontalResolution>");
		out.println ("    <VerticalResolution>600</VerticalResolution>");
		out.println ("   </Print>");
		out.println ("   <Selected/>");
		out.println ("   <Panes>");
		out.println ("    <Pane>");
		out.println ("     <Number>3</Number>");
		out.println ("     <ActiveRow>16</ActiveRow>");
		out.println ("     <ActiveCol>7</ActiveCol>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		
		
		out.println (" <Worksheet ss:Name='缺課明細列表'>");
		out.println ("  <Table ss:ExpandedColumnCount='7' ss:ExpandedRowCount='600000' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:DefaultColumnWidth='54' ss:DefaultRowHeight='16.5'>");
		out.println ("   <Column ss:Width='63'/>");
		out.println ("   <Column ss:AutoFitWidth='0' ss:Width='37.5'/>");
		out.println ("   <Row>");
		out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>日期</Data></Cell>");
		out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>節次</Data></Cell>");
		out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>課程</Data></Cell>");
		//out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>開課班級</Data></Cell>");
		out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>教師</Data></Cell>");
		out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>學號</Data></Cell>");
		out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>0:生理假,1:重大傷病住院,2:曠課,3:病假,4:事假,5:遲到,6:公假,7:喪假,8:婚假,9:產假</Data></Cell>");
		out.println ("   </Row>");
		
		
		List<Map>tmp;
		for(int i=0; i<list.size(); i++){
			tmp=df.sqlGet("SELECT c.chi_name, d.date, d.student_no, d.cls, d.abs, e.cname,"
			+ "dt.cscode FROM Csno c, Dilg d, Dtime dt LEFT OUTER JOIN empl e ON e.idno=dt.techid WHERE c.cscode=dt.cscode AND "
			+ "dt.Oid=d.Dtime_oid AND d.student_no='"+list.get(i).get("student_no")+"'");
			
			for(int j=0; j<tmp.size(); j++){
				out.println ("   <Row>");
				out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>"+tmp.get(j).get("date")+"</Data></Cell>");
				out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>"+tmp.get(j).get("cls")+"</Data></Cell>");
				out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>"+tmp.get(j).get("chi_name")+"</Data></Cell>");
				//out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>"+tmp.get(j).get("depart_class")+"</Data></Cell>");
				out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>"+tmp.get(j).get("cname")+"</Data></Cell>");
				out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>"+tmp.get(j).get("student_no")+"</Data></Cell>");
				out.println ("    <Cell ss:StyleID='s76'><Data ss:Type='String'>"+tmp.get(j).get("abs")+"</Data></Cell>");
				out.println ("   </Row>");
			}
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.3'/>");
		out.println ("    <Footer x:Margin='0.3'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.7' x:Right='0.7' x:Top='0.75'/>");
		out.println ("   </PageSetup>");
		out.println ("   <Selected/>");
		out.println ("   <Panes>");
		out.println ("    <Pane>");
		out.println ("     <Number>3</Number>");
		out.println ("     <ActiveRow>6</ActiveRow>");
		out.println ("     <ActiveCol>3</ActiveCol>");
		out.println ("    </Pane>");
		out.println ("   </Panes>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");
		
		
		
		/*out.println (" <Worksheet ss:Name='工作表3'>");
		out.println ("  <Table ss:ExpandedColumnCount='1' ss:ExpandedRowCount='1' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:DefaultColumnWidth='54' ss:DefaultRowHeight='16.5'>");
		out.println ("   <Row ss:AutoFitHeight='0'/>");
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.3'/>");
		out.println ("    <Footer x:Margin='0.3'/>");
		out.println ("    <PageMargins x:Bottom='0.75' x:Left='0.7' x:Right='0.7' x:Top='0.75'/>");
		out.println ("   </PageSetup>");
		out.println ("   <Unsynced/>");
		out.println ("   <ProtectObjects>False</ProtectObjects>");
		out.println ("   <ProtectScenarios>False</ProtectScenarios>");
		out.println ("  </WorksheetOptions>");
		out.println (" </Worksheet>");*/
		
		
		
		out.println ("</Workbook>");	
		out.close();
		
		
	}

}
