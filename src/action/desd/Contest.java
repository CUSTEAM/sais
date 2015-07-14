package action.desd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Message;

import action.BaseAction;

public class Contest extends BaseAction{
	
	public String cno;
	public String sno;
	public String dno;
	public String gno;
	public String zno;
	
	public String begin;
	public String end;
	
	public String exectue(){
		
		return SUCCESS;
	}
	
	public String print() throws IOException{
		
		if(begin.trim().length()<10||end.trim().length()<10){
			Message msg=new Message();
			msg.setError("請選擇日期範圍");
			this.savMessage(msg);
			return SUCCESS;
		}
		
		String area=" d.date>='"+begin+"' AND d.date<='"+end+"' AND ";
		
		//全班人數及病假		
		List<Map>list=df.sqlGet("SELECT c.ClassName, c.ClassNo, " +
		"(SELECT COUNT(*) FROM Dilg d, stmd s WHERE "+area+" d.abs='3' AND " +
		"d.student_no=s.student_no AND s.depart_class=c.ClassNo)as abs3, " +
		"(SELECT COUNT(*)FROM stmd WHERE depart_class=c.ClassNo)as al " +
		"FROM Class c WHERE c.Type='P' AND c.CampusNo LIKE'"+cno+"%' AND c.SchoolNo " +
		"LIKE'"+sno+"%' AND c.DeptNo LIKE '"+dno+"%' AND c.Grade LIKE'"+gno+"%' AND c.SeqNo LIKE'"+zno+"%'");
		
		List<Map>tmp;
		float f;
		int c1,c2,c3,c4,cnt;
		for(int i=0; i<list.size(); i++){
			c1=0;c2=0;c3=0;c4=0;
			//每位學生曠課
			tmp=df.sqlGet("SELECT s.student_no, COUNT(*)as cnt FROM Dilg d, stmd s WHERE "+area+
			"d.abs='2' AND d.student_no=s.student_no AND s.depart_class='"+list.get(i).get("ClassNo")+"' " +
			"GROUP BY s.student_no");
			
			f=0.0f;
			
			//計算每位學生曠課
			for(int j=0; j<tmp.size(); j++){
				
				cnt=Integer.parseInt(tmp.get(j).get("cnt").toString());
				
				if(cnt>10&&cnt<=20){
					f=f+0.5f;
					c1++;
				}
				
				if(cnt>20&&cnt<=30){
					f=f+1.0f;
					c2++;
				}
				
				if(cnt>30&&cnt<=40){
					f=f+1.5f;
					c3++;
				}
				if(cnt>40){
					f=f+2.0f;
					c4++;
				}
			}
			
			list.get(i).put("c1", c1);
			list.get(i).put("c2", c2);
			list.get(i).put("c3", c3);
			list.get(i).put("c4", c4);
			list.get(i).put("f1", 0-f);			
			
			list.get(i).putAll(countDesd(df.sqlGet("SELECT d.* FROM desd d, stmd s, Class c WHERE d.ddate>='"+begin+"' AND d.ddate<='"+end+"' AND " +
			"c.ClassNo=s.depart_class AND s.student_no=d.student_no AND c.ClassNo ='"+list.get(i).get("ClassNo")+"' AND act_illegal='1'")));
		}
		
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
		out.println (" <Worksheet ss:Name='工作表1'>");
		out.println ("  <Table ss:ExpandedColumnCount='14' ss:ExpandedRowCount='"+list.size()+10+"' x:FullColumns='1'");
		out.println ("   x:FullRows='1' ss:StyleID='s78' ss:DefaultColumnWidth='54'");
		out.println ("   ss:DefaultRowHeight='25.5'>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='120'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
		out.println ("   <Column ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50' ss:Span='1'/>");
		out.println ("   <Column ss:Index='5' ss:StyleID='s76' ss:AutoFitWidth='0' ss:Width='50'/>");
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
		out.println ("    <Cell><Data ss:Type='String'>11~20</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>21~30</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>31~40</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>40+</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>均病</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>曠缺</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>大功</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>小功</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>嘉獎</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>大過</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>小過</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>申誡</Data></Cell>");
		out.println ("    <Cell><Data ss:Type='String'>獎懲</Data></Cell>");
		out.println ("   </Row>");		
		
		float f3;
		float abs3,al;
		for(int i=0; i<list.size(); i++){
			
			abs3=Float.parseFloat(list.get(i).get("abs3").toString());
			al=Float.parseFloat(list.get(i).get("al").toString());
			
			if(abs3>0){
				f3=abs3/al;
			}else{
				f3=0;
			}
			
			out.println ("   <Row>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("ClassName")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("c1")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("c2")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("c3")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("c4")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+f3+"</Data></Cell>");
			
			f3=Math.round(f3);
			if(f3>5){
				f3=0-(f3-5);
			}else{
				f3=0;
			}
			
			out.println ("    <Cell><Data ss:Type='String'>"+(Float.parseFloat(list.get(i).get("f1").toString())+f3)+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("j1")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("j2")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("j3")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("j4")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("j5")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("j6")+"</Data></Cell>");
			out.println ("    <Cell><Data ss:Type='String'>"+list.get(i).get("f2")+"</Data></Cell>");
			out.println ("   </Row>");	
		}		
		
		out.println ("  </Table>");
		out.println ("  <WorksheetOptions xmlns='urn:schemas-microsoft-com:office:excel'>");
		out.println ("   <PageSetup>");
		out.println ("    <Header x:Margin='0.3'");
		out.println ("     x:Data='&amp;C&amp;18 "+getContext().getAttribute("school_year")+"學年第"+getContext().getAttribute("school_term")+"學期生活教育競賽 &#10;"+begin+" 至"+end+"&amp;R&amp;D-&amp;T&#10;第&amp;P頁 共&amp;N頁'/>");
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
		
		out.println ("</Workbook>");	
		out.close();
		
		return null;
	}
	
	/**
	 * 去他媽的爛table要怎麼才能算的快,程式跑麻煩,sql跑更麻煩操操操,
	 * @param desd
	 * @return
	 */
	private Map countDesd(List<Map>desd){
		float k1,k2,k3,k4,k5,k6; 
		
		int j1,j2,j3,j4,j5,j6;
		int cnt;
		
		k1=0;k2=0;k3=0;k4=0;k5=0;k6=0;
		j1=0;j2=0;j3=0;j4=0;j5=0;j6=0;
		
		String kind;
		Map map=new HashMap();
		
		for(int i=0; i<desd.size(); i++){
			k1=0;k2=0;k3=0;k4=0;k5=0;k6=0;
			j1=0;j2=0;j3=0;j4=0;j5=0;j6=0;
			
			if(desd.get(i).get("kind1")!=null&&desd.get(i).get("cnt1")!=null){
					
				kind=desd.get(i).get("kind1").toString();
				cnt=Integer.parseInt(desd.get(i).get("cnt1").toString());				
				//大功
				if(kind.equals("1")){
					k1=k1+(cnt*2);             
					j1=j1+cnt;
				}				
				//小功
				if(kind.equals("2")){
					k2=k2+(cnt*0.5f);             
					j2=j2+cnt;
				}				
				//嘉獎
				if(kind.equals("3")){
					k3=k3+(cnt*0.1f);             
					j3=j3+cnt;
				}				
				//大過
				if(kind.equals("4")){
					k4=k4-(cnt*2);             
					j4=j4+cnt;
				}				
				//小功
				if(kind.equals("5")){
					k5=k5-(cnt*0.5f);             
					j5=j5+cnt;
				}				
				//嘉獎
				if(kind.equals("6")){
					k6=k6-(cnt*0.1f);             
					j6=j6+cnt;
				}
			}
			
			if(desd.get(i).get("kind2")!=null&&desd.get(i).get("cnt2")!=null){

				kind=desd.get(i).get("kind2").toString();
				cnt=Integer.parseInt(desd.get(i).get("cnt2").toString());
				//大功
				if(kind.equals("1")){
					k1=k1+(cnt*2);             
					j1=j1+cnt;
				}				
				//小功
				if(kind.equals("2")){
					k2=k2+(cnt*0.5f);             
					j2=j2+cnt;
				}				
				//嘉獎
				if(kind.equals("3")){
					k3=k3+(cnt*0.1f);             
					j3=j3+cnt;
				}				
				//大過
				if(kind.equals("4")){
					k4=k4-(cnt*2);             
					j4=j4+cnt;
				}
				
				//小功
				if(kind.equals("5")){
					k5=k5-(cnt*0.5f);             
					j5=j5+cnt;
				}				
				//嘉獎
				if(kind.equals("6")){
					k6=k6-(cnt*0.1f);             
					j6=j6+cnt;
				}
			}			
		}		
		map.put("j1", j1);		
		map.put("j2", j2);
		map.put("j3", j3);
		map.put("j4", j4);
		map.put("j5", j5);
		map.put("j6", j6);
		map.put("f2", k1+k2+k3+k4+k5+k6);
		return map;
	}
}