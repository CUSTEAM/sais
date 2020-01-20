package action.just.justFilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import action.BasePrintXmlAction;

/**
 * 報表2
 * @author John
 *
 */
public class testPrint extends BasePrintXmlAction{
	
	public void print(HttpServletResponse response, List<Map>list, Map info) throws IOException{
		
		Date date=new Date();
		xml2ods(response, getRequest(), date);		
		
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
		out.println ("  <LastPrinted>2012-12-21T02:00:00Z</LastPrinted>");
		out.println ("  <Created>2012-12-21T00:00:00Z</Created>");
		out.println ("  <LastSaved>2012-12-21T00:00:00Z</LastSaved>");
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
		out.println ("   <Alignment ss:Horizontal='Right' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s78'>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");	
		
		
		
		
		out.println (" <Worksheet ss:Name='等第統計'>");
		out.println ("  <Table ss:ExpandedColumnCount='9' ss:ExpandedRowCount='999' x:FullColumns='1'");		
		out.println ("   x:FullRows='1' ss:StyleID='s78' ss:DefaultColumnWidth='20'");
		out.println ("   ss:DefaultRowHeight='25.5'>");
		
		out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0' ss:Width='200'/>");		
		out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0' ss:Width='80'/>");			
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		
		out.println ("   <Row>");
		out.println ("    <Cell><Data ss:Type='String'>班級</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>95以上</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>90以上</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>80以上</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>70以上</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>60以上</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>不及格</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>定查</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>人數</Data></Cell>");
		out.println ("   </Row>");
		int rank[];
		for(int i=0; i<list.size(); i++){
			if(list.get(i).get("rankCount")==null)continue;
			
			rank=(int[]) list.get(i).get("rankCount");
			
			out.println ("   <Row>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("ClassName")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+rank[0]+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+rank[1]+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+rank[2]+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+rank[3]+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+rank[4]+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+rank[5]+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+rank[6]+"</Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-7],RC[-6],RC[-5],RC[-4],RC[-3])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("   </Row>");
		}
		
		out.println ("  </Table>");			
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		
		out.println ("    <Header x:Margin='0.3'");
		
		
		out.println ("     x:Data='&amp;L&amp;12&amp;C&amp;20 "+info.get("school_year")+"學年第 "+info.get("school_term")+"學期操行成績統計表&amp;R&amp;18'/>");
		
		
		
		out.println ("    <Footer x:Margin='0.3'");
		out.println ("     x:Data='&amp;L&amp;12*表示定期察看學生&#10;#表示應屆畢業下修低年級課程 &amp;R&amp;D-&amp;T&#10;第&amp;P頁 共&amp;N頁'/>");
		
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
		List<Map>stds;		
		int down_year,total_score;
		for(int i=0; i<list.size(); i++){
			
			stds=(List)list.get(i).get("stds");
			if(stds.size()<1)continue;
			out.println (" <Worksheet ss:Name='"+list.get(i).get("ClassNo")+"'>");
			out.println ("  <Table ss:ExpandedColumnCount='10' ss:ExpandedRowCount='"+stds.size()+1+"' x:FullColumns='1'");		
			out.println ("   x:FullRows='1' ss:StyleID='s78' ss:DefaultColumnWidth='20'");
			out.println ("   ss:DefaultRowHeight='25.5'>");
			if(i==0){
				out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0' ss:Width='220'/>");
			}else{
				out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0' ss:Width='100'/>");
			}
			
			out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0' ss:Width='70'/>");			
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s78' ss:AutoFitWidth='0' ss:Width='400'/>");
			
			out.println ("   <Row>");
			out.println ("    <Cell><Data ss:Type='String'>學號</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>姓名</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>導師</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>主任</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>教官</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>曠缺</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>獎懲</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>評審</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>總分</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>評語</Data></Cell>");
			out.println ("   </Row>");
			
			for(int j=0; j<stds.size(); j++){			
				out.println ("   <Row>");
				out.println ("    <Cell><Data ss:Type='String'>"+stds.get(j).get("down_year")+stds.get(j).get("down")+stds.get(j).get("student_no")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+stds.get(j).get("student_name")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+stds.get(j).get("teacher_score")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+stds.get(j).get("deptheader_score")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+stds.get(j).get("military_score")+"</Data></Cell>");
				
				out.println ("    <Cell><Data ss:Type='String'>"+stds.get(j).get("dilg_score")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='Number'>"+stds.get(j).get("desd_score")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+stds.get(j).get("meeting_score")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+stds.get(j).get("total_score")+"</Data></Cell>");				
				
				total_score=Integer.parseInt(stds.get(j).get("total_score").toString());				
				out.println ("    <Cell><Data ss:Type='String'>"+
				stds.get(j).get("com_code1")+stds.get(j).get("com_code2")+stds.get(j).get("com_code3")+"</Data></Cell>");
				out.println ("   </Row>");			
			}
			
			out.println ("  </Table>");			
			out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
			out.println ("   <PageSetup>");
			
			out.println ("    <Header x:Margin='0.3'");
			
			if(list.get(i).get("rank")!=null){
				out.println ("     x:Data='&amp;L&amp;12人數:"+stds.size()+", "+list.get(i).get("rank")+"&amp;C&amp;20 "+
				list.get(i).get("ClassName")+info.get("school_year")+"學年第 "+info.get("school_term")+"學期操行成績表&amp;R&amp;18 導師"+list.get(i).get("cname")+"'/>");
			}else{
				out.println ("     x:Data='&amp;L&amp;12人數:"+stds.size()+"&amp;C&amp;20 "+
						list.get(i).get("ClassName")+info.get("school_year")+"學年第 "+info.get("school_term")+"學期操行成績表&amp;R&amp;18 導師"+list.get(i).get("cname")+"'/>");
			}
			
			
			out.println ("    <Footer x:Margin='0.3'");
			out.println ("     x:Data='&amp;L&amp;12*表示定期察看學生&#10;#表示應屆畢業下修低年級課程 &amp;R&amp;D-&amp;T&#10;第&amp;P頁 共&amp;N頁'/>");
			
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
		}		
		out.println ("</Workbook>");	
		out.close();
		out.flush();		
	}

}
