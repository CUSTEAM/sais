package action.rollCall;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import action.BaseAction;
import action.rollCall.timeOffSearch.ListCls;
import action.rollCall.timeOffSearch.ListMail;
import action.rollCall.timeOffSearch.ListStd;

public class TimeOffSearchAction extends BaseAction{
	
	public String cno;
	public String sno;
	public String dno;
	public String gno;
	public String zno;
	
	public String less;
	public int num;
	
	public String beginDate;
	public String endDate;
	
	public String execute(){
		
		return SUCCESS;
	}
	
	
	/**
	 * 班級
	 * @return
	 * @throws IOException
	 */
	public String listCls() throws IOException{		
		
		/*簡短但效率
		List a=df.sqlGet("SELECT sum(abs='2') abs2, sum(abs='3') abs3, sum(abs='4') abs4, " +
				"sum(abs='6') abs6,(SELECT COUNT(*)FROM stmd WHERE stmd.student_no NOT IN " +
				"(SELECT DISTINCT Dilg.student_no FROM Dilg))as noabs FROM " +
				"Dilg d, stmd s WHERE d.date>='"+beginDate+"' AND d.date<='"+endDate+"' AND " +
				"d.student_no=s.student_no AND s.depart_class LIKE '"+cno+sno+dno+gno+zno+"%'");
		*/
		
		StringBuilder sb=new StringBuilder("SELECT ClassName,(SELECT COUNT(*)FROM stmd WHERE "
		+ "depart_class=Class.ClassNo)as sts, (SELECT COUNT(*)FROM Dilg,stmd WHERE "
		+ "Dilg.date>='"+beginDate+"'AND Dilg.date<='"+endDate+"'AND Dilg.student_no=stmd.student_no AND "
		+ "stmd.depart_class=Class.ClassNo AND Dilg.abs='2')as abs2, (SELECT COUNT(*)FROM Dilg, stmd "
		+ "WHERE Dilg.date>='"+beginDate+"'AND Dilg.date<='"+endDate+"'AND Dilg.student_no=stmd.student_no AND "
		+ "stmd.depart_class=Class.ClassNo AND Dilg.abs='3')as abs3,(SELECT COUNT(*)FROM Dilg, stmd WHERE "
		+ "Dilg.date>='"+beginDate+"'AND Dilg.date<='"+endDate+"'AND Dilg.student_no=stmd.student_no AND "
		+ "stmd.depart_class=Class.ClassNo AND Dilg.abs='4')as abs4,(SELECT COUNT(*)FROM Dilg, stmd WHERE "
		+ "Dilg.date>='"+beginDate+"'AND Dilg.date<='"+endDate+"'AND Dilg.student_no=stmd.student_no AND "
		+ "stmd.depart_class=Class.ClassNo AND Dilg.abs='6')as abs6,(SELECT COUNT(*) FROM stmd s "
		+ "LEFT OUTER JOIN Dilg d ON s.student_no=d.student_no WHERE d.Oid IS NULL AND "
		+ "s.depart_class =Class.ClassNo)as noabs FROM Class WHERE Type='P'");
		if(!cno.equals(""))sb.append("AND CampusNo='"+cno+"'");
		if(!sno.equals(""))sb.append("AND SchoolNo='"+sno+"'");
		if(!dno.equals(""))sb.append("AND DeptNo='"+dno+"'");
		if(!gno.equals(""))sb.append("AND Grade='"+gno+"'");
		if(!zno.equals(""))sb.append("AND SeqNo='"+zno+"'");
		sb.append("ORDER BY ClassNo");
		List<Map>tmpList=df.sqlGet(sb.toString());			
		
		List<Map>list=new ArrayList();
		for(int i=0; i<tmpList.size(); i++){//無學生班級
			if(!tmpList.get(i).get("sts").toString().equals("0")){
				list.add(tmpList.get(i));
			}			
		}
		tmpList=null;		
		
		ListCls lc=new ListCls();
		lc.print(response, list, beginDate, endDate, getContext().getAttribute("school_year").toString(), getContext().getAttribute("school_term").toString());
		lc=null;
		return null;
	}
	
	/**
	 * 嚴重
	 * @return
	 * @throws IOException
	 */
	public String listStd() throws IOException{
		
		List tmpList=df.sqlGet("SELECT c.ClassName, s.student_no, s.student_name,s.parent_name,s.CellPhone,s.telephone, " +
		"(SELECT COUNT(*)FROM Dilg WHERE abs='1' AND Dilg.date>='"+beginDate+"' AND Dilg.date<='"+endDate+"' AND Dilg.student_no=s.student_no)as abs1, " +
		"(SELECT COUNT(*)FROM Dilg WHERE abs='2' AND Dilg.date>='"+beginDate+"' AND Dilg.date<='"+endDate+"' AND Dilg.student_no=s.student_no)as abs2, " +
		"(SELECT COUNT(*)FROM Dilg WHERE abs='3' AND Dilg.date>='"+beginDate+"' AND Dilg.date<='"+endDate+"' AND Dilg.student_no=s.student_no)as abs3, " +
		"(SELECT COUNT(*)FROM Dilg WHERE abs='4' AND Dilg.date>='"+beginDate+"' AND Dilg.date<='"+endDate+"' AND Dilg.student_no=s.student_no)as abs4, " +
		"(SELECT COUNT(*)FROM Dilg WHERE abs='5' AND Dilg.date>='"+beginDate+"' AND Dilg.date<='"+endDate+"' AND Dilg.student_no=s.student_no)as abs5, " +
		"(SELECT COUNT(*)FROM Dilg WHERE abs='6' AND Dilg.date>='"+beginDate+"' AND Dilg.date<='"+endDate+"' AND Dilg.student_no=s.student_no)as abs6, " +
		"(SELECT COUNT(*)FROM Dilg WHERE abs='7' AND Dilg.date>='"+beginDate+"' AND Dilg.date<='"+endDate+"' AND Dilg.student_no=s.student_no)as abs7, " +
		"(SELECT COUNT(*)FROM Dilg WHERE abs='8' AND Dilg.date>='"+beginDate+"' AND Dilg.date<='"+endDate+"' AND Dilg.student_no=s.student_no)as abs8, " +
		"(SELECT COUNT(*)FROM Dilg WHERE abs='9' AND Dilg.date>='"+beginDate+"' AND Dilg.date<='"+endDate+"' AND Dilg.student_no=s.student_no)as abs9 " +
		"FROM Class c,stmd s WHERE c.ClassNo=s.depart_class AND s.depart_class LIKE'"+cno+sno+dno+gno+zno+"%' ORDER BY c.ClassNo, s.student_no");
		
		List list=new ArrayList();
		for(int i=0; i<tmpList.size(); i++){
			if (less.equals("m")) {
				if(Integer.parseInt(((Map)tmpList.get(i)).get("abs2").toString())>=num){
					list.add(tmpList.get(i));
				}
			}
			if (less.equals("l")) {
				if(Integer.parseInt(((Map)tmpList.get(i)).get("abs1").toString())<=num&&
				   Integer.parseInt(((Map)tmpList.get(i)).get("abs2").toString())<=num&&
				   Integer.parseInt(((Map)tmpList.get(i)).get("abs3").toString())<=num&&
				   Integer.parseInt(((Map)tmpList.get(i)).get("abs4").toString())<=num&&
				   Integer.parseInt(((Map)tmpList.get(i)).get("abs5").toString())<=num&&
				   Integer.parseInt(((Map)tmpList.get(i)).get("abs6").toString())<=num&&
				   Integer.parseInt(((Map)tmpList.get(i)).get("abs7").toString())<=num&&
				   Integer.parseInt(((Map)tmpList.get(i)).get("abs8").toString())<=num&&
			       Integer.parseInt(((Map)tmpList.get(i)).get("abs9").toString())<=num){
					list.add(tmpList.get(i));
				}
			}
		}
		tmpList=null;
		ListStd ls=new ListStd();
		ls.print(response, list, beginDate, endDate, getContext().getAttribute("school_year").toString(), getContext().getAttribute("school_term").toString());
		ls=null;
		return null;
	}
	
	/**
	 * 通知
	 * @return
	 * @throws IOException
	 * @throws ParseException 
	 */
	public String listMail() throws IOException, ParseException{
		
		List<Map>tmpList=df.sqlGet("SELECT c.ClassName, s.student_no, s.student_name,s.parent_name,s.curr_addr, s.curr_post,COUNT(*)as cnt " +
		"FROM Class c,stmd s LEFT OUTER JOIN Just j ON j.student_no=s.student_no ,Dilg d WHERE j.dilg_mail_cnt<"+num+" AND c.ClassNo=s.depart_class AND s.student_no=d.student_no AND d.abs='2' AND " +
		"s.depart_class LIKE'"+cno+sno+dno+gno+zno+"%' AND d.date<='"+endDate+"' GROUP BY s.student_no ORDER BY c.ClassNo");
		
		//缺課內容
		List <Map>list=new ArrayList();
		List tmp;
		for(int i=0; i<tmpList.size(); i++){
			tmp=df.sqlGet("SELECT d.cls, DATE_FORMAT(d.date,'%m-%d')as date FROM Dilg d WHERE date<='"+endDate+"' AND d.abs='2' AND d.student_no='"+tmpList.get(i).get("student_no")+"'");
			
			if(Integer.parseInt(tmpList.get(i).get("cnt").toString())<num){				
				continue;
			}
			/*
			if(df.sqlGetInt("SELECT COUNT(*)as cnt FROM Dilg d WHERE d.abs='2' AND d.student_no='"+
			tmpList.get(i).get("student_no")+"' AND d.date<='"+beginDate+"'")>num){
				continue;
			}	
			*/		
			tmpList.get(i).put("dilgs", tmp);
			list.add(tmpList.get(i));
			df.exSql("INSERT INTO Just(student_no, dilg_mail_cnt) VALUES ('"+tmpList.get(i).get("student_no")+"', "+num+")ON DUPLICATE KEY UPDATE dilg_mail_cnt="+num);
		}
		
		ListMail lm=new ListMail();
		lm.print(response, list, beginDate, endDate);
		lm=null;		
		return null;
	}
}