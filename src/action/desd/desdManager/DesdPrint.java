package action.desd.desdManager;

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
public class DesdPrint extends BasePrintXmlAction{
	
	public void print(HttpServletResponse response, List<Map>list, List<Map>desd, List<Map>desd2, List<Map>desd3, Map info) throws IOException{
		
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
		out.println ("   <Alignment ss:Horizontal='Left' ss:Vertical='Center'/>");
		out.println ("   <Borders>");
		out.println ("    <Border ss:Position='Bottom' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("    <Border ss:Position='Top' ss:LineStyle='Dash' ss:Weight='1'/>");
		out.println ("   </Borders>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println ("  <Style ss:ID='s78'>");
		out.println ("   <Alignment ss:Horizontal='Right' ss:Vertical='Center'/>");
		out.println ("   <Borders/>");
		out.println ("   <Font ss:FontName='新細明體' x:CharSet='136' x:Family='Roman' ss:Size='18'");
		out.println ("    ss:Color='#000000'/>");
		out.println ("  </Style>");
		out.println (" </Styles>");	
		
		out.println (" <Worksheet ss:Name='班級統計'>");
		out.println ("  <Table ss:ExpandedColumnCount='13' ss:ExpandedRowCount='"+list.size()+1+"' x:FullColumns='1'");		
		out.println ("   x:FullRows='1' ss:StyleID='s78' ss:DefaultColumnWidth='20'");
		out.println ("   ss:DefaultRowHeight='25.5'>");
		
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='140'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");			
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='65'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='65'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		
		out.println ("   <Row>");		
		out.println ("    <Cell><Data ss:Type='String'>班級</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>人次(功)</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>大功</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>小功</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>嘉獎</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>功統計</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>人次(過)</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>大過</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>小過</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>申戒</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>過統計</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>人次總計</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>功過總計</Data></Cell>");
		out.println ("   </Row>");		
		int a,b,c,d,e,f;
		for(int i=0; i<desd.size(); i++){
			//操他媽若用泛型會出錯
			a=Integer.parseInt(desd.get(i).get("kind11").toString())+Integer.parseInt(desd.get(i).get("kind21").toString());
			b=Integer.parseInt(desd.get(i).get("kind12").toString())+Integer.parseInt(desd.get(i).get("kind22").toString());
			c=Integer.parseInt(desd.get(i).get("kind13").toString())+Integer.parseInt(desd.get(i).get("kind23").toString());
			d=Integer.parseInt(desd.get(i).get("kind14").toString())+Integer.parseInt(desd.get(i).get("kind24").toString());
			e=Integer.parseInt(desd.get(i).get("kind15").toString())+Integer.parseInt(desd.get(i).get("kind25").toString());
			f=Integer.parseInt(desd.get(i).get("kind16").toString())+Integer.parseInt(desd.get(i).get("kind26").toString());			
			
			out.println ("   <Row>");		
			out.println ("    <Cell><Data ss:Type='String'>"+desd.get(i).get("ClassName")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+desd.get(i).get("cnt1")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+a+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+b+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+c+"</Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-3],RC[-2],RC[-1])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+desd.get(i).get("cnt2")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+d+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+e+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+f+"</Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-3],RC[-2],RC[-1])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-5],RC[-10])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-7],RC[-2])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("   </Row>");
		}
		out.println ("   <Row>");		
		out.println ("    <Cell><Data ss:Type='String'>總計</Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("   </Row>");
		
		out.println ("  </Table>");			
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");			
		out.println ("    <Header x:Margin='0.3'");
		out.println ("     x:Data='&amp;L&amp;12 "+list.size()+"人次&amp;C&amp;20  "+info.get("school_year")+"學年"+info.get("school_term")+"學期獎懲名單'/>");			
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
		
		
		
		out.println (" <Worksheet ss:Name='班級統計(男)'>");
		out.println ("  <Table ss:ExpandedColumnCount='13' ss:ExpandedRowCount='"+list.size()+1+"' x:FullColumns='1'");		
		out.println ("   x:FullRows='1' ss:StyleID='s78' ss:DefaultColumnWidth='20'");
		out.println ("   ss:DefaultRowHeight='25.5'>");
		
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='140'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");			
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='65'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='65'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		
		out.println ("   <Row>");		
		out.println ("    <Cell><Data ss:Type='String'>班級(男生)</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>人次(功)</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>大功</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>小功</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>嘉獎</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>功統計</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>人次(過)</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>大過</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>小過</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>申戒</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>過統計</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>人次總計</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>功過總計</Data></Cell>");
		out.println ("   </Row>");		
		int a2,b2,c2,d2,e2,f2;
		for(int i=0; i<desd2.size(); i++){
			//操他媽若用泛型會出錯
			a2=Integer.parseInt(desd2.get(i).get("kind11").toString())+Integer.parseInt(desd2.get(i).get("kind21").toString());
			b2=Integer.parseInt(desd2.get(i).get("kind12").toString())+Integer.parseInt(desd2.get(i).get("kind22").toString());
			c2=Integer.parseInt(desd2.get(i).get("kind13").toString())+Integer.parseInt(desd2.get(i).get("kind23").toString());
			d2=Integer.parseInt(desd2.get(i).get("kind14").toString())+Integer.parseInt(desd2.get(i).get("kind24").toString());
			e2=Integer.parseInt(desd2.get(i).get("kind15").toString())+Integer.parseInt(desd2.get(i).get("kind25").toString());
			f2=Integer.parseInt(desd2.get(i).get("kind16").toString())+Integer.parseInt(desd2.get(i).get("kind26").toString());			
			
			out.println ("   <Row>");		
			out.println ("    <Cell><Data ss:Type='String'>"+desd2.get(i).get("ClassName")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+desd2.get(i).get("cnt1")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+a2+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+b2+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+c2+"</Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-3],RC[-2],RC[-1])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+desd2.get(i).get("cnt2")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+d2+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+e2+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+f2+"</Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-3],RC[-2],RC[-1])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-5],RC[-10])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-7],RC[-2])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("   </Row>");
		}
		out.println ("   <Row>");		
		out.println ("    <Cell><Data ss:Type='String'>總計</Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd2.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("   </Row>");
		
		out.println ("  </Table>");			
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");			
		out.println ("    <Header x:Margin='0.3'");
		out.println ("     x:Data='&amp;L&amp;12 "+list.size()+"人次&amp;C&amp;20  "+info.get("school_year")+"學年"+info.get("school_term")+"學期獎懲名單'/>");			
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
		
		
		
		out.println (" <Worksheet ss:Name='班級統計(女)'>");
		out.println ("  <Table ss:ExpandedColumnCount='13' ss:ExpandedRowCount='"+list.size()+1+"' x:FullColumns='1'");		
		out.println ("   x:FullRows='1' ss:StyleID='s78' ss:DefaultColumnWidth='20'");
		out.println ("   ss:DefaultRowHeight='25.5'>");
		
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='140'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");			
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='65'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='65'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='80'/>");
		
		out.println ("   <Row>");		
		out.println ("    <Cell><Data ss:Type='String'>班級(女生)</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>人次(功)</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>大功</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>小功</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>嘉獎</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>功統計</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>人次(過)</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>大過</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>小過</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>申戒</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>過統計</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>人次總計</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>功過總計</Data></Cell>");
		out.println ("   </Row>");		
		int a3,b3,c3,d3,e3,f3;
		for(int i=0; i<desd3.size(); i++){
			//操他媽若用泛型會出錯
			a3=Integer.parseInt(desd3.get(i).get("kind11").toString())+Integer.parseInt(desd3.get(i).get("kind21").toString());
			b3=Integer.parseInt(desd3.get(i).get("kind12").toString())+Integer.parseInt(desd3.get(i).get("kind22").toString());
			c3=Integer.parseInt(desd3.get(i).get("kind13").toString())+Integer.parseInt(desd3.get(i).get("kind23").toString());
			d3=Integer.parseInt(desd3.get(i).get("kind14").toString())+Integer.parseInt(desd3.get(i).get("kind24").toString());
			e3=Integer.parseInt(desd3.get(i).get("kind15").toString())+Integer.parseInt(desd3.get(i).get("kind25").toString());
			f3=Integer.parseInt(desd3.get(i).get("kind16").toString())+Integer.parseInt(desd3.get(i).get("kind26").toString());			
			
			out.println ("   <Row>");		
			out.println ("    <Cell><Data ss:Type='String'>"+desd3.get(i).get("ClassName")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+desd3.get(i).get("cnt1")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+a3+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+b3+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+c3+"</Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-3],RC[-2],RC[-1])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+desd3.get(i).get("cnt2")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+d3+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+e3+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='Number'>"+f3+"</Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-3],RC[-2],RC[-1])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-5],RC[-10])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("    <Cell ss:Formula='=SUM(RC[-7],RC[-2])'><Data ss:Type='Number'></Data></Cell>");
			out.println ("   </Row>");
		}
		out.println ("   <Row>");		
		out.println ("    <Cell><Data ss:Type='String'>總計</Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("    <Cell ss:Formula='=SUM(R[-"+desd3.size()+"]C:R[-1]C)'><Data ss:Type='Number'></Data></Cell>");
		out.println ("   </Row>");
		
		out.println ("  </Table>");			
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");			
		out.println ("    <Header x:Margin='0.3'");
		out.println ("     x:Data='&amp;L&amp;12 "+list.size()+"人次&amp;C&amp;20  "+info.get("school_year")+"學年"+info.get("school_term")+"學期獎懲名單'/>");			
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
		
		
		
		
			
			out.println (" <Worksheet ss:Name='個別統計'>");
			out.println ("  <Table ss:ExpandedColumnCount='12' ss:ExpandedRowCount='"+list.size()+1+"' x:FullColumns='1'");		
			out.println ("   x:FullRows='1' ss:StyleID='s78' ss:DefaultColumnWidth='20'");
			out.println ("   ss:DefaultRowHeight='25.5'>");
			
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='100'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='100'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");			
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='100'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='300'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
			
			out.println ("   <Row>");
			out.println ("    <Cell><Data ss:Type='String'>日期</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>文號</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>班級</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>學號</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>姓名</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>性別</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>原因</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>種類</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>次數</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>種類</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>次數</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>競賽</Data></Cell>");
			out.println ("   </Row>");		
			
			for(int i=0; i<list.size(); i++){
				out.println ("   <Row>");
				out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("ddate")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("no")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("ClassName")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("student_no")+"</Data></Cell>");
				out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("student_name")+"</Data></Cell>");
				if(list.get(i).get("sex").equals("1")){
					out.println ("    <Cell><Data ss:Type='String'>男</Data></Cell>");
				}else{
					out.println ("    <Cell><Data ss:Type='String'>女</Data></Cell>");
				}
				
				out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("name")+"</Data></Cell>");
				
				
				if(list.get(i).get("k1name")!=null){
					out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("k1name")+"</Data></Cell>");
					out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("cnt1")+"</Data></Cell>");
				}else{
					out.println ("    <Cell><Data ss:Type='String'></Data></Cell>");
					out.println ("    <Cell><Data ss:Type='String'></Data></Cell>");
				}
				
				if(list.get(i).get("k2name")!=null){
					out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("k2name")+"</Data></Cell>");
					out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("cnt2")+"</Data></Cell>");
				}else{
					out.println ("    <Cell><Data ss:Type='String'></Data></Cell>");
					out.println ("    <Cell><Data ss:Type='String'></Data></Cell>");
				}
				
				if(list.get(i).get("act_illegal").equals("1")){
					out.println ("    <Cell><Data ss:Type='String'>納入</Data></Cell>");
				}else{
					out.println ("    <Cell><Data ss:Type='String'>不納入</Data></Cell>");
				}				
				out.println ("   </Row>");
			}			
			
			out.println ("  </Table>");			
			out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
			out.println ("   <PageSetup>");			
			out.println ("    <Header x:Margin='0.3'");
			out.println ("     x:Data='&amp;L&amp;12 "+list.size()+"人次&amp;C&amp;20  "+info.get("school_year")+"學年"+info.get("school_term")+"學期獎懲名單'/>");			
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
		
		
		out.println ("</Workbook>");	
		out.close();
		out.flush();
		
		
	}

}
