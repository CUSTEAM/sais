package action.rollCall;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.Message;
import action.BaseAction;

/**
 * 檢點名記錄
 * @author John
 */
public class CallStatusViewAction extends BaseAction{
	
	public String begin;
	public String end;
	public String cno;
	public String sno;
	public String dno;
	public String grade;
	public String class_no;
	public int num;
	
	public String execute() throws Exception {
		if(request.getParameter("Oid")!=null){
			
			List<Map>dilg=df.sqlGet("SELECT Dilg_app_oid, c.ClassName, s.student_no, s.student_name, d.date, d.cls, dr.name FROM "
					+ "Class c,Dilg d, stmd s, Dilg_rules dr WHERE c.ClassNo=s.depart_class AND "
					+ "d.abs=dr.id AND d.student_no=s.student_no AND Dtime_oid="+request.getParameter("Oid"));
			//制造文字報表
			Date date=new Date();
			response.setContentType("application/vnd.ms-excel; charset=UTF-8");
			response.setHeader("Content-disposition","attachment;filename="+date.getTime()+".xls");				
			PrintWriter out=response.getWriter();
			
			out.println("<html>");
			out.println("<head><meta http-equiv=application/vnd.ms-excel; charset=UTF-8><style>body{background-color:#cccccc;}td{font-size:18px;color:#333333;font-family:新細明體}</style>");
			out.println("</head>");
			out.println("<body>");
			
			out.println("			<table bgcolor='#ffffff'><tr><td>");
			out.println("			<table border='1'>");
			out.println("<tr>");
			out.println("<td>");

			// SimpleDateFormat sf=new SimpleDateFormat("yyyy/MM/dd HH:mm");
			// String date=sf.format(new Date());
			//String school;

			out.println("<table border='1'>");
			out.println("<tr>");
			out.println("<td align='center'>班級</td>");
			out.println("<td align='center'>學號</td>");
			out.println("<td align='center'>姓名</td>");
			out.println("<td align='center'>日期</td>");
			out.println("<td align='center'>節次</td>");
			out.println("<td align='center'>假單</td>");
			out.println("<td align='center'>假別</td>");
			out.println("</tr>");
			
			for(int i=0; i<dilg.size(); i++){
				out.println("<tr>");
				out.println("<td align='center'>"+dilg.get(i).get("ClassName")+"</td>");
				out.println("<td align='center'>"+dilg.get(i).get("student_no")+"</td>");
				out.println("<td align='center'>"+dilg.get(i).get("student_name")+"</td>");
				out.println("<td align='center'>"+dilg.get(i).get("date")+"</td>");
				out.println("<td align='center'>"+dilg.get(i).get("cls")+"</td>");
				if(dilg.get(i).get("Dilg_app_oid")==null){
					out.println("<td align='center'></td>");
				}else{
					out.println("<td align='center'>"+dilg.get(i).get("Dilg_app_oid")+"</td>");
				}
				
				out.println("<td align='center'>"+dilg.get(i).get("name")+"</td>");
				out.println("</tr>");
			}
			
			
			
			out.close();
			return null;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		return SUCCESS;
	}
	
	public String search() throws Exception {
		if(begin.trim().equals("")||end.trim().equals("")){
			Message msg=new Message();
			msg.setError("請輸入範圍");
			this.savMessage(msg);
			return SUCCESS;
		}		
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		
		if(sf.parse(begin).getTime()>sf.parse(end).getTime()){
			Message msg=new Message();
			msg.setError("時間範圍矛盾");
			this.savMessage(msg);
			return SUCCESS;
		}	
		
		session.put("result", df.sqlGet("SELECT cl.ClassName, c.chi_name, d.Oid, e.cname, (SELECT COUNT(*)FROM Dilg WHERE Dtime_oid=d.Oid AND date>='"+begin+"' AND date<='"+end+"') as dilgCnt, " +
		"(SELECT COUNT(*)FROM DilgLog WHERE Dtime_oid=d.Oid)as logCnt FROM Csno c, Class cl, (Dtime d LEFT OUTER JOIN " +
		"empl e ON d.techid=e.idno)LEFT OUTER JOIN Dilg g ON g.Dtime_oid=d.Oid AND g.date>='"+begin+"' AND g.date<='"+end+"' WHERE " +
		"cl.ClassNo=d.depart_class AND c.cscode=d.cscode AND d.Sterm='"+getContext().getAttribute("school_term")+"' AND cl.CampusNo='"+
		cno+"' AND cl.SchoolNo LIKE'"+sno+"%' AND cl.DeptNo LIKE '"+dno+"%' GROUP BY d.Oid"));
		
		return SUCCESS;
	}
}